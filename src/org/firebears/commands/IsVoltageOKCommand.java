package org.firebears.commands;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IsVoltageOKCommand extends Command {
	double volt;
	public IsVoltageOKCommand() {


	}

	// Called just before this Command runs the first time
	protected void initialize() {
		volt = RobotController.getBatteryVoltage();


	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (volt >= 13) {
			System.out.println("Voltage is above 13");
			return true;
		}else {
			System.out.println("Voltage is below 13");
			return false;

		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
