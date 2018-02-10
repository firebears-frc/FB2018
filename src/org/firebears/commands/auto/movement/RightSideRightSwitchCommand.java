package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSideRightSwitchCommand extends CommandGroup {

    public RightSideRightSwitchCommand() {
        addSequential(new PlayRecordingCommand("recordings/RightSideRightSwitch.csv"));
    }
}
