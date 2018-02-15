package org.firebears.commands.grabber;

import org.firebears.Robot;

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
			Robot.shooter.shooterStopWheel();
		} else {
			Robot.shooter.shooterSpinWheel(this.speed);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
