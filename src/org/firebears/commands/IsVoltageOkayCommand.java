package org.firebears.commands;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Checks the battery voltage and sends a message to the console of the riolog
 */
public class IsVoltageOkayCommand extends Command {
	double volt;
	double check;
	
	public IsVoltageOkayCommand(double check) {
		this.check = check;
	}

	protected void initialize() {
		System.out.println("IsVoltageOkayCommand started");
		RobotMap.TestVoltage = false;
		volt = RobotController.getBatteryVoltage();
		if (volt<= check) {
			System.out.println("Battery Voltage is too low: " + String.format("%6.2f", volt));
		}
	}
	
	protected void execute() {
	}

	protected boolean isFinished() {
		return volt >= check;
	}

	protected void end() {
		RobotMap.TestVoltage = true;
		System.out.println("Battary Status: Nominal");

	}

	protected void interrupted() {
		System.out.println("IsVoltageOkayCommand Interrupted");

	}
}
