package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSideLeftScaleCommand extends CommandGroup {

    public LeftSideLeftScaleCommand() {
        addSequential(new PlayRecordingCommand("recordings/LeftSideLeftScale.csv"));
    }
}