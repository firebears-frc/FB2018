package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lights extends Subsystem {
	public static final int MAX_ANIMATIONS = 9;
	public static final int MAX_PIXELSTRIPS = 3;
	public static final int BRIGHTNESS = 128;
	public static final int I2C_ADDRESS = 4;
	public static final int BUILD_ANIMATION = 0;
	public static final int CELEBRATE_ANIMATION = 1;
	public static final int CUBE_ANIMATION = 2;
	public static final int FIRE_ANIMATION = 3;
	public static final int FALL_ANIMATION = 4;
	public static final int NULL_ANIMATION = 5;
	public static final int PACMAN_ANIMATION = 6;
	public static final int BLUE_ANIMATION = 7;
	public static final int RED_ANIMATION = 8;

	public static final int SHOOTER_STRIP = 0;
	public static final int GRABBER_STRIP = 1;
	public static final int BASE_STRIP = 2;

	private boolean letsCelebrate = false;

	public final I2C i2c;
	final DriverStation driverstation;

	public Lights() {
		i2c = new I2C(Port.kOnboard, I2C_ADDRESS);
		driverstation = DriverStation.getInstance();
	}

	public void setCelebrateMode(boolean celebrate) {
		letsCelebrate = celebrate;
	}

	public void initDefaultCommand() {

	}

	private byte[] dataOut = new byte[2];
	private byte[] dataBack = new byte[0];
	private int[] currentAnimation = new int[MAX_PIXELSTRIPS];

	public synchronized void setAnimation(int s, int a) {
		if (currentAnimation[s] == a) {
			return;
		}
		dataOut[0] = (byte) (s + '0');
		dataOut[1] = (byte) (a + '0');
		i2c.transaction(dataOut, dataOut.length, dataBack, dataBack.length);
		currentAnimation[s] = a;
	}

	@Override
	public void periodic() {

		if (letsCelebrate) {
			setAnimation(SHOOTER_STRIP, CELEBRATE_ANIMATION);
			setAnimation(GRABBER_STRIP, CELEBRATE_ANIMATION);
			setAnimation(BASE_STRIP, CELEBRATE_ANIMATION);
		} else if (driverstation.isDisabled()) {
			setAnimation(SHOOTER_STRIP, PACMAN_ANIMATION);
			setAnimation(GRABBER_STRIP, FIRE_ANIMATION);
			setAnimation(BASE_STRIP, FIRE_ANIMATION);
		} else if (driverstation.isAutonomous() || driverstation.isOperatorControl()) {
			if (Robot.chassis.isTapeBright()) {
				setAnimation(SHOOTER_STRIP, NULL_ANIMATION);
				setAnimation(GRABBER_STRIP, NULL_ANIMATION);
				setAnimation(BASE_STRIP, NULL_ANIMATION);
			} else if (driverstation.getAlliance() == DriverStation.Alliance.Blue) {
				setAnimation(SHOOTER_STRIP, BLUE_ANIMATION);
				setAnimation(GRABBER_STRIP, BLUE_ANIMATION);
				setAnimation(BASE_STRIP, BLUE_ANIMATION);
			} else if (driverstation.getAlliance() == DriverStation.Alliance.Red) {
				setAnimation(SHOOTER_STRIP, RED_ANIMATION);
				setAnimation(GRABBER_STRIP, RED_ANIMATION);
				setAnimation(BASE_STRIP, RED_ANIMATION);
			}

		}

	}

	public void reset() {
		for (int i = 0; i < MAX_PIXELSTRIPS; i++) {
			currentAnimation[i] = -1;
		}
		setAnimation(SHOOTER_STRIP, FIRE_ANIMATION);
		setAnimation(GRABBER_STRIP, FIRE_ANIMATION);
		setAnimation(BASE_STRIP, FIRE_ANIMATION);
	}
}
