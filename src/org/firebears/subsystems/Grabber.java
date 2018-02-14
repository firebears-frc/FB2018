package org.firebears.subsystems;

import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	
	final double MOTORSPEED = 1;
	
	// to-do
	public void grabberRaise() {
		
	}
	
	public void grabberLower() {
		
	}
	
	public void grabberOpen() {
		
	}
	
	public void grabberClose() {
		
	}
	
	public void grabberStartSpinning() {
		RobotMap.leftIntake.set(MOTORSPEED);
		RobotMap.rightIntake.set(MOTORSPEED);
	}
	
	public void grabberStopSpinning() {
		RobotMap.leftIntake.set(0);
		RobotMap.rightIntake.set(0);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

