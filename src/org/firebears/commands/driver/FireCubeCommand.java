package org.firebears.commands.driver;

import org.firebears.commands.shooter.ExtendShooterCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *	for shooting the cube
 */
public class FireCubeCommand extends CommandGroup {

    public FireCubeCommand() {
        addSequential(new ExtendShooterCommand(true));
        addSequential(new WaitCommand(.5));
        addSequential(new ExtendShooterCommand(false));
    }
}
