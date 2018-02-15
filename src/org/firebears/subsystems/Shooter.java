package org.firebears.subsystems;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

	public final double HIGH_SPEED = 1;
	public final double LOW_SPEED = 0.25;

	public void shooterSpinWheel(double speed) {
		RobotMap.leftLaunchSpinner.set(speed);
		RobotMap.rightLaunchSpinner.set(speed);
	}

	public void shooterStopWheel() {
		RobotMap.leftLaunchSpinner.set(0);
		RobotMap.rightLaunchSpinner.set(0);
	}
	
	public void isShooterAtSpeed() {
		
	}

	public void shooterPneumaticsUp() {

	}

	public void shooterPneumaticsDown() {

	}

	public void initDefaultCommand() {

	}
}
