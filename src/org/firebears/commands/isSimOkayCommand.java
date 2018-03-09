package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Tests Either side of the motors
 */
public class isSimOkayCommand extends Command {
	
	
	String side;
	int forward = 1;//default to 1 for forward operation
    public isSimOkayCommand(String side, int forward) {
    	this.side = side;
    	this.forward = forward;
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	switch (side) {
    	case "left":
        	RobotMap.chassisLeftMotors.set(610 * forward);//610 is close to max speed
        	System.out.println("Side: " + side +" Set to 610 and direction " + forward);
        	break;
    	case "right":
        	RobotMap.chassisRightMotors.set(610 * forward);
        	System.out.println("Side: " + side +" Set to 610 and direction " + forward);
        	break;
        default:
        	RobotMap.chassisRightMotors.set(0);
        	RobotMap.chassisLeftMotors.set(0);
        	System.out.println("Error in isSimOkayCommand: "+ side + "is not a side");
        	break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Math.abs(RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX)) >= 600) {
    		System.out.println("");
			System.out.println(side + "Motors reached speed in " + forward +"Direction. " + RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
    		return true;
    	}else if (Math.abs(RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX)) >= 600){
    		System.out.println("");
			System.out.println(side + "Motors reached speed in " + forward +"Direction. " + RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
    		return true;
    	}else {
    		if (side == "Left") {
    			System.out.println(side + "Motors not reaching speed in " + forward +"Direction!: " + RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
    		}
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.chassisRightMotors.set(0);
    	RobotMap.chassisLeftMotors.set(0);
    	System.out.println("isSimOkayCommand Ended");

    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
