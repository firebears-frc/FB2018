package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Store the current gyro direction, taken from the NavX board.
 */
public class GetRotationCommand extends Command {

    public GetRotationCommand() {
	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	RobotMap.rotation = RobotMap.navXBoard.getAngle();
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
