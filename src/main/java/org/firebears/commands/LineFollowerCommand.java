/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;

import javax.lang.model.util.ElementScanner6;

import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.subsystems.Chassis;

public class LineFollowerCommand extends Command {

  DigitalInput rightSensor = RobotMap.rightSensor;// 2
  DigitalInput centerSensor = RobotMap.tapeSensor;// 3
  DigitalInput leftSensor = RobotMap.cubeSwitch;// 4

  private boolean Rsen;
  private boolean Csen;
  private boolean Lsen;
  private boolean seenTape;
  private boolean steepAngle;
  private boolean steepAngleExecute;
  private boolean RsenState;
  private boolean LsenState;
  private boolean RsenState2;
  private boolean LsenState2;

  private double driveSpeed = 0.225;
  private double rotationSpeed = 0.3;

  int offTape;
  int lastOn;
  int lastOnUnsaved;

  long timeout;
  long timeout2;
  long timeout3;

  public LineFollowerCommand() {
    requires(Robot.chassis);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.chassis.drive(0.05, 0, true);

    timeout = System.currentTimeMillis() + 5500;

    System.out.print("initialize");
    RsenState = false;
    LsenState = false;
    RsenState2 = false;
    LsenState2 = false;
    seenTape = false;
    offTape = 0;
    lastOnUnsaved = 0;
    steepAngle = false;
    steepAngleExecute = false;
    // drive slowly
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.print("execute");
    Rsen = rightSensor.get();
    System.out.print(Rsen);
    Csen = centerSensor.get();
    System.out.print(Csen);
    Lsen = leftSensor.get();
    System.out.print(Lsen);

    if (Lsen || Csen || Rsen) {
      seenTape = true;
    }
    if (!steepAngle) {
      if (Lsen && !Csen && !Rsen) {
        Robot.chassis.drive(-driveSpeed, -0.25, false);
      } else if (!Lsen && Csen && !Rsen) {
        Robot.chassis.drive(-driveSpeed, 0.0, false);
      } else if (!Lsen && !Csen && Rsen) {
        Robot.chassis.drive(-driveSpeed, 0.25, false);
      } else if (Lsen && Csen && !Rsen) {
        Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
      } else if (!Lsen && Csen && Rsen) {
        Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
      } else if (!Lsen && !Csen && !Rsen && !seenTape) {
        Robot.chassis.drive(-driveSpeed, 0.0, false);
      }
    }

    if (offTape == 1) {
      if (lastOn == 1) {
        Robot.chassis.drive(-0.075, 0.5, false);
      } else if (lastOn == 2) {
        Robot.chassis.drive(-0.075, -0.5, false);
      }

      if (Lsen || Csen || Rsen) {
        timeout = System.currentTimeMillis() + 4000;
        offTape = 0;
        System.out.println("Off Tape");
      }
    }

    if (Rsen) {
      RsenState = true;
      if (RsenState2) {
        lastOnUnsaved = 1;
      }
      RsenState2 = false;
    } else {
      RsenState = false;
      RsenState2 = true;
    }
    if (Lsen) {
      LsenState = true;
      if (LsenState2) {
        lastOnUnsaved = 2;

      }
      LsenState2 = false;
    } else {
      LsenState = false;
      LsenState2 = true;
    }

    System.out.println("last on: " + lastOnUnsaved);

    if (Lsen && Csen && Rsen) {
      System.out.println("All Sensors on");
      if (!steepAngle) {
        steepAngle = true;
        timeout2 = System.currentTimeMillis() + 750;
        timeout3 = System.currentTimeMillis() + 350;
        lastOn = lastOnUnsaved;
        System.out.println("Steep Angle");
      }
    }

    if (steepAngle) {
      if (!steepAngleExecute) {
        Robot.chassis.drive(-0.1, 0, false);
        if (System.currentTimeMillis() >= timeout3) {
          steepAngleExecute = true;
        }
      }
      if (steepAngleExecute) {
        if (lastOn == 1) {
          Robot.chassis.drive(-0.075, rotationSpeed, false);
        } else if (lastOn == 2) {
          Robot.chassis.drive(-0.075, -rotationSpeed, false);
        }

        if (System.currentTimeMillis() >= timeout2) {
          offTape = 1;
          timeout = System.currentTimeMillis() + 3000;
        }

        if (lastOn == 1 && Lsen && Csen && !Rsen) {
          steepAngle = false;
          System.out.println("Steep Angle End");
        }
        if (lastOn == 2 && Rsen && Csen && !Lsen) {
          steepAngle = false;
          System.out.println("Steep Angle End");
        }
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() >= timeout) {
      return true;
    }
    if (offTape == 0 && !steepAngle && seenTape && !Lsen && !Csen && !Rsen) {
      timeout = System.currentTimeMillis() + 1500;
      offTape = 1;
    }
    if (steepAngle) {
      if (lastOn == 0) {
        return true;
      }
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0, 0, true);
    System.out.println("end");
    System.out.println("Left sensor: " + Lsen);
    System.out.println("Center sensor: " + Csen);
    System.out.println("Right sensor: " + Rsen);
    System.out.println("Steep Angle: " + steepAngle);
    System.out.println("Off Tape: " + offTape);
    System.out.println("last on: " + lastOnUnsaved);
    System.out.println("last saved: " + lastOn);
  }

}
