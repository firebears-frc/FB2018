package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the robot "degrees" degrees
 */
public class RotateToAngleCommand extends PIDCommand {

    protected final double turnValue;
    protected final double SPEED = 0.3;
    protected double angleTolerance = 5.0;
    protected double targetAngle;
    long timeout;

    public RotateToAngleCommand(double degrees) {
	super(1., 0.0, 0.0); // PID
	requires(Robot.chassis);
	turnValue = degrees;

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

    protected void initialize() {
	timeout = System.currentTimeMillis() + 1000 * 2;
	targetAngle = bound(RobotMap.navXBoard.getAngle() + turnValue);
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
