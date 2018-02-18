package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {

	final double MOTORSPEED = 1;
	final Value SOL_FORWARD = DoubleSolenoid.Value.kForward;
	final Value SOL_REVERSE = DoubleSolenoid.Value.kReverse;

	// to-do
	public void grabberRaise() {
		RobotMap.leftUpDown.set(SOL_REVERSE);
		RobotMap.rightUpDown.set(SOL_REVERSE);
		Robot.lights.setCubeMode(true);
	}

	public void grabberLower() {
		RobotMap.leftUpDown.set(SOL_FORWARD);
		RobotMap.rightUpDown.set(SOL_FORWARD);
		Robot.lights.setCubeMode(false);
	}

	public void grabberOpen() {
		RobotMap.leftOpenClose.set(SOL_REVERSE);
		RobotMap.rightOpenClose.set(SOL_REVERSE);
	}

	public void grabberClose() {
		RobotMap.leftOpenClose.set(SOL_FORWARD);
		RobotMap.rightOpenClose.set(SOL_FORWARD);
	}

	public void grabberStartSpinning() {
		RobotMap.leftIntake.set(MOTORSPEED);
		RobotMap.rightIntake.set(MOTORSPEED);
	}

	public void grabberStopSpinning() {
		RobotMap.leftIntake.set(0);
		RobotMap.rightIntake.set(0);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
