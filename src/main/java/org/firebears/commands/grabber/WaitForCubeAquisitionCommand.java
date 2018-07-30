package org.firebears.commands.grabber;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitForCubeAquisitionCommand extends Command {

    public WaitForCubeAquisitionCommand() {
    }
    protected void initialize() {
    }
    protected void execute() {
    }
    protected boolean isFinished() {    	
        return Robot.grabber.hasCube();
//    	return true;
    }
    protected void end() {
    }
    protected void interrupted() {
    }
}
