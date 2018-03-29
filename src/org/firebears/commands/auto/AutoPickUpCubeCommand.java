package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoPickUpCubeCommand extends Command {

	double timer;
	
    public AutoPickUpCubeCommand() {
        requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = 0;
    	Robot.grabber.grabberLower();
    	Robot.grabber.grabberOpen();
    	Robot.grabber.grabberStartSpinning();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.grabber.cubeInRange()) {
    		System.out.println("Closing Grabber");
    		Robot.grabber.grabberClose();
    	}
    	if (Robot.grabber.hasCube()) {
    		System.out.println("Raising Grabber");
    		Robot.grabber.grabberRaise();
    		Robot.grabber.grabberStopSpinning();
    	}
    	timer ++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.grabber.isRaised() && timer >= 150) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
