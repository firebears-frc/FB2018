package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayMirroredRecording;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LeftSideLeftSwitchCommand extends CommandGroup {

    public LeftSideLeftSwitchCommand() {
//      addSequential(new PlayRecordingCommand("recordings/LeftSideLeftSwitch.csv"));
//        addSequential(new PlayMirroredRecording("recordings/RightSideRightSwitch.csv"));
    	
    	addSequential(new DriveToDistanceStraightCommand(60, .4));
		addSequential(new DriveToDistanceStraightCommand(60, .8));
		addSequential(new WaitCommand(.5));
		addSequential(new RotateToAngleCommand(90));
		addSequential(new DriveToDistanceStraightCommand(24, -.5));
		addSequential(new WaitCommand(.5));
		addSequential(new OpenGrabberCommand(true));
		addSequential(new DriveToDistanceStraightCommand(10, 0.5));
		addSequential(new DriveToDistanceStraightCommand(40, 1.0),2.0);
    }
}
