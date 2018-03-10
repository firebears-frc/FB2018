package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LeftSideRightSwitchCommand extends CommandGroup {

    public LeftSideRightSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/LeftSideRightSwitch.csv"));
    	
    	addSequential(new ResetNavX());
		addSequential(new WaitCommand(.25));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(0.5));
    	addSequential(new RelativeAngleCommand(90));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(215, 1.0));
    	
    	
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(180));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(55, 0.7));
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(90));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(48, -.5));
		addSequential(new SpinShooterWheelsCommand(.1));
		addSequential(new WaitCommand(.25));
		addSequential(new FireCubeCommand());
    }
}
