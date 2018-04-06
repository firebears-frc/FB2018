package org.firebears.commands.testing;

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

	boolean leftForwardNegation = false;
	boolean leftBackwardNegation = false;
	boolean rightForwardNegation = false;
	boolean rightBackwardNegation = false;

	public IsShooterWheelOkayCommand(String side, boolean forward) {
		this.side = side;
		this.forward = forward;
		requires(Robot.shooter);
	}

	protected void initialize() {
//		RobotMap.TestShooterLeftPositive = false;
//		RobotMap.TestShooterLeftNegative = false;
//		RobotMap.TestShooterRightPositive = false;
//		RobotMap.TestShooterRightNegative = false;
		System.out.println("IsShooterWheelOkayCommand started");
		// RobotMap.TestShooter = false;
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

		
		Robot.shooter.leftSpinner.enable();
		Robot.shooter.rightSpinner.enable();
	}

	protected void execute() {

		switch (side) {
		case "left":
//			RobotMap.leftLaunchSpinner.set(1 * direction);
			 Robot.shooter.leftSpinner.setSetpoint(700 * direction);
			// Robot.shooter.shooterSpinWheel(1 * direction);
			System.out.println("Shooter Side: " + side + " Direction: " + vector);
			break;
		case "right":
//			RobotMap.rightLaunchSpinner.set(1 * direction);
			 Robot.shooter.rightSpinner.setSetpoint(700 * direction);
			// Robot.shooter.shooterSpinWheel(1* direction);
			System.out.println("Shooter Side: " + side + " Direction: " + vector);
			break;
		default:
			 Robot.shooter.leftSpinner.setSetpoint(0);
			 Robot.shooter.rightSpinner.setSetpoint(0);
//			RobotMap.rightLaunchSpinner.set(0);
//			RobotMap.leftLaunchSpinner.set(0);

			System.out.println("Error in IsShooterWheelOkayCommand: " + side + "is not a side");
			break;
		}

	}

	protected boolean isFinished() {
		if (side == "right") {
			System.out.println(side + " Shooter Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.rightLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));
		} else if (side == "left") {
			System.out.println(side + " Shooter Motors not reaching speed in " + vector + " Direction!: "
					+ RobotMap.leftLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));

		}

		return ((RobotMap.leftLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 700)
				&& leftForwardNegation)
				|| ((RobotMap.rightLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX) <= -700)
						&& rightBackwardNegation)
				|| ((RobotMap.leftLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX) <= -700)
						&& leftBackwardNegation)
				|| ((RobotMap.rightLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX) >= 700))
						&& rightForwardNegation;

		// return
		// (RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >=
		// 300000
		// || RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >=
		// -300000)
		// ^ (RobotMap.leftLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >=
		// -300000
		// || RobotMap.rightLaunchSpinner.getSelectedSensorPosition(RobotMap.PID_IDX) >=
		// 300000);// max speed
		// is 750ish

	}

	protected void end() {
		// RobotMap.TestShooter = true;

		if (side == "left" && forward == true) {
			RobotMap.TestShooterLeftPositive = true;

		} else if (side == "left" && forward == false) {
			RobotMap.TestShooterLeftNegative = true;

		} else if (side == "right" && forward == true) {
			RobotMap.TestShooterRightPositive = true;

		} else if (side == "right" && forward == false) {
			RobotMap.TestShooterRightNegative = true;

		}

		if (side == "right") {
			System.out.println(side + "Shooter Motors reached speed in " + vector + "Direction. "
					+ RobotMap.rightLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));
		} else if (side == "left") {
			System.out.println(side + "Shooter Motors reached speed in " + vector + "Direction. "
					+ RobotMap.leftLaunchSpinner.getSelectedSensorVelocity(RobotMap.PID_IDX));

		}
//		RobotMap.leftLaunchSpinner.set(0);
//		RobotMap.rightLaunchSpinner.set(0);
		Robot.shooter.leftSpinner.setSetpoint(0);
		Robot.shooter.rightSpinner.setSetpoint(0);

	}

	protected void interrupted() {
		System.out.println("IsShooterWheelOkayCommand Interrupted");
//		RobotMap.leftLaunchSpinner.set(0);
//		RobotMap.rightLaunchSpinner.set(0);
		Robot.shooter.leftSpinner.setSetpoint(0);
		Robot.shooter.rightSpinner.setSetpoint(0);
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}
}
