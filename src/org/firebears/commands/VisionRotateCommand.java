package org.firebears.commands;

import static org.firebears.RobotMap.boundAngle;
import static org.firebears.RobotMap.getNavXAngle;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionRotateCommand extends PIDCommand {

	long timeout;
	double turnValue;
	double targetAngle;
	double FORWARD_SPEED = .15;
	private static final double SPEED = 0.1;
	private static final double TOLERANCE = 1.0;
	
    public VisionRotateCommand() {
    	super(.0325, 0, 0);
        
    	requires(Robot.chassis);
    	
    	getPIDController().setInputRange(-180, 180);
    	getPIDController().setContinuous();
    	getPIDController().setAbsoluteTolerance(TOLERANCE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// Set how much time until command stops automatically
    	timeout = System.currentTimeMillis() + 1000 * 5;
    	
    	// Get turn value from vision subsystem
//    	turnValue = Robot.vision.getAngleX();
    	turnValue = 90;
    	
    	// Set target angle for PID to current angle + angle from vision
    	targetAngle = boundAngle(getNavXAngle() + turnValue);
    	System.out.println(targetAngle);
    	getPIDController().setSetpoint(0);
    	
    	System.out.println("Starting " + this.toString());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { 
    	
    	// Stop command when timeout expires, or when the current angle is within the tolerance
    	double difference = getAngleDifference(); 
    	SmartDashboard.putNumber("Angle Difference:", difference); 
    	if (System.currentTimeMillis() >= timeout || Math.abs(difference) < TOLERANCE) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.chassis.stop();
    	Robot.chassis.drive(0, 0, false);
    	System.out.println("Ending " + this);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("Was interrupted");
    }
    
    private double getAngleDifference() {
    	// Get angle between current position and target position
		return boundAngle(getNavXAngle() - targetAngle);
	}

	@Override
	protected double returnPIDInput() {
		// Use difference in angles as input for PID
		return getAngleDifference();
	}

	@Override
	protected void usePIDOutput(double output) {
		// Make sure output doesn't go faster than expected
		output = Math.max(-SPEED, Math.min(output, SPEED));
		// Drive the robot
		Robot.chassis.drive(0.0, output,false);
	}
	
	public String toString() {
		return "VisionRotateCommand: " + turnValue + " degrees";
	}
}
