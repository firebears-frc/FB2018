package org.firebears.commands.auto;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */


public class ChangeSide extends Command {
	
	String side;
	
    public ChangeSide(String side) {
    	this.side = side;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	RobotMap.side = side;
    	
    	System.out.println(this.toString());
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
    
    public String toString() {
    	return "Change Side to " + side;
    }
}
