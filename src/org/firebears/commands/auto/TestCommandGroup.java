package org.firebears.commands.auto;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.GetAcceleration;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TestCommandGroup extends CommandGroup {

    public TestCommandGroup() {
    	addSequential(new DriveToDistanceStraightCommand(200, 1));
    	addSequential(new GetAcceleration());
    }
}
