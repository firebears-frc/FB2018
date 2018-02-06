package org.firebears.commands.auto;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToTapeCommand extends Command {

	
	double SPEED;
	double startingDistance;
	double currentDistance;
	
    public DriveToTapeCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	SPEED = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.drive(-SPEED, 0);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);

        return Robot.chassis.isTapeBright();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Distance to tape: " + (currentDistance - startingDistance)/52.6);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
