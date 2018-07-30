package org.firebears.commands;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActivateCompressor extends Command {

	boolean enable;
	
    public ActivateCompressor(boolean enable) {
        this.enable = enable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (enable) {
    		RobotMap.compressor.start();
    	} else {
    		RobotMap.compressor.stop();
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
