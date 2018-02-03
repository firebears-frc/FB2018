package org.firebears.commands.auto;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward a certain amount of inches
 */
public class DriveToDistanceCommand extends Command {

	double targetDistance;
	int startingDistance;
	int currentDistance;

	public DriveToDistanceCommand(int inches) {
		requires(Robot.chassis);
		targetDistance = inches;
	}

	protected void initialize() {
		System.out.println("Driving distance command");
		startingDistance = RobotMap.chassisFrontLeft.getSelectedSensorPosition(RobotMap.PID_IDX);
	}

	protected void execute() {
		Robot.chassis.drive(-0.5, 0);
	}

	protected boolean isFinished() {
		return inchesTraveled() >= targetDistance;
	}

	protected void end() {
		Robot.chassis.stop();
	}

	protected void interrupted() {
		end();
	}

	private double inchesTraveled() {
		currentDistance = RobotMap.chassisFrontLeft.getSelectedSensorPosition(RobotMap.PID_IDX);
		return (currentDistance - startingDistance) / 52.6;
	}
}
