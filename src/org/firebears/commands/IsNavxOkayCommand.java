package org.firebears.commands;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tests the Navx by seeing if the value twitches
 */
public class IsNavxOkayCommand extends Command {
	double value;

	public IsNavxOkayCommand() {
	}

	protected void initialize() {
		System.out.println("IsNavxOkayCommand started");
		RobotMap.TestNavx = false;
		value = RobotMap.navXBoard.getYaw();
		System.out.println("Starting Navx angle: " + value);

	}

	protected void execute() {
	}

	protected boolean isFinished() {
		System.out.println("Current Navx angle: " + RobotMap.navXBoard.getYaw());
		return RobotMap.navXBoard.getYaw() != value;
	}

	protected void end() {
		RobotMap.TestNavx = true;

		System.out.println("Navx is seeing values");
		System.out.println("Navx Status: Nominal");
	}

	protected void interrupted() {
		System.out.println("IsNavxOkayCommand interrupted");

	}
}
