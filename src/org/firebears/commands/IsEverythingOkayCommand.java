package org.firebears.commands;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Prints everything that has been tested.
 */
public class IsEverythingOkayCommand extends Command {

	public IsEverythingOkayCommand() {

	}

	protected void initialize() {
		System.out.println("Check list of all tested items:");
		System.out.println("	Voltage Test Status:....." + check(RobotMap.TestVoltage));
		System.out.println("	DriveSim Test Status:...." + check(RobotMap.TestDriveSim));
		System.out.println("	Navx Test Status:........" + check(RobotMap.TestNavx));
		System.out.println("	Shooter Test Status:....." + check(RobotMap.TestShooter));
		System.out.println("	Solenoid Test Status:...." + check(RobotMap.TestSolenoid));
		System.out.println("	Ultrasonic Test Status:.." + check(RobotMap.TestUltrasonic));
		System.out.println("	Vision Test Status:......" + check(RobotMap.TestVision));
		System.out.println("	Pressure Test Status:...." + check(RobotMap.TestPressure));
		if (RobotMap.TestVoltage && RobotMap.TestDriveSim && RobotMap.TestNavx && RobotMap.TestShooter && RobotMap.TestSolenoid && RobotMap.TestUltrasonic && RobotMap.TestVision && RobotMap.TestPressure) {
			System.out.println("Robot is ready to Drive");
		}else {
			System.out.println("Robot Test failed");
		}

	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
	protected String check(boolean input) {
		if (input) {
			return "PASSED";
		}else {
			return "FAILED";
		}
	}
}
