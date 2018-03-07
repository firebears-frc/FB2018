package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RightSideLeftSwitchCommand extends CommandGroup {

    public RightSideLeftSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/RightSideLeftSwitch.csv"));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(1.0));
    	addSequential(new RotateToAngleCommand(-90));
    	addSequential(new WaitCommand(.7));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	
    	
    	addSequential(new WaitCommand(.7));
    	addSequential(new RotateToAngleCommand(-90));
    	addSequential(new WaitCommand(.7));
    	addSequential(new DriveToDistanceStraightCommand(40, 0.7));
    	addSequential(new WaitCommand(.7));
    	addSequential(new RotateToAngleCommand(-90));
    	addSequential(new WaitCommand(.7));
    	addSequential(new DriveToDistanceStraightCommand(24, -0.5));
    	addSequential(new WaitCommand(.7));
    	addSequential(new DriveToDistanceStraightCommand(48, 1.0));



		
    }
}
