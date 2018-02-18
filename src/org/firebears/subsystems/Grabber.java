package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.commands.grabber.AutoPullCubeCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {

	final double MOTORSPEED = .5;
	final double SLOW_SPEED = .05;
	final Value SOL_FORWARD = DoubleSolenoid.Value.kForward;
	final Value SOL_REVERSE = DoubleSolenoid.Value.kReverse;

	// to-do
	public void grabberRaise() {
		RobotMap.leftUpDown.set(SOL_FORWARD);
		RobotMap.rightUpDown.set(SOL_FORWARD);
		Robot.lights.setCubeMode(true);
	}

	public void grabberLower() {
		RobotMap.leftUpDown.set(SOL_REVERSE);
		RobotMap.rightUpDown.set(SOL_REVERSE);
		Robot.lights.setCubeMode(false);
	}

	public void grabberOpen() {
		RobotMap.leftOpenClose.set(SOL_FORWARD);
		RobotMap.rightOpenClose.set(SOL_FORWARD);
	}

	public void grabberClose() {
		RobotMap.leftOpenClose.set(SOL_REVERSE);
		RobotMap.rightOpenClose.set(SOL_REVERSE);
	}

	public void grabberStartSpinning() {
		RobotMap.leftIntake.set(-MOTORSPEED);
		RobotMap.rightIntake.set(MOTORSPEED);
	}
	
	public void grabberSlowSpin() {
		RobotMap.leftIntake.set(-SLOW_SPEED);
		RobotMap.rightIntake.set(SLOW_SPEED);
	}

	public void grabberStopSpinning() {
		RobotMap.leftIntake.set(0);
		RobotMap.rightIntake.set(0);
	}
	
	public boolean hasCube() {
		return RobotMap.cubeSwitch.get();
	}
	
	public boolean isRaised() {
		return RobotMap.rightUpDown.get() == SOL_FORWARD;
	}

	public void initDefaultCommand() {
//		setDefaultCommand(new AutoPullCubeCommand());
	}
}
