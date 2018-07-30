package org.firebears.commands.grabber;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoPullCubeCommand extends Command {
	
    public AutoPullCubeCommand() {
        requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.grabber.isRaised() && RobotMap.cubeSwitch.get() == false) {
    		Robot.grabber.grabberSlowSpin();
    	} else if (Robot.grabber.isRaised() && RobotMap.cubeSwitch.get()) {
    		Robot.grabber.grabberStopSpinning();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
