package org.firebears.commands.grabber;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendShooterCommand extends Command {
	boolean shouldExtend;
    public ExtendShooterCommand(boolean shouldExtend) {
    	this.shouldExtend = shouldExtend;
    	requires(Robot.shooter);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (this.shouldExtend == true) {
    		Robot.shooter.shooterPneumaticsUp();
    		System.out.println("Extend Shooter");
    	} else {
    		Robot.shooter.shooterPneumaticsDown();
    		System.out.println("Retract Shooter");
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
