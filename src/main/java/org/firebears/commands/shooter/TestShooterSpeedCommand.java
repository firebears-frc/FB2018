package org.firebears.commands.shooter;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestShooterSpeedCommand extends Command {

	double speed;
	
    public TestShooterSpeedCommand() {
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	speed = SmartDashboard.getNumber("Set Shooter Test Speed", 0);
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
