package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestRobotCommand extends CommandGroup {

    public TestRobotCommand() {
    	addSequential(new IsVoltageOKCommand(13.0),5);
    	addSequential(new isSimOkayCommand("left",1), 3);
    	addSequential(new isSimOkayCommand("left",-1), 3);
    	addSequential(new isSimOkayCommand("right",1), 3);
    	addSequential(new isSimOkayCommand("right",-1), 3);



    }
}
