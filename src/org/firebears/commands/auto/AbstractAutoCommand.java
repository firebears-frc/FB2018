package org.firebears.commands.auto;

import static java.lang.System.out;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 * Base class for autonomous Power Up commands.
 */
public abstract class AbstractAutoCommand extends Command {

  protected Command autoCommand;
  protected String gameData;

  @Override
  protected void initialize() {
    autoCommand = null;
    setTimeout(10);
  }

  @Override
  protected void execute() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData != null && gameData.trim().length() > 0) {
      autoCommand = pickAutoCommand(gameData);
    }
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      autoCommand = getTimeoutCommand();
      out.printf("Timed out!");
      return true;
    }
    return autoCommand != null;
  }

  @Override
  protected void end() {
    out.printf("gamedata = %s%n", gameData);
    out.printf("autoCommand = %s%n", autoCommand);
    autoCommand.start();
    gameData = null;
  }

  /**
   * @return the best command for handling the game data.
   */
  abstract protected Command pickAutoCommand(String data);

  /**
   * @return a command to start if we never get game data.
   */
  protected Command getTimeoutCommand() {
	  return new DriveToDistanceCommand(60,.5);
  }


}