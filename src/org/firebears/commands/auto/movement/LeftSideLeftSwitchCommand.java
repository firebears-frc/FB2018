package org.firebears.commands.auto.movement;

import org.firebears.commands.PlayMirroredRecording;
import org.firebears.commands.PlayRecordingCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSideLeftSwitchCommand extends CommandGroup {

    public LeftSideLeftSwitchCommand() {
//      addSequential(new PlayRecordingCommand("recordings/LeftSideLeftSwitch.csv"));
        addSequential(new PlayMirroredRecording("recordings/RightSideRightSwitch.csv"));
    }
}
