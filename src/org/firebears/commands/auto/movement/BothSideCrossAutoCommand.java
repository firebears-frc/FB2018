package org.firebears.commands.auto.movement;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.auto.DriveToDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BothSideCrossAutoCommand extends CommandGroup {

    public BothSideCrossAutoCommand() {
        addSequential(new DriveToDistanceStraightCommand(200, 0.75));
        addSequential(new ActivateCompressor(true));
    }
}
