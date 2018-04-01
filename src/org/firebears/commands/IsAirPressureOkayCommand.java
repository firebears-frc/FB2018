package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Checks Air Pressure
 */
public class IsAirPressureOkayCommand extends Command {
	
	double CHECK = 100;
	double pressure;
	public IsAirPressureOkayCommand() {

	}

	protected void initialize() {
		pressure = Robot.chassis.getAirPressure();
		System.out.println("IsAirPressureOkayCommand started");
		RobotMap.TestPressure = false;
		if (pressure < CHECK) {
			System.out.println("Pressure is too low: " + String.format("%6.2f", pressure));

		}

	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return pressure > CHECK;
	}

	protected void end() {
		System.out.println("Pressure Status: Nominal");
		RobotMap.TestPressure = true;


	}

	protected void interrupted() {
		System.out.println("IsVoltageOkayCommand Interrupted");
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}
}
