package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.RobotMap.EncoderPIDSource;
import org.firebears.commands.TeleopShooterCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	public final double HIGH_SPEED = 1;
	public final double LOW_SPEED = 0.25;

	final Value SOL_FORWARD = DoubleSolenoid.Value.kForward;
	final Value SOL_REVERSE = DoubleSolenoid.Value.kReverse;

	public PIDController leftSpinner;
	EncoderPIDSource leftSpinnerEncoder;

	public PIDController rightSpinner;
	EncoderPIDSource rightSpinnerEncoder;

	final double spinnerP = 0.00025;
	final double spinnerI = 0;
	final double spinnerD = 0.00025;
	final double spinnerF = 0;
	final double TOLERANCE_PER = 5;

	public Shooter() {
		leftSpinnerEncoder = new EncoderPIDSource(RobotMap.leftLaunchSpinner);
		leftSpinner = new PIDController(spinnerP, spinnerI, spinnerD, spinnerF, leftSpinnerEncoder,
				RobotMap.leftLaunchSpinner);
		leftSpinner.setPercentTolerance(TOLERANCE_PER);
//		leftSpinner.setToleranceBuffer(6);
		SmartDashboard.putData("Left Spin", leftSpinner);

		rightSpinnerEncoder = new EncoderPIDSource(RobotMap.rightLaunchSpinner);
		rightSpinner = new PIDController(spinnerP, spinnerI, spinnerD, spinnerF, rightSpinnerEncoder,
				RobotMap.rightLaunchSpinner);
		rightSpinner.setPercentTolerance(TOLERANCE_PER);
//		rightSpinner.setToleranceBuffer(6);
		SmartDashboard.putData("Right Spin", rightSpinner);
	}

	public void shooterSpinWheel(double speed) {
		speed = speed * 800;
		
		leftSpinner.enable();
		rightSpinner.enable();

		SmartDashboard.putNumber("Target Speed", speed);

		leftSpinner.setSetpoint(speed);
		rightSpinner.setSetpoint(-speed);

		// RobotMap.leftLaunchSpinner.set(speed);
		// RobotMap.rightLaunchSpinner.set(speed);
		//Robot.lights.setShootingMode(true);
		//Robot.lights.setFallingMode(false);
	}

	public void shooterStopWheel() {
		SmartDashboard.putNumber("Target Speed", 0);

		leftSpinner.disable();
		rightSpinner.disable();
		// RobotMap.leftLaunchSpinner.set(0);
		// RobotMap.rightLaunchSpinner.set(0);
		//Robot.lights.setShootingMode(false);
		//Robot.lights.setFallingMode(true);
	}

	public void shooterPneumaticsUp() {
		RobotMap.leftLaunch.set(SOL_REVERSE);
		RobotMap.rightLaunch.set(SOL_REVERSE);
	}

	public void shooterPneumaticsDown() {
		RobotMap.leftLaunch.set(SOL_FORWARD);
		RobotMap.rightLaunch.set(SOL_FORWARD);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TeleopShooterCommand());
	}
}
