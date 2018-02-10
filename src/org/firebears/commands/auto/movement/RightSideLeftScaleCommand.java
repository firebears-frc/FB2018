package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSideLeftScaleCommand extends CommandGroup {

    public RightSideLeftScaleCommand() {
        addSequential(new PlayRecordingCommand("recordings/RightSideLeftScale.csv"));
    }
}
