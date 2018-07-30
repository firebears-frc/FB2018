package org.firebears.commands;

import static org.firebears.RobotMap.boundAngle;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive Straight from 2016 modified for 2018 robot.
 */
public class VisionForwardCommand extends PIDCommand {
	
	long timeout;
	double startAngle;
	double currentAngle;
	double tolerance = 2.5;
	double startingDistance;
	double currentDistance;
	double targetDistance;
	double speed;
	double offsetFrom50Per = 11.33;
	double offsetFrom100Per = 23.75;

	public VisionForwardCommand(double speed) {
		super(.065, 0.0, 0.0);
		requires(Robot.chassis);
		this.speed = speed;
		getPIDController().setInputRange(-180.0, 180.0);
		getPIDController().setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous();
		
	}
	public double getAngleDifference() {
		return boundAngle(RobotMap.navXBoard.getAngle() - startAngle);
	}

	protected void initialize() {
		timeout = System.currentTimeMillis() + 1000 * 6;
		
		// Remove this before comp
//		speed = SmartDashboard.getNumber("Target Speed", 0);
		
		startAngle = RobotMap.navXBoard.getAngle();
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
//		targetDistance = targetDistance - getOffset(speed);
		getPIDController().setSetpoint(0.0);
	}

	protected void execute() {
		currentAngle = RobotMap.navXBoard.getAngle();
//		System.out.println("navx angle: "+ currentAngle);
	}

	protected boolean isFinished() {
		if (System.currentTimeMillis() >= timeout) {
			return true;
		}
		if (Robot.grabber.cubeInRange()) {
			return true;
		}
		return false;	
	}

	protected void end() {
		getPIDController().disable();
		Robot.chassis.drive(0, 0, true);
	}

	protected void interrupted() {
		end();
	}
	
	// Calculate overshoot based on given speed
//	private double getOffset(double speed) {
//    	double offsetAnswer;
//    	offsetAnswer = speed * ((offsetFrom100Per - offsetFrom50Per)/.5);//y = m*x + b
//    	return offsetAnswer;
//    }
	
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
		Robot.chassis.drive(-speed, output, false);

	}
}
