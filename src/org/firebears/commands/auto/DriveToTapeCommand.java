package org.firebears.commands.auto;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Drive To the tape on the field
 */
public class DriveToTapeCommand extends Command {

	
	double SPEED;
	double startingDistance;
	double currentDistance;
	
    public DriveToTapeCommand(double speed) {
    	requires(Robot.chassis);
    	SPEED = speed;
    }

    protected void initialize() {
    	System.out.println("Starting " + this.toString());
//		startingDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
    }
    protected void execute() {
    	Robot.chassis.drive(-SPEED, 0,false);
    }

    protected boolean isFinished() {
//    	currentDistance = RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX);
        return Robot.chassis.isTapeBright();
    }

    protected void end() {
		Robot.chassis.drive(0, 0,false);
//    	Robot.chassis.stop();
    	System.out.println("Ending " + this);
//    	System.out.println("Distance to tape: " + (currentDistance - startingDistance)/52.6);
    }
    
    protected void interrupted() {
    	end();
    	System.out.println("Was interrupted");
    }
    
    public String toString() {
    	return "DriveToTapeCommand: " + SPEED + " speed";
    }
}
