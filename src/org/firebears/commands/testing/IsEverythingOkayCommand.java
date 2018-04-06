package org.firebears.commands.testing;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Prints everything that has been tested.
 */
public class IsEverythingOkayCommand extends Command {

	public IsEverythingOkayCommand() {

	}

	protected void initialize() {
		System.out.println("Check list of all tested items:");
		
		System.out.println("	Voltage Test Status:....." + check(RobotMap.TestVoltage));

		
		System.out.println("Drive=================");
		
		System.out.println("			DriveSim LeftMaster In Positive Test Status:...."
				+ check(RobotMap.TestDriveSimLeftMasterPositive));
		System.out.println("			DriveSim LeftMaster In Negative Test Status:...."
				+ check(RobotMap.TestDriveSimLeftMasterNegative));
		System.out.println("			DriveSim LeftSlave In Positive Test Status:...."
				+ check(RobotMap.TestDriveSimLeftSlavePositive));
		System.out.println("			DriveSim LeftSlave In Negative Test Status:...."
				+ check(RobotMap.TestDriveSimLeftSlaveNegative));

		System.out.println("			DriveSim RightMaster In Positive Test Status:...."
				+ check(RobotMap.TestDriveSimRightMasterPositive));
		System.out.println("			DriveSim RightMaster In Negative Test Status:...."
				+ check(RobotMap.TestDriveSimRightMasterNegative));
		System.out.println("			DriveSim RightMaster In Positive Test Status:...."
				+ check(RobotMap.TestDriveSimRightSlavePositive));
		System.out.println("			DriveSim RightMaster In Negative Test Status:...."
				+ check(RobotMap.TestDriveSimRightSlaveNegative));

		if (RobotMap.TestDriveSimLeftMasterPositive && RobotMap.TestDriveSimLeftMasterNegative
				&& RobotMap.TestDriveSimLeftSlavePositive && RobotMap.TestDriveSimLeftSlaveNegative
				&& RobotMap.TestDriveSimRightMasterPositive && RobotMap.TestDriveSimRightMasterNegative
				&& RobotMap.TestDriveSimRightSlavePositive && RobotMap.TestDriveSimRightSlaveNegative) {
			RobotMap.TestDriveSim = true;
		}
		System.out.println("		DriveSim Test Status:...." + check(RobotMap.TestDriveSim));
		
		System.out.println("Shooter=================");

		
		System.out.println("			Shooter Left Positive Test Status:....." + check(RobotMap.TestShooterLeftPositive));
		System.out.println("			Shooter Left Negative Test Status:....." + check(RobotMap.TestShooterLeftNegative));
		System.out.println("			Shooter Right Positive Test Status:....." + check(RobotMap.TestShooterRightPositive));
		System.out.println("			Shooter Right Negative Test Status:....." + check(RobotMap.TestShooterRightNegative));

		if (RobotMap.TestShooterLeftPositive && RobotMap.TestShooterLeftNegative && RobotMap.TestShooterRightPositive && RobotMap.TestShooterRightNegative) {
			RobotMap.TestShooter = true;
		}
		
		System.out.println("		Shooter Test Status:....." + check(RobotMap.TestShooter));
	
		System.out.println("Solenoid=================" + check(RobotMap.TestSolenoid));

		
		System.out.println("	Solenoid UP Test Status:...." + check(RobotMap.TestSolenoidGrabberVertUp));
		System.out.println("	Solenoid DOWN Test Status:...." + check(RobotMap.TestSolenoidGrabberVertDown));

		
		
		if (RobotMap.TestSolenoidGrabberVertUp && RobotMap.TestSolenoidGrabberVertDown) {
			RobotMap.TestSolenoid = true;
		}
		System.out.println("		Solenoid Test Status:...." + check(RobotMap.TestSolenoid));

		
		

		
		System.out.println("	Navx Test Status:........" + check(RobotMap.TestNavx));
		System.out.println("	Ultrasonic Test Status:.." + check(RobotMap.TestUltrasonic));
		System.out.println("	Vision Test Status:......" + check(RobotMap.TestVision));
		System.out.println("	Pressure Test Status:...." + check(RobotMap.TestPressure));
		System.out.println("	Lightar Test Status:...." + check(RobotMap.TestLightar));


		if (RobotMap.TestVoltage && RobotMap.TestDriveSim && RobotMap.TestNavx && RobotMap.TestShooter
				&& RobotMap.TestSolenoid && RobotMap.TestUltrasonic && RobotMap.TestVision && RobotMap.TestPressure) {
			System.out.println("Robot is ready to Drive");
		} else {
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
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}

	protected String check(boolean input) {
		if (input) {
			return "PASSED";
		} else {
			return "FAILED";
		}
	}
}
