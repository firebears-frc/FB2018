package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RightSideLeftSwitchCommand extends CommandGroup {

    public RightSideLeftSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/RightSideLeftSwitch.csv"));
    	
    	addSequential(new ResetNavX());
		addSequential(new WaitCommand(.25));
		addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(1));
    	addSequential(new RelativeAngleCommand(-90));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(215, 0.70));
    	
    	
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(-180));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(40, 0.7));
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(-270));
    	addSequential(new WaitCommand(.5));
		addSequential(new OpenGrabberCommand(true));
    	addSequential(new DriveToDistanceStraightCommand(48, 1.0));



		
    }
}
