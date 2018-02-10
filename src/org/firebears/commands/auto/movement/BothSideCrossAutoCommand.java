package org.firebears.commands.auto.movement;

import org.firebears.commands.auto.DriveToDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BothSideCrossAutoCommand extends CommandGroup {

    public BothSideCrossAutoCommand() {
        addSequential(new DriveToDistanceCommand(40, 0.75));
    }
}
