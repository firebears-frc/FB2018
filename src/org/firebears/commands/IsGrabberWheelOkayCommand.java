package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IsGrabberWheelOkayCommand extends Command {

	boolean direction;
    public IsGrabberWheelOkayCommand(boolean direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.grabber);
    	this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("IsGrabberWheelOkayCommand started");

    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (direction) {
        	Robot.grabber.grabberStartSpinning();
    	}else {
        	Robot.grabber.grabberReverseSpin();

    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//add timeout at commandgroup
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		System.out.println("IsGrabberWheelOkayCommand interrupted");

    	RobotMap.DisableDrive = false;
		RobotMap.DisableShooter = false;
    }
}
