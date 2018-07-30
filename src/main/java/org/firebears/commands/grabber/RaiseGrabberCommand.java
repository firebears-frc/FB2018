package org.firebears.commands.grabber;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseGrabberCommand extends Command {
	boolean shouldRaise;
    public RaiseGrabberCommand(boolean shouldRaise) {
    	this.shouldRaise = shouldRaise;
    	requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println(this.shouldRaise);
    	if (this.shouldRaise == true) {
    		System.out.println("Raised grabber");
    		Robot.grabber.grabberRaise();
    	} else {
    		System.out.println("Lowered grabber");
    		Robot.grabber.grabberLower();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println(RobotMap.leftUpDown.get());
		System.out.println(RobotMap.rightUpDown.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
