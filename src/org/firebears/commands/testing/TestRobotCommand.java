package org.firebears.commands.testing;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DisableDriveCommand;
import org.firebears.commands.DisableShooterCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.shooter.ExtendShooterCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TestRobotCommand extends CommandGroup {

    public TestRobotCommand() {
    	
    	addSequential(new ActivateCompressor(false));
    	addSequential(new DisableShooterCommand(true));
    	addSequential(new DisableDriveCommand(true));



    	
    	addSequential(new IsVoltageOkayCommand(13.0), 3);
//    	
    	addSequential(new IsNavxOkayCommand(), 3);
//
    	addSequential(new IsAirPressureOkayCommand(), 3);
    	
//    	
//
//
//    	addSequential(new IsSolenoidOkayCommand(1), 5);
//    	addSequential(new RaiseGrabberCommand(true));
//    	
//    	addSequential(new OpenGrabberCommand(false));
//    	addSequential(new IsSolenoidOkayCommand(3), 5);
//    	addSequential(new ExtendShooterCommand(false));
//
//    	addSequential(new IsSolenoidOkayCommand(2), 5);
//    	addSequential(new OpenGrabberCommand(true));
//
//    	
//    	
//    	addSequential(new IsGrabberWheelOkayCommand(true),3);
//    	addSequential(new IsGrabberWheelOkayCommand(false),3);
//    	
//
//
//    	addSequential(new IsShooterWheelOkayCommand("left", true), 3);
//    	addSequential(new IsShooterWheelOkayCommand("right", true), 3);
//    	addSequential(new IsShooterWheelOkayCommand("right", false), 3);
//    	addSequential(new IsShooterWheelOkayCommand("left", false), 3);
//    	
//    	addSequential(new IsLightarOkayCommand());
////    	addSequential(new IsUltraSonicOkayCommand(1), 10);
//    	addSequential(new IsUltraSonicOkayCommand(2), 10);

    	
    	addSequential(new IsEverythingOkayCommand());
    	
    	
    	addSequential(new DisableShooterCommand(false));
    	addSequential(new DisableDriveCommand(false));

    	addSequential(new ActivateCompressor(true));

//    	addSequential(new IsShooterWheelOkayCommand("right", true), 3);
    	
//    	addSequential(new WaitCommand(15));
    	
//    	addSequential(new IsSimOkayCommand("left front",true), 3);
//    	addSequential(new IsSimOkayCommand("right rear",true), 3);

    	
    	

    	

//    	addSequential(new IsSimOkayCommand("",false), 3);
//    	addSequential(new IsSimOkayCommand("",true), 3);
//    	addSequential(new IsSimOkayCommand("",false), 3);
    	

//    	
//
    	



    }
}
