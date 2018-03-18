package org.firebears.commands.auto.movement;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayMirroredRecording;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.driver.GrabberDownCommand;
import org.firebears.commands.driver.GrabberUpCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.ReverseGrabberWheelsCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;
import org.firebears.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LeftSideLeftSwitchCommand extends CommandGroup {

    public LeftSideLeftSwitchCommand() {
//      addSequential(new PlayRecordingCommand("recordings/LeftSideLeftSwitch.csv"));
//        addSequential(new PlayMirroredRecording("recordings/RightSideRightSwitch.csv"));
    	
    	addSequential(new DriveToDistanceStraightCommand(60, .4));
		addSequential(new DriveToDistanceStraightCommand(48, .8));
		addSequential(new WaitCommand(.5));
		addSequential(new RelativeAngleCommand(-90));
//		addSequential(new DriveToDistanceStraightCommand(24, -.5));
		addSequential(new WaitCommand(.5));
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new DriveToDistanceStraightCommand(10, 0.5));
		addSequential(new DriveToDistanceStraightCommand(40, -.35),1.0);
		addSequential(new SpinShooterWheelsCommand(.1));
		addSequential(new WaitCommand(.25));
		addSequential(new FireCubeCommand());
        addSequential(new ActivateCompressor(true));
		
        
//        addSequential(new WaitCommand(.5));
//		addSequential(new RaiseGrabberCommand(false));
//		addSequential(new WaitCommand(2.5));
//		addSequential(new ReverseGrabberWheelsCommand(true));
//		addSequential(new WaitCommand(3.0));
//		addSequential(new RaiseGrabberCommand(true));
//		addSequential(new ReverseGrabberWheelsCommand(false));
//		addSequential(new OpenGrabberCommand())

    }
}
