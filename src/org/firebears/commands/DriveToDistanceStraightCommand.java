package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Drive Straight from 2016 modified for 2018 robot.
 */
public class DriveToDistanceStraightCommand extends PIDCommand {
	enum UNTIL {
		UNTIL_TIMEOUT, // Command will finish after a timeout
		UNTIL_DISTANCE, // Command will finish after a certain distance
		UNTIL_HIT_WALL, // Command will finish after distance sensor detects something
	}

	final double max_speed;
	final double distance;
	final UNTIL untilx;
	double targetLocation;
	double targetAngle;
	long timeout;

	public DriveToDistanceStraightCommand(double z, double speed) {
		super(0.1, 0.0, 0.0);
		requires(Robot.chassis);
		distance = z;
		max_speed = speed;
		untilx = UNTIL.UNTIL_DISTANCE;
		getPIDController().setAbsoluteTolerance(2);
	}

	public DriveToDistanceStraightCommand(double z) {
		this(z, .6);
	}

	public DriveToDistanceStraightCommand(UNTIL until, double speed) {
		this(0., speed);
	}

	protected void initialize() {
		timeout = System.currentTimeMillis() + 1000 * 20;
		targetAngle = RobotMap.navXBoard.getAngle();
		targetLocation = distance + returnPIDInput();
		setSetpoint(targetLocation);
		getPIDController().enable();
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		double currentLocation = returnPIDInput();
		return (Math.abs(currentLocation - targetLocation) < 2) || (System.currentTimeMillis() > timeout);
	}

	protected void end() {
		getPIDController().disable();
		Robot.chassis.drive(0, 0, false);
	}

	protected void interrupted() {
		end();
	}

	@Override
	protected double returnPIDInput() {
		return RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX)/52.6;
	}

	@Override
	protected void usePIDOutput(double output) {
		output = Math.max((max_speed*-1), Math.min(output, max_speed));
		double currentAngle = RobotMap.navXBoard.getAngle();
		double angleDiff = diff(targetAngle, currentAngle);
		double x = angleDiff * 0.1;
		x = Math.max(-0.2, Math.min(x, 0.2));
		double y = -1 * output;
		System.out.println("Diff: " + angleDiff);
		Robot.chassis.drive(y, x, false);

	}
	/**
	 * Calculate angle1 - angle2.
	 * @return angle in the range -180 to 180.
	 */
	protected static double diff(double angle1, double angle2) {
		double angleDiff = angle1 - angle2;
		while (angleDiff < -180.0) { angleDiff += 360.0; }
		while (angleDiff > 180.0) { angleDiff -= 360.0; }
		return angleDiff;
}
}
