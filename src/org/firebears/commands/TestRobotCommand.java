package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestRobotCommand extends CommandGroup {

    public TestRobotCommand() {
    	
    	addSequential(new ActivateCompressor(false));

    	
//    	addSequential(new IsVoltageOkayCommand(13.0), 5);
//    	
//    	addSequential(new IsNavxOkayCommand(), 3);
//
//    	addSequential(new IsAirPressureOkayCommand(), 3);
    	
    	addSequential(new IsSimOkayCommand("left",true), 3);
    	addSequential(new IsSimOkayCommand("left",false), 3);
    	addSequential(new IsSimOkayCommand("right",true), 3);
    	addSequential(new IsSimOkayCommand("right",false), 3);
    	
//    	addSequential(new IsShooterWheelOkayCommand("left", true), 3);
//    	addSequential(new IsShooterWheelOkayCommand("left", false), 3);
//    	addSequential(new IsShooterWheelOkayCommand("right", true), 3);
//    	addSequential(new IsShooterWheelOkayCommand("right", false), 3);
    	
//    	addSequential(new IsUltraSonicOkayCommand(1), 10);
//    	addSequential(new IsUltraSonicOkayCommand(2), 10);
//
//    	addSequential(new IsEverythingOkayCommand());
    	
    	addSequential(new ActivateCompressor(true));



    }
}
