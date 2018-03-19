package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the shooter wheel and checks if they move in the correct direction and
 * magnitude with the encoders
 */
public class IsShooterWheelOkayCommand extends Command {
	String side;
	String vector;
	boolean forward;
	int direction;

	public IsShooterWheelOkayCommand(String side, boolean forward) {
		this.side = side;
		this.forward = forward;
		requires(Robot.shooter);
	}

	protected void initialize() {
		System.out.println("IsShooterWheelOkayCommand started");
		RobotMap.TestShooter = false;
		if (forward) {
			direction = 1;
			vector = "Positive";

		} else {
			direction = -1;
			vector = "Negative";
		}

		switch (side) {
		case "left":
			Robot.shooter.leftSpinner.setSetpoint(1 * direction);
			System.out.println("Shooter Side: " + side + " Direction: " + vector);
			break;
		case "right":
			Robot.shooter.rightSpinner.setSetpoint(1 * direction);
			System.out.println("Shooter Side: " + side + " Direction: " + vector);
			break;
		default:
			Robot.shooter.leftSpinner.setSetpoint(0);
			Robot.shooter.rightSpinner.setSetpoint(0);
			System.out.println("Error in IsShooterWheelOkayCommand: " + side + "is not a side");
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		if (side == "right") {
			System.out.println(side + "Shooter Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));
		} else {
			System.out.println(side + "Shooter Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));

		}

		return (RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >= 700
				|| RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >= -700)
				^ (RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >= -700
						|| RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >= 700);// max speed
																											// is 750ish

	}

	protected void end() {
		RobotMap.TestShooter = true;

		if (side == "right") {
			System.out.println(side + "Shooter Motors reached speed in " + vector + "Direction. "
					+ RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));
		} else {
			System.out.println(side + "Shooter Motors reached speed in " + vector + "Direction. "
					+ RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX));

		}
		Robot.shooter.leftSpinner.setSetpoint(0);
		Robot.shooter.rightSpinner.setSetpoint(0);
	}

	protected void interrupted() {
		System.out.println("IsShooterWheelOkayCommand Interrupted");
		Robot.shooter.leftSpinner.setSetpoint(0);
		Robot.shooter.rightSpinner.setSetpoint(0);

	}
}
