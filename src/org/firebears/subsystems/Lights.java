package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lights extends Subsystem {
    public static final int MAX_ANIMATIONS = 9;
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

    public Lights() {
	i2c = new I2C(Port.kOnboard, I2C_ADDRESS);
	driverstation = DriverStation.getInstance();
    }

    // public void setShootingMode(boolean shooting) {
    // isShooting = shooting;
    // }

    // public void setFallingMode(boolean falling) {
    // hasShot = falling;
    // if (falling) {
    // fallingTimeout = System.currentTimeMillis() + 2 * 1000L;
    // }
    // }

    public void setCelebrateMode(boolean celebrate) {
	letsCelebrate = celebrate;
    }

    public void setCubeMode(boolean cube) {
	holdingCube = cube;
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
	boolean shootingnow = (((stick.getRawAxis(0) + 1) / 2) > .1);
	if (shootingnow == false && isShooting == true) {
	    hasShot = true;
	    fallingTimeout = System.currentTimeMillis() + 5 * 1000L;
	}
	isShooting = shootingnow;

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
	    if (isShooting) {
		setAnimation(SHOOTER_STRIP, BUILD_ANIMATION);
	    }
	    if (hasShot) {
		setAnimation(SHOOTER_STRIP, FALL_ANIMATION);
		if (System.currentTimeMillis() > fallingTimeout) {
		    hasShot = false;
		}
	    }
	    if (holdingCube) {
		setAnimation(GRABBER_STRIP, CUBE_ANIMATION);
	    }

	}
	sendAllAnimations();
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
