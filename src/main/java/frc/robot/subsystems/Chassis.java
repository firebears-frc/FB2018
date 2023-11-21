package frc.robot.subsystems;

import java.util.function.Supplier;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chassis extends SubsystemBase {
    private static final class Constants {
        private static final int FRONT_LEFT_CAN_ID = 2;
        private static final int REAR_LEFT_CAN_ID = 3;
        private static final int FRONT_RIGHT_CAN_ID = 4;
        private static final int REAR_RIGHT_CAN_ID = 5;

        private static final double TRACK_WIDTH = Units.inchesToMeters(30); // TODO!
    }

    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_CAN_ID);
    private final WPI_TalonSRX rearLeft = new WPI_TalonSRX(Constants.REAR_LEFT_CAN_ID);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_CAN_ID);
    private final WPI_TalonSRX rearRight = new WPI_TalonSRX(Constants.REAR_RIGHT_CAN_ID);
    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH);

    private ChassisSpeeds target = new ChassisSpeeds();

    public Chassis() {
        // TODO: Configure TalonSRXes for PID velocity control
    }

    private void drive(ChassisSpeeds speeds) {
        target = speeds;

        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(target);
        frontLeft.set(ControlMode.Velocity, wheelSpeeds.leftMetersPerSecond);
        rearLeft.set(ControlMode.Velocity, wheelSpeeds.leftMetersPerSecond);
        frontRight.set(ControlMode.Velocity, wheelSpeeds.rightMetersPerSecond);
        rearRight.set(ControlMode.Velocity, wheelSpeeds.rightMetersPerSecond);
    }

    @AutoLogOutput
    public ChassisSpeeds targetSpeed() {
        return target;
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(frontLeft.getSelectedSensorVelocity(),
                frontRight.getSelectedSensorVelocity());
    }

    @AutoLogOutput
    public ChassisSpeeds actualSpeed() {
        return kinematics.toChassisSpeeds(getSpeeds());
    }

    public Command defaultCommand(Supplier<ChassisSpeeds> supplier) {
        return run(() -> drive(supplier.get()));
    }
}
