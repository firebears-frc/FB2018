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
	double timeout;
	final double SPEED;
	

	public DriveToDistanceCommand(double inches,double speed) {
		requires(Robot.chassis);
		targetDistance = inches;
		SPEED = speed;
	}

	protected void initialize() {
		timeout = System.currentTimeMillis() + 1000 * 5;
		System.out.println("Starting " + this.toString());
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
	}

	protected void execute() {
		Robot.chassis.drive(-SPEED, 0,false);
	}

	protected boolean isFinished() {
		if (inchesTraveled() >= targetDistance) {
			return true;
		} else if (System.currentTimeMillis() >= timeout) {
			return true;
		}
		return false;
	}

	protected void end() {
		Robot.chassis.drive(0, 0,false);
		System.out.println("Ending " + this);
//		Robot.chassis.stop();
	}

	protected void interrupted() {
		end();
		System.out.println("Was interrupted");
	}

	private double inchesTraveled() {
		currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		return (currentDistance - startingDistance) / 52.6;
	}
	
	public String toString() {
		return "DriveToDistanceCommand: " + targetDistance + " inches, " + SPEED + " speed"; 
	}
}
