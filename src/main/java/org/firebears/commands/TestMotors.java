package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestMotors extends Command {

	boolean straight;
	boolean forward;
	
	double SPEED;
	double ROTATE;
	
    public TestMotors(boolean straight, boolean forward) {
    	this.straight = straight;
    	this.forward = forward;
    	
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (straight && forward) {
    		SPEED = 1;
    		ROTATE = 0;
    	} else if (straight && forward == false) {
    		SPEED = -1;
    		ROTATE = 0;
    	} else if (straight == false && forward) {
    		SPEED = 0;
    		ROTATE = 1;
    	} else if (straight == false && forward == false) {
    		SPEED = 0;
    		ROTATE = -1;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.drive(SPEED, ROTATE);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
