package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tests Either side of the motors by moving them and checking with the encoders
 */
public class IsSimOkayCommand extends Command {

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
		RobotMap.TestDriveSim = false;
		if (forward) {
			direction = 1;
			vector = "Positive";

		} else {
			direction = -1;
			vector = "Negative";
		}

		switch (side) {
		case "left":
			RobotMap.chassisLeftMotors.set(600*10 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector);
			break;
		case "right":
			RobotMap.chassisRightMotors.set(600*10 * direction);
			System.out.println("Chassis Side: " + side + " Set to 610 and direction " + vector);
			break;
		default:
			RobotMap.chassisRightMotors.set(0);
			RobotMap.chassisLeftMotors.set(0);
			System.out.println("Error in isSimOkayCommand: " + side + "is not a side");
			break;
		}

	}

	protected void execute() {
	}

	protected boolean isFinished() {
		if (side == "right") {
			System.out.println(side + "Chassis Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));
		} else {
			System.out.println(side + "Chassis Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));

		}

		return (RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= -600*10
				|| RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= 600*10)
				^ (RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= 600*10
						|| RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= -600*10);// 600 is
																											// close to
																											// max speed

		// if
		// (Math.abs(RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX))
		// >= 600) {
		// System.out.println("");
		// System.out.println(side + "Chassis Motors reached speed in " + forward
		// +"Direction. " +
		// RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
		// return true;
		// }else if
		// (Math.abs(RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX))
		// >= 600){
		// System.out.println("");
		// System.out.println(side + "Chassis Motors reached speed in " + forward
		// +"Direction. " +
		// RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
		// return true;
		// }else {
		// if (side == "Left") {
		// System.out.println(side + "Chassis Motors not reaching speed in " + forward
		// +"Direction!: " +
		// RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
		// }
		// return false;
		// }
	}

	protected void end() {
		RobotMap.chassisRightMotors.set(0);
		RobotMap.chassisLeftMotors.set(0);
		RobotMap.TestDriveSim = true;
		System.out.println("Chassis Motor reached target speed");
		System.out.println("Chassis Motor " + side + " Status: Nominal");

	}

	protected void interrupted() {
		RobotMap.chassisRightMotors.set(0);
		RobotMap.chassisLeftMotors.set(0);
		System.out.println("isSimOkayCommand interrupted");

	}
}
