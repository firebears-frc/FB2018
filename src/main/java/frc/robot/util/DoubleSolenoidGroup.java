package frc.robot.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DoubleSolenoidGroup {
    private final DoubleSolenoid[] solenoids;

    public DoubleSolenoidGroup(DoubleSolenoid doubleSolenoid, DoubleSolenoid... doubleSolenoids) {
        solenoids = new DoubleSolenoid[doubleSolenoids.length + 1];
        solenoids[0] = doubleSolenoid;
        System.arraycopy(doubleSolenoids, 0, solenoids, 1, doubleSolenoids.length);
    }

    public void set(DoubleSolenoid.Value value) {
        for (DoubleSolenoid solenoid : solenoids) {
            solenoid.set(value);
        }
    }
}
