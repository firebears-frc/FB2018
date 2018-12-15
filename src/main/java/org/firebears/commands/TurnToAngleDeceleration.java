package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Rotate the robot "degrees" degrees based on the current direction
 */
public class TurnToAngleDeceleration extends PIDCommand {

    protected double m_turnValue;
    protected double m_MAXSPEED;
    protected double m_MINSPEED = 0.1;
    protected double m_decelDegree;
    protected double m_angleTolerance = 2.0;
    protected double m_targetAngle;
    long timeout;

    public TurnToAngleDeceleration(double degrees, double speed, double decelDegree) {
    	super(0.075, 0.0, 0.0); // PID
    	requires(Robot.chassis);
    	m_turnValue = degrees;
    	m_MAXSPEED = speed;
    	m_decelDegree = decelDegree;
    	
    	getPIDController().setInputRange(-180, 180);
    	getPIDController().setContinuous(true);
    	getPIDController().setAbsoluteTolerance(m_angleTolerance);
    }

    protected void initialize() {
		timeout = System.currentTimeMillis() + 1000 * 5;
		
		m_turnValue = SmartDashboard.getNumber("Target Angle", 0);
		m_decelDegree = SmartDashboard.getNumber("Decel Degree", 0);
		m_MAXSPEED = SmartDashboard.getNumber("Max Speed", 0);
		m_MINSPEED = SmartDashboard.getNumber("Min Speed", 0);
		m_targetAngle = bound(RobotMap.navXBoard.getAngle() + m_turnValue);
		
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

    	
		if (m_turnValue > 0) {
			return difference < m_angleTolerance || System.currentTimeMillis() >= timeout;
		} else if (m_turnValue < 0) {
			return difference > -m_angleTolerance || System.currentTimeMillis() >= timeout;
		}else {
			return true;
		}
    }

    protected void end() {
    	Robot.chassis.drive(0, 0);
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
    	double maxSpeed = m_MAXSPEED;
    	
    	if (Math.abs(getAngleDifference()) <= m_decelDegree) {
    		maxSpeed = m_MINSPEED;
    	}
    	
    	output = Math.max(-maxSpeed, Math.min(output, maxSpeed));
    	Robot.chassis.drive(0, -output);
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
    	return getAngleDifference(RobotMap.navXBoard.getAngle(), m_targetAngle);
    }
    
    @Override
    public String toString() {
    	return "RotateToAngleCommand(" + this.m_turnValue + ") from " + bound(RobotMap.getNavXAngle());
    }
}
