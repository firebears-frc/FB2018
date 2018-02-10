package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestRobotCommand extends CommandGroup {

    public TestRobotCommand() {
    	addSequential(new IsVoltageOKCommand());
    }
}
