package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BackIntoWallCommand extends Command {

	long timeout;
	double stopDistance;
	static final int SLOW_DISTANCE = 15;
	static final int FAST_DISTANCE = 30;
	static final double SLOW_SPEED = -0.4;
	static final double FAST_SPEED = -0.75;
	
    public BackIntoWallCommand(double stopDistance) {
        requires(Robot.chassis);
        this.stopDistance = stopDistance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timeout = System.currentTimeMillis() + 1000 * 6;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double distancePer = (Robot.chassis.getChassisRangeFinderDistance() - SLOW_DISTANCE) / (FAST_DISTANCE - SLOW_DISTANCE);
		distancePer = Math.min(100, distancePer);
		distancePer = Math.max(0, distancePer);
		double speed = distancePer * (FAST_DISTANCE - SLOW_DISTANCE) + SLOW_DISTANCE;
		SmartDashboard.putNumber("Target Speed", speed);
		// Drive the robot
		Robot.chassis.drive(-1 * speed, 0, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (System.currentTimeMillis() >= timeout || Robot.chassis.getChassisRangeFinderDistance() < stopDistance) {
			return true;
		}
		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.drive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
