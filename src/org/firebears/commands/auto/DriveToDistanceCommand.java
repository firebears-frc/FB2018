package org.firebears.commands.auto;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward a certain amount of inches
 */
public class DriveToDistanceCommand extends Command {

	final double targetDistance;
	double startingDistance;
	double currentDistance;
	final double SPEED;

	public DriveToDistanceCommand(double inches,double speed) {
		requires(Robot.chassis);
		targetDistance = inches;
		SPEED = speed;
	}

	protected void initialize() {
		System.out.println("Driving distance command");
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
	}

	protected void execute() {
		Robot.chassis.drive(-SPEED, 0,false);
	}

	protected boolean isFinished() {
		return inchesTraveled() >= targetDistance;
	}

	protected void end() {
		Robot.chassis.drive(0, 0,false);
//		Robot.chassis.stop();
	}

	protected void interrupted() {
		end();
	}

	private double inchesTraveled() {
		currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		return (currentDistance - startingDistance) / 52.6;
	}
}
