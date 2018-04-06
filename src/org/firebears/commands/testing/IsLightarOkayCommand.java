package org.firebears.commands.testing;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IsLightarOkayCommand extends Command {

	boolean currentState1;
	boolean currentState2;

    public IsLightarOkayCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("IsLightarOkayCommand started");
		currentState1 = !Robot.grabber.cubeInRange();
		currentState2 = !Robot.grabber.hasCube();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Lightar is " + !Robot.grabber.cubeInRange() + !Robot.grabber.hasCube());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (currentState1 != !Robot.grabber.cubeInRange() && currentState2 != !Robot.grabber.hasCube());
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.TestLightar = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		System.out.println("IsLightarOkayCommand interrupted");

    }
}
