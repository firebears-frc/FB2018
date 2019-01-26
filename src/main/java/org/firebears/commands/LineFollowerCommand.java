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

  private double driveSpeed = 0.225;
  private double rotationSpeed = 0.3;

  int offTape;
  int lastOn;

  long timeout;
  long timeout2;

  public LineFollowerCommand() {
    requires(Robot.chassis);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.chassis.drive(0.2, 0, true);

    timeout = System.currentTimeMillis() + 5500;

    System.out.print("initialize");
    seenTape = false;
    offTape = 0;
    steepAngle = false;
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

    if (Lsen && !Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
    } else if (!Lsen && Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, 0.0, false);
    } else if (!Lsen && !Csen && Rsen) {
      Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
    } else if (Lsen && Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
    } else if (!Lsen && Csen && Rsen) {
      Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
    } else if (!Lsen && !Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, 0.0, false);
    }

    if (offTape == 1) {
      if (Lsen || Csen || Rsen) {
        timeout = 4000;
        offTape = 0;
      }
    }

    if (Rsen) {
      lastOn = 2;
    }
    if (Lsen) {
      lastOn = 1;
    }


    if (Lsen && Csen && Rsen) {
      if (!steepAngle) {
        steepAngle = true;
        timeout2 = 750;
      }
    }

    if (steepAngle) {
      if (lastOn == 1) {
        Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
      } else if (lastOn == 2) {
        Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
      }
      if (System.currentTimeMillis() >= timeout2) {
        offTape = 1;
        timeout = 3000;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() >= timeout) {
      return true;
    }
    if (offTape == 0) {
      if (!steepAngle) {
        if (Lsen == false && Rsen == false && Csen == false && seenTape) {
          timeout = 1500;
          offTape = 1;
        }
      }
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
  }

}
