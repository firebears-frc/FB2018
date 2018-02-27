package org.firebears.commands;

import static org.firebears.RobotMap.boundAngle;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Drive Straight from 2016 modified for 2018 robot.
 */
public class DriveToDistanceStraightCommand extends PIDCommand {
	
	long timeout;
	double startAngle;
	double currentAngle;
	double tolerance = 2.5;
	double startingDistance;
	double currentDistance;
	double targetDistance;
	double speed;

	

	public DriveToDistanceStraightCommand(double z, double speed) {
		super(1.0, 0.0, 0.0);
		requires(Robot.chassis);
		targetDistance = z;
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
		startAngle = RobotMap.navXBoard.getAngle();
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		getPIDController().setSetpoint(0.0);
		
	}

	protected void execute() {
		currentAngle = RobotMap.navXBoard.getAngle();
		System.out.println("navx angle: "+ currentAngle);
	}

	protected boolean isFinished() {
		if (System.currentTimeMillis() >= timeout) {
			return true;
		}

		if (inchesTraveled() >= targetDistance) {
			return true;
		}
		return false;	}

	protected void end() {
		getPIDController().disable();
		Robot.chassis.drive(0, 0, false);
	}

	protected void interrupted() {
		end();
	}
	private double inchesTraveled() {
		currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		return (currentDistance - startingDistance) / 52.6;
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
