package org.firebears.commands.auto.movement;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.RelativeAngleCommandFast;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RotateToAngleCommandFast;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RightSideRightScaleCommand extends CommandGroup {

	public RightSideRightScaleCommand() {
		// PlayRecordingCommand("recordings/RightSideRightScale.csv"));
		
		addSequential(new ResetNavX());
		addSequential(new WaitCommand(.25));
		addSequential(new DriveToDistanceStraightCommand(210, 1.0));
    	addSequential(new DriveToDistanceStraightCommand(30, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new SpinShooterWheelsCommand(.6));
		addSequential(new RotateToAngleCommandFast(125), 2);
//		addSequential(new DriveToDistanceStraightCommand(24, .5));
//    	addSequential(new WaitCommand(.25));
    	addSequential(new FireCubeCommand());

	}
}
