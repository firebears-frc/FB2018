package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionGetCubeCommandGroup extends CommandGroup {

    public VisionGetCubeCommandGroup() {
        addSequential(new VisionRotateCommand());
        addSequential(new VisionForwardCommand());
    }
}
