package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class MiddleSideRightSwitchCommand extends CommandGroup {

    public MiddleSideRightSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/MiddleSideRightSwitch.csv"));
    	addSequential(new DriveToDistanceStraightCommand(12, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(60));
		addSequential(new WaitCommand(.7));
		addSequential(new DriveToDistanceStraightCommand(28, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(-60));
		addSequential(new WaitCommand(.5));
		addSequential(new OpenGrabberCommand(true));
		addSequential(new DriveToDistanceStraightCommand(45, 1.0));
    }
}
