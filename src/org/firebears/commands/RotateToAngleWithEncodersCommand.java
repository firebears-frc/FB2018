package org.firebears.commands;

import static java.lang.Math.PI;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Rotate to an angle, using the wheel encoders to measure distance. For a given
 * angle, calculate the distance that one wheel must travel. Drive both sides of
 * the robot in opposite directions to reach that target distance.
 */
public class RotateToAngleWithEncodersCommand extends PIDCommand {

	final double TARGET_ANGLE;
	final double TARGET_DISTANCE;
	final double MAX_SPEED = 0.7;
	final double ROBOT_WIDTH_INCHES = 20.0;
	final double TOLERANCE_INCHES = 2.0;
	private double startingDistance;

	public RotateToAngleWithEncodersCommand(double angle) {
		super(.065, 0.0, 0.0);
		requires(Robot.chassis);
		TARGET_ANGLE = angle;
		TARGET_DISTANCE = ROBOT_WIDTH_INCHES * PI * (angle / 360.0);
	}

	@Override
	protected void initialize() {
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		setSetpoint(TARGET_DISTANCE);
		setTimeout(Math.round(Math.abs(TARGET_ANGLE) / 20) + 1);
	}

	@Override
	protected double returnPIDInput() {
		return inchesTraveled();
	}

	@Override
	protected void usePIDOutput(double speed) {
		speed = Math.max(-MAX_SPEED, Math.min(speed, MAX_SPEED));
		Robot.chassis.leftFront(-1 * speed);
		Robot.chassis.rightFront(speed);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(inchesTraveled() - TARGET_DISTANCE) <= TOLERANCE_INCHES || isTimedOut();
	}

	@Override
	public String toString() {
		return "RotateToAngleWithEncodersCommand(" + TARGET_ANGLE + ")";
	}
	
	private double inchesTraveled() {
		double currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		return (currentDistance - startingDistance) / 52.6;
	}
}
