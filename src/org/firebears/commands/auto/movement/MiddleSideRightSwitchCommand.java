package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleSideRightSwitchCommand extends CommandGroup {

    public MiddleSideRightSwitchCommand() {
        addSequential(new PlayRecordingCommand("recordings/MiddleSideRightSwitch.csv"));
    }
}
