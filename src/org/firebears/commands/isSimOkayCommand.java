package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Tests Either side of the motors
 */
public class isSimOkayCommand extends Command {
	
	
	String side;
    public isSimOkayCommand(String side) {
    	this.side = side;
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	switch (side) {
    	case "left":
        	RobotMap.chassisLeftMotors.set(610);
        	break;
    	case "right":
        	RobotMap.chassisRightMotors.set(610);
        	break;
        default:
        	System.out.println("Error in isSimOkayCommand: Not a side");
        	break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= 600) {
    		return true;
    	}else if (RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX) >= 600){
    		return true;
    	}else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
