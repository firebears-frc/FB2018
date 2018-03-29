package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tests Either side of the motors by moving them and checking with the encoders
 */
public class IsSimOkayCommand extends Command {

	boolean leftForwardNegation = false;
	boolean leftBackwardNegation = false;
	boolean rightForwardNegation = false;
	boolean rightBackwardNegation = false;
	
	String side;
	String vector;
	boolean forward;
	int direction;

	public IsSimOkayCommand(String side, boolean forward) {
		this.side = side;
		this.forward = forward;
		requires(Robot.chassis);
	}

	protected void initialize() {
		System.out.println("IsSimOkayCommand started");
		// RobotMap.TestDriveSim = false;
		if (forward) {
			direction = 1;
			vector = "Positive";

		} else {
			direction = -1;
			vector = "Negative";
		}

		if (side == "left" && forward == true) {
			leftForwardNegation = true;
		} else if (side == "left" && forward == false) {
			leftBackwardNegation = true;
		} else if (side == "right" && forward == true) {
			rightForwardNegation = true;
		} else if (side == "right" && forward == false) {
			rightBackwardNegation = true;
		}

	}

	protected void execute() {
		
		
		switch (side) {
		case "left front":
//			RobotMap.chassisLeftMaster.set(1 * direction);
			Robot.chassis.leftFront(1 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector + " " + RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
			break;
		case "left rear":
//			RobotMap.chassisLeftSlave.set(1 * direction);
			Robot.chassis.leftRear(1 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector + " " + RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
			break;
		case "right front":
//			RobotMap.chassisRightMaster.set(1 * direction);
			Robot.chassis.rightFront(1 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector + " " + RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
			break;
		case "right rear":
//			RobotMap.chassisRightSlave.set(1 * direction);
			Robot.chassis.rightRear(1 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector + " " + RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
			break;
		default:
			// RobotMap.chassisRightMotors.set(0);
			// RobotMap.chassisLeftMotors.set(0);
//			RobotMap.chassisLeftMaster.set(0);
//			RobotMap.chassisLeftSlave.set(0);
//			RobotMap.chassisRightMaster.set(0);
//			RobotMap.chassisRightSlave.set(0);
			Robot.chassis.leftFront(0);
			Robot.chassis.leftRear(0);
			Robot.chassis.rightFront(0);
			Robot.chassis.rightRear(0);
			System.out.println("Error in isSimOkayCommand: " + side + "is not a side");
			break;
		}
		
		
	}

	protected boolean isFinished() {
//		if (side == "right ") {
//			System.out.println(side + "Chassis Motors not reaching speed in " + vector + " Direction!: "
//					+ RobotMap.rightLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));
//		} else {
//			System.out.println(side + "Chassis Motors not reaching speed in " + vector + " Direction!: "
//					+ RobotMap.leftLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));
//
//		}
		
//		System.out.println(side + "Chassis Motors not reaching speed in " + vector + " Direction!");
//
		
		return ((RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 600)
				&& leftForwardNegation)
				|| ((RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= -600)
						&& rightBackwardNegation)
				|| ((RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= -600)
						&& leftBackwardNegation)
				|| ((RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 600))
						&& rightForwardNegation;
		
		
		
//		return (RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= -600 * 10
//				|| RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 600 * 10)
//				^ (RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 600 * 10
//						|| RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX) >= -600 * 10);// 600
																													// is
																													// close
																													// to
																													// max
																													// speed

		// if
		// (Math.abs(RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX))
		// >= 600) {
		// System.out.println("");
		// System.out.println(side + "Chassis Motors reached speed in " + forward
		// +"Direction. " +
		// RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
		// return true;
		// }else if
		// (Math.abs(RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX))
		// >= 600){
		// System.out.println("");
		// System.out.println(side + "Chassis Motors reached speed in " + forward
		// +"Direction. " +
		// RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
		// return true;
		// }else {
		// if (side == "Left") {
		// System.out.println(side + "Chassis Motors not reaching speed in " + forward
		// +"Direction!: " +
		// RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));
		// }
		// return false;
		// }
	}

	protected void end() {
		// RobotMap.chassisRightMotors.set(0);
		// RobotMap.chassisLeftMotors.set(0);

//		RobotMap.chassisLeftMaster.set(0);
//		RobotMap.chassisLeftSlave.set(0);
//		RobotMap.chassisRightMaster.set(0);
//		RobotMap.chassisRightSlave.set(0);
		Robot.chassis.leftFront(0);
		Robot.chassis.leftRear(0);
		Robot.chassis.rightFront(0);
		Robot.chassis.rightRear(0);





		// RobotMap.TestDriveSim = true;
		// RobotMap.TestDriveSimLeftMasterPositive = true;

		if (side == "left front" && forward == true) {
			RobotMap.TestDriveSimLeftMasterPositive = true;
			
		} else if (side == "left front" && forward == false) {
			RobotMap.TestDriveSimLeftMasterNegative = true;
			
		} else if (side == "left rear" && forward == true) {
			RobotMap.TestDriveSimLeftSlavePositive = true;
			
		} else if (side == "left rear" && forward == false) {
			RobotMap.TestDriveSimLeftSlaveNegative = true;
			
		} else if (side == "right front" && forward == true) {
			RobotMap.TestDriveSimRightMasterPositive = true;
			
		} else if (side == "right front" && forward == false) {
			RobotMap.TestDriveSimRightMasterNegative = true;
			
		} else if (side == "right rear" && forward == true) {
			RobotMap.TestDriveSimRightSlavePositive = true;
			
		} else if (side == "right rear" && forward == false) {
			RobotMap.TestDriveSimRightSlaveNegative = true;
			
		}

		System.out.println("Chassis Motor reached target speed");
		System.out.println("Chassis Motor " + side +" in " + forward + " Direction." + " Status: Nominal");

	}

	protected void interrupted() {
		// RobotMap.chassisRightMotors.set(0);
		// RobotMap.chassisLeftMotors.set(0);

//		RobotMap.chassisLeftMaster.set(0);
//		RobotMap.chassisLeftSlave.set(0);
//		RobotMap.chassisRightMaster.set(0);
//		RobotMap.chassisRightSlave.set(0);

		Robot.chassis.leftFront(0);
		Robot.chassis.leftRear(0);
		Robot.chassis.rightFront(0);
		Robot.chassis.rightRear(0);
		
		System.out.println("isSimOkayCommand interrupted");
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}
}
