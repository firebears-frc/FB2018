package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the robot "degrees" degrees based on the current direction
 */
public class RelativeAngleCommand extends PIDCommand {

    protected double turnValue;
    protected final double SPEED = 0.7;
    protected double angleTolerance = 2.0;
    protected double targetAngle;
    double offsetFrom90 = 35;
    //Competition Robot: 
    //5.625;
    double offsetFrom10 = 7;
    //Competition Robot: 
    //4;
    double ideal90 = 32.625;
    long timeout;

    public RelativeAngleCommand(double degrees) {
	super(0.075, 0.0, 0.0); // PID
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
    
    private double getOffset(double startAngle) {
    	double offsetAnswer;
    	double absoluteAngle = Math.abs(startAngle);
//    	offsetAnswer = startAngle * ((offsetFrom90 - offsetFrom10)/80);//y = m*x + b
    	offsetAnswer = (absoluteAngle - 90) * ((offsetFrom90 - offsetFrom10)/80) + ideal90;
    	
    	if (startAngle < 0) {
    		offsetAnswer = offsetAnswer * -1;
    	}
    	
//    	offsetAnswer = Math.pow(startAngle, 2) * -.003 + startAngle * .829 + 11.89 - 35;
    	
    	return offsetAnswer;
    }

    protected void initialize() {
	timeout = System.currentTimeMillis() + 1000 * 5;
	
//	turnValue = SmartDashboard.getNumber("Target Angle", 0);
//	targetAngle = bound(RobotMap.navXBoard.getAngle() + turnValue);
	targetAngle = bound(RobotMap.navXBoard.getAngle() + turnValue - getOffset(turnValue));
	
//	targetAngle = bound(RobotMap.navXBoard.getAngle() + SmartDashboard.getNumber("Target Angle", 0));
//	targetAngle = bound(RobotMap.navXBoard.getAngle() + SmartDashboard.getNumber("Target Angle", 0) - getOffset(turnValue));
	getPIDController().setSetpoint(0.0);
	if (RobotMap.DEBUG)
	    System.out.println("\t # " + this);
		this.toString();
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

	
//		return Math.abs(difference) < angleTolerance || System.currentTimeMillis() >= timeout;
	
		if (turnValue > 0) {
			return difference < angleTolerance || System.currentTimeMillis() >= timeout;
		} else if (turnValue < 0) {
			return difference > -angleTolerance || System.currentTimeMillis() >= timeout;
		}else {
			return true;
		}
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
	return "RotateToAngleCommand(" + this.turnValue + ") from " + bound(RobotMap.getNavXAngle());
    }
}
