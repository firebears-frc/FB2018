package org.firebears.commands.testing;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IsVisionOkayCommand extends Command {

	public IsVisionOkayCommand() {

	}

	protected void initialize() {
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;
	}
}
