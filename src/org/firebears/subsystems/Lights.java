package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lights extends Subsystem {
	public static final int MAX_ANIMATIONS = 15;
	public static final int MAX_PIXELSTRIPS = 3;
	public static final int BRIGHTNESS = 125;
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
	public static final int RLOW_ANIMATION = 9;
	public static final int RMID_ANIMATION = 10;
	public static final int RHIGH_ANIMATION = 11;
	public static final int BLOW_ANIMATION = 12;
	public static final int BMID_ANIMATION = 13;
	public static final int BHIGH_ANIMATION = 14;

	public static final int SHOOTER_STRIP = 0;
	public static final int GRABBER_STRIP = 1;
	public static final int BASE_STRIP = 2;

	private boolean letsCelebrate = false;
	private boolean isShooting = false;
	private boolean hasShot = false;
	private boolean holdingCube = false;

	public final I2C i2c;
	final DriverStation driverstation;
	private long fallingTimeout = 0;
	private long celebrateTimeout = 0;
	private long cubeTimeout = 0;

	public Lights() {
		i2c = new I2C(Port.kOnboard, I2C_ADDRESS);
		driverstation = DriverStation.getInstance();
	}

	public void setCelebrateMode(boolean celebrate) {
		letsCelebrate = celebrate;
		if (celebrate) {
			celebrateTimeout = System.currentTimeMillis() + 3 * 1000L;
		}
	}

	public void setCubeMode(boolean cube) {
		holdingCube = cube;
		if (cube) {
			cubeTimeout = System.currentTimeMillis() + 3 * 1000L;
		}
	}

	public void initDefaultCommand() {

	}

	private byte[] dataOut = new byte[2];
	private byte[] dataBack = new byte[0];
	private int[] currentAnimation = new int[MAX_PIXELSTRIPS];
	private int[] nextAnimation = new int[MAX_PIXELSTRIPS];

	public synchronized void setAnimation(int s, int a) {
		nextAnimation[s] = a;
	}

	public synchronized boolean sendAllAnimations() {
		boolean messagesSent = false;
		for (int s = 0; s < MAX_PIXELSTRIPS; s++) {
			if (nextAnimation[s] != currentAnimation[s]) {
				int a = nextAnimation[s];
				dataOut[0] = (byte) (s + '0');
				dataOut[1] = (byte) (a + '0');
				i2c.transaction(dataOut, dataOut.length, dataBack, dataBack.length);
				currentAnimation[s] = a;
				if (RobotMap.DEBUG) {
					System.out.printf("Lights: setAnimation(%d, %d)%n", s, a);
				}
				messagesSent = true;
			}
		}
		if (messagesSent && RobotMap.DEBUG) {
			System.out.printf("Lights: isShooting=%b hasShot=%b holdingCube=%b %n%n", isShooting, hasShot, holdingCube);
		}
		return messagesSent;
	}

	@Override
	public void periodic() {
		Joystick stick = Robot.oi.joystick2;
		double shooterSpeed = ((stick.getRawAxis(0) + 1) / 2);
		boolean shootingnow = shooterSpeed > 0.1;
		if (shootingnow == false && isShooting == true) {
			hasShot = true;
			fallingTimeout = System.currentTimeMillis() + 5 * 1000L;
		}
		isShooting = shootingnow;

		if (celebrateTimeout > 0) {
			setAnimation(SHOOTER_STRIP, CELEBRATE_ANIMATION);
			setAnimation(GRABBER_STRIP, CELEBRATE_ANIMATION);
			setAnimation(BASE_STRIP, CELEBRATE_ANIMATION);
			if (System.currentTimeMillis() > celebrateTimeout) {
				letsCelebrate = false;
				celebrateTimeout = 0;
			}
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
			if (isShooting) {
				setAnimation(SHOOTER_STRIP, BUILD_ANIMATION);
				setAnimation(BASE_STRIP, getSpeedAnimation(shooterSpeed));
			}
			if (hasShot) {
				setAnimation(SHOOTER_STRIP, FALL_ANIMATION);
				if (System.currentTimeMillis() > fallingTimeout) {
					hasShot = false;
				}
			}
			if (cubeTimeout > 0) {
				setAnimation(GRABBER_STRIP, CUBE_ANIMATION);
				setAnimation(BASE_STRIP, CUBE_ANIMATION);
				if (System.currentTimeMillis() > cubeTimeout) {
					holdingCube = false;
					cubeTimeout = 0;
				}

			}
			sendAllAnimations();

		}
	}

	private int getSpeedAnimation(double shooterSpeed) {
		if (shooterSpeed < 0.4) {
			if (driverstation.getAlliance() == DriverStation.Alliance.Blue) {
				return BLOW_ANIMATION;
			} else {
				return RLOW_ANIMATION;
			}
		} else if (shooterSpeed < 0.7) {
			if (driverstation.getAlliance() == DriverStation.Alliance.Blue) {
				return BMID_ANIMATION;
			} else {
				return RMID_ANIMATION;
			}
		} else {
			if (driverstation.getAlliance() == DriverStation.Alliance.Blue) {
				return BHIGH_ANIMATION;
			} else {
				return RHIGH_ANIMATION;
			}
		}
	}

	public void reset() {
		for (int s = 0; s < MAX_PIXELSTRIPS; s++) {
			currentAnimation[s] = -1;
			nextAnimation[s] = currentAnimation[s];
		}
		setAnimation(SHOOTER_STRIP, FIRE_ANIMATION);
		setAnimation(GRABBER_STRIP, FIRE_ANIMATION);
		setAnimation(BASE_STRIP, FIRE_ANIMATION);
	}
}
