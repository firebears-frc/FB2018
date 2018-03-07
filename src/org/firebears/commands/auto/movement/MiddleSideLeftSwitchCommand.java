package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class MiddleSideLeftSwitchCommand extends CommandGroup {

    public MiddleSideLeftSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/MiddleSideLeftSwitch.csv"));
    	addSequential(new DriveToDistanceStraightCommand(12, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(-60));
		addSequential(new WaitCommand(.7));
		addSequential(new DriveToDistanceStraightCommand(24, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(60));
		addSequential(new WaitCommand(.5));
		addSequential(new DriveToDistanceStraightCommand(40, 1.0));
    }
}
