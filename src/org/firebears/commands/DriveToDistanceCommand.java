package org.firebears.commands;

import static org.firebears.RobotMap.boundAngle;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class DriveToDistanceCommand extends PIDCommand {

	double time;
	
	
	long timeout;
	double startAngle;
	double currentAngle;
	double tolerance = 2.5;
	double startingDistance;
	double currentDistance;
	double targetDistance;	
	double dis;
	double timeMutiple;
	double velocity;
	double timeToGo;
	double setVelocity;
	double idleTime;
	double idle;
	double timeToStop;
	double initialTime;
    public DriveToDistanceCommand(double dis, double time, double setVelocity) {
    	
    	
    	super(.065, 0.0, 0.0);
		requires(Robot.chassis);
		this.targetDistance = dis;
//		targetDistance = z;
//		this.speed = speed;
		getPIDController().setInputRange(-180.0, 180.0);
		getPIDController().setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous();
		this.setVelocity = setVelocity;
    	this.time = time;
    }

    protected void initialize() {
    	initialTime = System.currentTimeMillis();
    	timeToGo = initialTime + time;
    	idleTime = dis/setVelocity - time;
    	idle = timeToGo + idleTime;
    	timeToStop = idle + time;
    	
    	startAngle = RobotMap.navXBoard.getAngle();
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		getPIDController().setSetpoint(0.0);
    }

    protected void execute() {
    	double currentTime = System.currentTimeMillis();
    	double stopWatchTime = System.currentTimeMillis() - initialTime;
    	double timeMutiple = stopWatchTime/time;
    	double negTimeMutiple = (stopWatchTime - idle / time) - 1;  

    	
    	if (currentTime <= timeToGo) {
    		velocity = timeMutiple;
    	}else if (currentTime > timeToGo){
    		velocity = setVelocity;
    	}else if (currentTime >= timeToStop) {
    		velocity = negTimeMutiple;
    	}
    	
		currentAngle = RobotMap.navXBoard.getAngle();

    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("inchesTraveled: " + inchesTraveled());
        return inchesTraveled() >= targetDistance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	getPIDController().disable();
		Robot.chassis.drive(0, 0, true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		end();
    }
    
    public double getAngleDifference() {
		return boundAngle(RobotMap.navXBoard.getAngle() - startAngle);
	}
    
    private double inchesTraveled() {
		currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		return Math.abs((currentDistance - startingDistance) / 52.6);
	}

	@Override
	protected double returnPIDInput() {
		return getAngleDifference();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.chassis.drive(-velocity, output, false);		
	}
}
