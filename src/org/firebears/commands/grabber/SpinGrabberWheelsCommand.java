package org.firebears.commands.grabber;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinGrabberWheelsCommand extends Command {
	boolean shouldSpin;
    public SpinGrabberWheelsCommand(boolean shouldSpin) {
    	this.shouldSpin = shouldSpin;
    	requires(Robot.grabber);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (this.shouldSpin == true) {
    		Robot.grabber.grabberStartSpinning();
    	} else {
    		Robot.grabber.grabberStopSpinning();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
