package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LeftSideRightScaleCommand extends CommandGroup {

    public LeftSideRightScaleCommand() {
//        addSequential(new PlayRecordingCommand("recordings/LeftSideRightScale.csv"));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(1.0));
    	addSequential(new RotateToAngleCommand(90));
    	addSequential(new WaitCommand(.7));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(.7));
    	addSequential(new RotateToAngleCommand(-90));
    	addSequential(new DriveToDistanceStraightCommand(80, 0.70));
    	addSequential(new RotateToAngleCommand(90));



    }
}
