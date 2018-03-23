// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.firebears.subsystems;

import org.firebears.RobotMap;
import org.firebears.commands.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends Subsystem {

	private double driveMove;
	private double driveRotate;

	private final WPI_TalonSRX frontLeft = RobotMap.chassisLeftMaster;
	private final WPI_TalonSRX rearLeft = RobotMap.chassisLeftSlave;
	private final SpeedControllerGroup leftMotors = RobotMap.chassisLeftMotors;
	private final WPI_TalonSRX frontRight = RobotMap.chassisRightMaster;
	private final WPI_TalonSRX rearRight = RobotMap.chassisRightSlave;
	private final SpeedControllerGroup rightMotors = RobotMap.chassisRightMotors;
	private final DifferentialDrive robotDrive = RobotMap.chassisRobotDrive;
	private final DigitalInput tapeSensor = RobotMap.tapeSensor;

	public boolean isTapeBright() {
		// Return true when detecting light tape
		return tapeSensor.get();
//		return false;

	}

	public double getDriveMove() {
		return driveMove;
	}

	public double getDriveRotate() {
		return driveRotate;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveCommand());
	}

	@Override
    public void periodic() {
        periodicCount++;
        if (periodicCount % 25 == 0) {
            SmartDashboard.putNumber("Air Pressure", getAirPressure());
        }
//        if (periodicCount % 250 == 0) {
//            System.out.printf("Chassis: Air Pressure = %5.2f%n", getAirPressure());
//        }  
    }
	private long periodicCount = 0;

	/**
	 * @return Returns the voltage of the range finder. Returns 0.0 if the value is
	 *         null.
	 */
	private double getRangeFinderVoltage() {
		return RobotMap.chassisRangeFinder != null ? RobotMap.chassisRangeFinder.getVoltage() : 0.0;
	}

	/**
	 * @return Returns pressure in pounds per square inches.
	 */
	public double getAirPressure() {
		double volts = RobotMap.pressureSensor.getAverageVoltage() - 0.49;
		return volts * 51.28;
	}

	/**
	 * @return Get range finder distance in inches. Returns 0.0 of the value is
	 *         null.
	 */
	public double getChassisRangeFinderDistance() {
		// double distanceInInches = getRangeFinderVoltage() *110.5;// /
		// VOLT_DIST_RATIO;
//		double distanceInInches = getRangeFinderVoltage() * 107.5;
		double distanceInInches = getRangeFinderVoltage() * 41.6;
		return distanceInInches;
	}

	public void drive(double speed, double rotation, boolean square) {
		robotDrive.arcadeDrive(speed * -1, rotation * 1, square);
		driveMove = speed;
		driveRotate = rotation;
	}

	public void stop() {
		leftMotors.stopMotor();
		rightMotors.stopMotor();
	}

}
