package org.firebears.commands;

import org.firebears.commands.auto.AutoPickUpCubeCommand;
import org.firebears.commands.grabber.WaitforArmDownCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class VisionGetCubeCommandGroup extends CommandGroup {

    public VisionGetCubeCommandGroup() {
    	addParallel(new AutoPickUpCubeCommand());
		addSequential(new WaitCommand(.25));
		addSequential(new DriveToDistanceStraightCommand(36, .5));
    	addSequential(new WaitforArmDownCommand());
    	addSequential(new WaitCommand(.25));
        addSequential(new VisionRotateCommand());
        addSequential(new WaitCommand(.25));
//        addSequential(new VisionForwardCommand());
        addSequential(new VisionForwardCommand(.3));
        addSequential(new DriveToDistanceStraightCommand(10, -.3));
    }
}
