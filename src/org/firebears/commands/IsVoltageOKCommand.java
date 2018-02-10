package org.firebears.commands;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Checks the battery voltage and sends a message to the console of the rio
 */
public class IsVoltageOKCommand extends Command {
	double volt;
	int x;
	double check;
	
	public IsVoltageOKCommand(double check) {
		this.check = check;
		
	}

	protected void initialize() {
		volt = RobotController.getBatteryVoltage();
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		if (volt >= check) {
			System.out.println("Battery Voltage Good: " + String.format("%6.2f", volt));
			return true;
		} else {
			x++;
			if (x < 1) {
				System.out.println("Check Battery!: " + String.format("%6.2f", volt));
			}
			return false;
		}
	}

	protected void end() {
		x = 0;
	}

	protected void interrupted() {
		end();
	}
}
