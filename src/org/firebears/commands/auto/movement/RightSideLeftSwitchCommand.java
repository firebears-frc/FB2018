package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSideLeftSwitchCommand extends CommandGroup {

    public RightSideLeftSwitchCommand() {
        addSequential(new PlayRecordingCommand("recordings/RightSideLeftSwitch.csv"));
    }
}
