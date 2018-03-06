package org.firebears.commands.shooter;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinShooterWheelsCommand extends Command {
	double speed;

	public SpinShooterWheelsCommand(double speed) {
		this.speed = speed;
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
		if (this.speed == 0) {
			System.out.println("Spinning shooter wheels");
			Robot.shooter.shooterStopWheel();
		} else {
			System.out.println("Stopped shooter wheels");
			Robot.shooter.shooterSpinWheel(this.speed);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		Joystick stick = Robot.oi.joystick2;
//		Robot.shooter.shooterSpinWheel(stick.getThrottle());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
