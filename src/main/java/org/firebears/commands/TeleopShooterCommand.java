package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the Shooter wheels from the throttle on the button board
 */
public class TeleopShooterCommand extends Command {

//	double throttle = -1;
    public TeleopShooterCommand() {
        requires(Robot.shooter);
    }

    protected void initialize() {
    	
    }
    XboxController stick = Robot.oi.xbox1;

    protected void execute() {
        double throttle = Robot.oi.xbox1.getTriggerAxis(Hand.kRight);
        throttle = throttle*2.0-1.0;
    	if (RobotMap.DisableShooter == false) {
//        	System.out.println("Input: " + throttle);
    		if (((throttle + 1) / 2) >= .1) {
    			Robot.shooter.shooterSpinWheel((throttle + 1) / 2);
    		} else {
    			Robot.shooter.shooterSpinWheel(0);
    		}
    	}else {
    	}
    	
    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
