package org.firebears.commands;

import javax.xml.stream.events.StartDocument;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NonPIDRotateCommand extends Command {
	double CurrentAnlge;
	double StartingAngle;
	double SPEED = 0.7;
	double angle;
	double direction;
	boolean positiveNegation;
	boolean negativeNegation;
	double FinalAngle;
    public NonPIDRotateCommand(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angle = angle;
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	positiveNegation = false;
    	negativeNegation = false;
    	StartingAngle = RobotMap.navXBoard.getAngle();
    	FinalAngle = StartingAngle + angle;
    	if (angle < 0) {
    		direction = -1;
        	negativeNegation = true;
    	}else {
    		direction = 1;
        	positiveNegation = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	CurrentAnlge = RobotMap.navXBoard.getAngle();
    	Robot.chassis.drive(0, SPEED * direction, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((CurrentAnlge >= FinalAngle) && positiveNegation) || ((CurrentAnlge <= FinalAngle) && negativeNegation);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
