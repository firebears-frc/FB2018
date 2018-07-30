package org.firebears.commands.auto;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive forward a certain amount of inches
 */
public class DriveToDistanceCommand extends Command {

	double targetDistance;
	double startingDistance;
	double currentDistance;
	double timeout;
	final double SPEED;
	
	final double LOWER_SPEED = .25;
	final double UPPER_SPEED = .7;
	final double LOWER_OVERSHOOT = 1;
	final double UPPER_OVERSHOOT = 9.5;
	double overshoot;
	

	public DriveToDistanceCommand(double inches,double speed) {
		requires(Robot.chassis);
		targetDistance = inches;
		SPEED = speed;
		
		double overshootPer = (SPEED - LOWER_SPEED) / (UPPER_SPEED - LOWER_SPEED);
		overshoot = overshootPer * (UPPER_OVERSHOOT - LOWER_OVERSHOOT) + LOWER_OVERSHOOT;
		targetDistance = targetDistance - overshoot;
	}

	protected void initialize() {
//		targetDistance = SmartDashboard.getNumber("Target Inches", 0);
//		
//		double overshootPer = (SPEED - LOWER_SPEED) / (UPPER_SPEED - LOWER_SPEED);
//		overshoot = overshootPer * (UPPER_OVERSHOOT - LOWER_OVERSHOOT) + LOWER_OVERSHOOT;
//		targetDistance = targetDistance - overshoot;
		
		timeout = System.currentTimeMillis() + 1000 * 5;
		System.out.println("Starting " + this.toString());
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
		System.out.println(targetDistance);
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
