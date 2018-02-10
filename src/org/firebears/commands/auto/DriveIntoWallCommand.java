package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveIntoWallCommand extends Command {
	double speed;

	public DriveIntoWallCommand(double speed) {
		requires(Robot.chassis);
		this.speed = speed;

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.chassis.drive(-speed, 0, false);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (RobotController.getInputCurrent() > .45) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.chassis.drive(0, 0,false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
