package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleSideLeftSwitchCommand extends CommandGroup {

    public MiddleSideLeftSwitchCommand() {
        addSequential(new PlayRecordingCommand("recordings/MiddleSideLeftSwitch.csv"));
    }
}
