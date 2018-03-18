package org.firebears.commands.auto;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.RelativeAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TestCrossFieldCommandGroup extends CommandGroup {

    public TestCrossFieldCommandGroup() {
        addSequential(new DriveToDistanceStraightCommand(220, .5));
        addSequential(new WaitCommand(.5));
        addSequential(new RelativeAngleCommand(-90));
        addSequential(new WaitCommand(.5));
        addSequential(new DriveToDistanceStraightCommand(240, .5));
        addSequential(new WaitCommand(.5));
        addSequential(new RelativeAngleCommand(90));
        addSequential(new WaitCommand(.5));
        addSequential(new DriveToDistanceStraightCommand(95, .5));
        addSequential(new WaitCommand(.5));
        addSequential(new RelativeAngleCommand(-90));
    }
}
