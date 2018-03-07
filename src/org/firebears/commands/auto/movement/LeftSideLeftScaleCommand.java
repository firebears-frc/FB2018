package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LeftSideLeftScaleCommand extends CommandGroup {

    public LeftSideLeftScaleCommand() {
//        addSequential(new PlayRecordingCommand("recordings/LeftSideLeftScale.csv"));
    	
    	addSequential(new DriveToDistanceStraightCommand(265, 1.0));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(-90));
//		addSequential(new SpinShooterWheelsCommand(.6));
		addSequential(new DriveToDistanceStraightCommand(24, .5));
		addSequential(new WaitCommand(.5));
//		addSequential(new FireCubeCommand());
    }
}
