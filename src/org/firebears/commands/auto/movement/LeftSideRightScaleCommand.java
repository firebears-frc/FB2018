package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSideRightScaleCommand extends CommandGroup {

    public LeftSideRightScaleCommand() {
        addSequential(new PlayRecordingCommand("recordings/LeftSideRightScale.csv"));
    }
}
