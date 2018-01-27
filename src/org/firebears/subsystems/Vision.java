package org.firebears.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Vision extends Subsystem {
	
	NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("Vision");
	
	public double getAngleX() {
		return visionTable.getEntry("AngleX").getDouble(0);
	}
	
	public double getAngleY() {
		return visionTable.getEntry("AngleY").getDouble(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
