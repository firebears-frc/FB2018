package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Get the change in degrees over time.
 */
public class DegreesPerSecondCommand extends Command {
	
	double y1;
	double y2;
	int t; 
	double degPerSec;
    public DegreesPerSecondCommand() {
      
    }

    protected void initialize() {
    	t = 1;
    }
   

    protected void execute() {
    	
    	if (t == 1) {
        	y1 = bound(RobotMap.navXBoard.getAngle());
        	t = 2; 
    	}else {
    		y2 = bound(RobotMap.navXBoard.getAngle());
    		t = 1;
    		degPerSec = (y2 - y1)/(2/50);
    	}
    }

    protected boolean isFinished() {
        return false;
    }
    protected static double bound(double angle) {
		while (angle > 180)
			angle -= 360;
		while (angle < -180)
			angle += 360;
		return angle;
	}

    protected void end() {
    }

    
    protected void interrupted() {
    }
}
