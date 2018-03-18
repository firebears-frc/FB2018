package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the robot to the given angle
 */
public class RotateToAngleCommand extends PIDCommand {

	protected double turnValue;
	protected final double SPEED = 0.5;
	protected double angleTolerance = 2;
	protected double targetAngle;
	double angle;
	double offsetFrom90 = 10;
	//Competition Robot: 5.625;
	// 3.8;
	double offsetFrom10 = 3;
	// Competition Robot: 4;
	// 2.0;
	long timeout;

	public RotateToAngleCommand(double angle) {
		super(0.3, 0.0, 0.0); // PID
		requires(Robot.chassis);
		this.angle = bound(angle);
		
		getPIDController().setInputRange(-180, 180);
		getPIDController().setContinuous(true);
		getPIDController().setAbsoluteTolerance(angleTolerance);
	}

	/**
	 * @return (angle2 - angle1) in the range from -180 to 180. This is the angle to
	 *         get from angle1 to angle2.
	 */
	private static double getAngleDifference(double angle1, double angle2) {
		return bound(angle2 - angle1);
	}

	/**
	 * @return the angle folded into the range -180 to 180.
	 */
	protected static double bound(double angle) {
		while (angle > 180)
			angle -= 360;
		while (angle < -180)
			angle += 360;
		return angle;
	}

	/**
	 * @return the angle from the current heading to get back to the target angle,
	 *         in the range -180 to 180.
	 */
	private double getAngleDifference() {
		return getAngleDifference(RobotMap.navXBoard.getAngle(), targetAngle);
	}

	private double getOffset(double startAngle) {
		double offsetAnswer;
		offsetAnswer = startAngle * ((offsetFrom90 - offsetFrom10) / 80);// y = m*x + b
		return offsetAnswer;
	}

	protected void initialize() {
		timeout = System.currentTimeMillis() + 1000 * 5;
		
		turnValue = bound(angle - RobotMap.getNavXAngle());
		
		targetAngle = bound(angle - getOffset(turnValue));
		System.out.println(turnValue);
		System.out.println(targetAngle);
		
		// targetAngle = bound(RobotMap.navXBoard.getAngle() +
		// SmartDashboard.getNumber("Target Angle", 0) - getOffset(turnValue));
		getPIDController().setSetpoint(0.0);
		if (RobotMap.DEBUG)
			System.out.println("\t # " + this);

	}

	protected void execute() {
	}

	/**
	 * @return true when the angle difference gets close to zero.
	 */
	protected boolean isFinished() {
		double difference = getAngleDifference();
		// System.out.println("Target + " + targetAngle + ", Turn" + turnValue + ", Now"
		// + RobotMap.navXBoard.getAngle() + ", Diff" + difference);
		// SmartDashboard.putNumber("Difference", difference);

		return Math.abs(difference) < angleTolerance || System.currentTimeMillis() >= timeout;
	}

	protected void end() {
		Robot.chassis.drive(0, 0, true);
	}

	protected void interrupted() {
		end();
	}

	@Override
	/**
	 * @return the angle distance from the current heading to the target angle.
	 */
	protected double returnPIDInput() {
		return getAngleDifference();
	}

	@Override
	protected void usePIDOutput(double output) {
		output = Math.max(-SPEED, Math.min(output, SPEED));
		Robot.chassis.drive(0, -output, true);
	}

	@Override
	public String toString() {
		return "RotateToAngleCommand(" + this.turnValue + ")";
	}
}
