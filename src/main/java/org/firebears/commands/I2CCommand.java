package org.firebears.commands;

import org.firebears.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * changes light animations
 */
public class I2CCommand extends Command {

	private final int strip;
	private final int animation;

	public I2CCommand(int s, int a) {
		strip = s;
		animation = a;

	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.lights.setAnimation(strip, animation);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
