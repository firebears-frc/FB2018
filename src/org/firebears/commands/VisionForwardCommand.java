package org.firebears.commands;

import static org.firebears.RobotMap.boundAngle;
import static org.firebears.RobotMap.getNavXAngle;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class VisionForwardCommand extends PIDCommand {

	long timeout;
	double startAngle;
	double currentAngle;
	private static final double TOLERANCE = 2.5;
	static int SLOWDOWN_DISTANCE = 24;
	
    public VisionForwardCommand() {
    	super(.025, 0, 0);
        requires(Robot.chassis);
        
        getPIDController().setInputRange(-180.0, 180.0);
		getPIDController().setAbsoluteTolerance(TOLERANCE);
		getPIDController().setContinuous();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// Set how much time until command stops automatically
    	timeout = System.currentTimeMillis() + 1000 * 6;
    	
    	// Get starting angle and set PID setpoint
		startAngle = getNavXAngle();
		getPIDController().setSetpoint(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Stop command when timeout expires, or when the robot's distance is within the tolerance
    	if (System.currentTimeMillis() >= timeout || Robot.chassis.getRangeFinderDistance() < 12) {
			return true;
		}
		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public double getAngleDifference() {
		return boundAngle(getNavXAngle() - startAngle);
	}

	@Override
	protected double returnPIDInput() {
		return getAngleDifference();
	}

	@Override
	protected void usePIDOutput(double output) {
		// Slow robot down when within specific distance
		double speed = (Robot.chassis.getRangeFinderDistance() < SLOWDOWN_DISTANCE) ? -0.10 : -0.30;
		// Drive the robot
		Robot.chassis.drive(speed, output);
	}
}
