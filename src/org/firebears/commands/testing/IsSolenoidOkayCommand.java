package org.firebears.commands.testing;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IsSolenoidOkayCommand extends Command {

	int solenoid;
	boolean downNegation = false;
	boolean upNegation = false;
	boolean openClose = false;
	boolean launch = false;
	public IsSolenoidOkayCommand(int solenoid) {
		this.solenoid = solenoid;
		requires(Robot.grabber);
	}

	protected void initialize() {
		System.out.println("IsSolenoidOkayCommand Started");
//		RobotMap.TestSolenoid = false;
//		RobotMap.TestSolenoidGrabberVertUp = false;
//		RobotMap.TestSolenoidGrabberVertDown = false;

		switch (solenoid) {
		case 1:
			if (RobotMap.grabberDownPositionSensor.get()) {
				Robot.grabber.grabberRaise();
				upNegation = true;

			}else if (RobotMap.grabberUpPositionSensor.get()) {
				Robot.grabber.grabberLower();
				downNegation = true;

			}else {
				System.out.println("Grabber ,Up Down, In middle postion");
				Robot.grabber.grabberRaise();
				upNegation = true;

			}
			break;
		case 2:
			if (RobotMap.leftOpenClose.get() == DoubleSolenoid.Value.kForward && RobotMap.rightOpenClose.get() == DoubleSolenoid.Value.kForward) {
				RobotMap.leftOpenClose.set(DoubleSolenoid.Value.kReverse);
				RobotMap.rightOpenClose.set(DoubleSolenoid.Value.kReverse);
			}else if (RobotMap.leftOpenClose.get() == DoubleSolenoid.Value.kReverse && RobotMap.rightOpenClose.get() == DoubleSolenoid.Value.kReverse) {
				RobotMap.leftOpenClose.set(DoubleSolenoid.Value.kForward);
				RobotMap.rightOpenClose.set(DoubleSolenoid.Value.kForward);
			}else {
				RobotMap.leftOpenClose.set(DoubleSolenoid.Value.kForward);
				RobotMap.rightOpenClose.set(DoubleSolenoid.Value.kForward);
				System.out.println("Grabber, Open close, out of sync");
			}
			openClose = true;
			break;
		case 3:
			if (RobotMap.leftLaunch.get() == DoubleSolenoid.Value.kForward && RobotMap.rightLaunch.get() == DoubleSolenoid.Value.kForward) {
				RobotMap.leftLaunch.set(DoubleSolenoid.Value.kReverse);
				RobotMap.rightLaunch.set(DoubleSolenoid.Value.kReverse);
			}else if (RobotMap.leftLaunch.get() == DoubleSolenoid.Value.kReverse && RobotMap.rightLaunch.get() == DoubleSolenoid.Value.kReverse) {
				RobotMap.leftLaunch.set(DoubleSolenoid.Value.kForward);
				RobotMap.rightLaunch.set(DoubleSolenoid.Value.kForward);
			}else {
				RobotMap.leftLaunch.set(DoubleSolenoid.Value.kReverse);
				RobotMap.rightLaunch.set(DoubleSolenoid.Value.kReverse);
				System.out.println("Lauch, Fire close, out of sync");
				launch = true;
			}
			break;
		}
		
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return (RobotMap.grabberDownPositionSensor.get() && downNegation) || (RobotMap.grabberUpPositionSensor.get() && upNegation) || openClose || launch;
	}

	protected void end() {
		
		
		if (solenoid == 1 && RobotMap.grabberUpPositionSensor.get()) {
			RobotMap.TestSolenoidGrabberVertUp = true;
		}else if (solenoid == 1 && RobotMap.grabberDownPositionSensor.get()) {
			RobotMap.TestSolenoidGrabberVertDown = true;
		}
		
		
		if (upNegation || downNegation) {
			System.out.println("Solenoids moved in sensor range");
			System.out.println("Solenoids " + solenoid + " Status: Nominal");

		}else {
			System.out.println("Solenoids, Open close, Fire close, Need to be checked Manualy");

		}
	}

	protected void interrupted() {
		System.out.println("IsSolenoidOkayCommand Interrupted");
		RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;

	}
}
