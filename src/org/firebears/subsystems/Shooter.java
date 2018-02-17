package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.RobotMap.EncoderPIDSource;

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
	
	final double spinnerP = 0.1;
	final double spinnerI = 0;
	final double spinnerD = 0;
	final double spinnerF = 0.1;
	final double TOLERANCE = 0.05;
	
	public Shooter() {
		leftSpinnerEncoder = new EncoderPIDSource(RobotMap.leftLaunchSpinner);
		leftSpinner = new PIDController(spinnerP, spinnerI, spinnerD, spinnerF, leftSpinnerEncoder, RobotMap.leftLaunchSpinner);
		leftSpinner.setAbsoluteTolerance(TOLERANCE);
		
		rightSpinnerEncoder = new EncoderPIDSource(RobotMap.rightLaunchSpinner);
		rightSpinner = new PIDController(spinnerP, spinnerI, spinnerD, spinnerF, rightSpinnerEncoder, RobotMap.rightLaunchSpinner);
		rightSpinner.setAbsoluteTolerance(TOLERANCE);
	}

	public void shooterSpinWheel(double speed) {
		leftSpinner.enable();
		rightSpinner.enable();
		
		SmartDashboard.putNumber("Target Speed", speed);
		
		leftSpinner.setSetpoint(speed);// ((speed/20) * 60) = rpm
		rightSpinner.setSetpoint(-speed);
		
//		RobotMap.leftLaunchSpinner.set(speed);
//		RobotMap.rightLaunchSpinner.set(speed);
		Robot.lights.setShootingMode(true);
	}

	public void shooterStopWheel() {
		SmartDashboard.putNumber("Target Speed", 0);
		
		leftSpinner.disable();
		rightSpinner.disable();
//		RobotMap.leftLaunchSpinner.set(0);
//		RobotMap.rightLaunchSpinner.set(0);
		Robot.lights.setShootingMode(false);
	}

	public void shooterPneumaticsUp() {
		//if (Robot.grabber.hasCube() && Robot.grabber.isRaised()){ // Uncomment for competition code
			RobotMap.leftLaunch.set(SOL_REVERSE);
			RobotMap.rightLaunch.set(SOL_REVERSE);
		//}
		
	}

	public void shooterPneumaticsDown() {
		RobotMap.leftLaunch.set(SOL_FORWARD);
		RobotMap.rightLaunch.set(SOL_FORWARD);
	}

	public void initDefaultCommand() {

	}
}
