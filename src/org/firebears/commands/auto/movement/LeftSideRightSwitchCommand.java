package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSideRightSwitchCommand extends CommandGroup {

    public LeftSideRightSwitchCommand() {
        addSequential(new PlayRecordingCommand("recordings/LeftSideRightSwitch.csv"));
    }
}
