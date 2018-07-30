package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the robot "degrees" degrees based on the current direction
 */
public class RelativeAngleCommandFast extends PIDCommand {

    protected double turnValue;
    protected final double SPEED = 0.7;
    protected double angleTolerance = 1.5;
    protected double targetAngle;
    long timeout;

    public RelativeAngleCommandFast(double degrees) {
    	super(0.025, 0.0, 0.055); // PID P = .035 or .03375 D = 0.095
		requires(Robot.chassis);
		turnValue = degrees;

		getPIDController().setInputRange(-180, 180);
		getPIDController().setContinuous(true);
		getPIDController().setAbsoluteTolerance(angleTolerance);
		
//		SmartDashboard.putData("Rotate Command", getPIDController());
    }
    
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
    	timeout = System.currentTimeMillis() + 1000 * 5;
//    	turnValue = SmartDashboard.getNumber("Target Angle", 0);
    	targetAngle = bound(RobotMap.navXBoard.getAngle() + turnValue);
    	
    	getPIDController().setSetpoint(0.0);
    	if (RobotMap.DEBUG)
    		System.out.println("\t # " + this);
    	this.toString();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	if (System.currentTimeMillis() >= timeout) {
    		return true;
    	} else {
    		return Math.abs(RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX)) < 1 
    				&& Math.abs(getAngleDifference()) < angleTolerance;
    	}
//    	return false;
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
    	Robot.chassis.drive(0, -output, false);
    }

    @Override
    public String toString() {
    	return "AnotherRotateCommand(" + this.turnValue + ") from " + bound(RobotMap.getNavXAngle());
    }
}
