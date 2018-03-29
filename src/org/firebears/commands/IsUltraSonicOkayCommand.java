package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tests the ultrasonic by moving an object less then 12 inches close to the
 * sensor
 */
public class IsUltraSonicOkayCommand extends Command {
	double dis;
	double m_ema;
	int us;

	public IsUltraSonicOkayCommand(int us) {
		this.us = us;
		requires(Robot.chassis);
		requires(Robot.grabber);
	}

	protected void initialize() {
		System.out.println("IsUltraSonicOkayCommand started");
		RobotMap.TestUltrasonic = false;
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		if (us == 1) {
			dis = Robot.chassis.getChassisRangeFinderDistance();
		} else if (us == 2) {
			dis = Robot.grabber.getGrabberRangeFinderDistance();
		} else {
			System.out.println("Error in IsUltraSonicOkayCommand: " + us + "is not an Ultrasonic sensor");
		}
		return dis <= 12;
	}

	protected void end() {
		RobotMap.TestUltrasonic = true;

		System.out.println("UltraSonic Reached less then 12 inches");
		System.out.println("UltraSonic " + us + " Status: Nominal");

	}

	protected void interrupted() {
		System.out.println("IsUltraSonicOkayCommand interrupted");
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}
}
