package frc.robot.subsystems;

import java.util.function.Supplier;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.talonsrx.CurrentLimitConfiguration;
import frc.robot.util.talonsrx.StatusFrameConfiguration;
import frc.robot.util.talonsrx.TalonSRXConfiguration;

public class Chassis extends SubsystemBase {
    private static final class Constants {
        private static final int FRONT_LEFT_CAN_ID = 2;
        private static final int REAR_LEFT_CAN_ID = 3;
        private static final int FRONT_RIGHT_CAN_ID = 4;
        private static final int REAR_RIGHT_CAN_ID = 5;

        public static final TalonSRXConfiguration CONFIG = new TalonSRXConfiguration(
                false,
                NeutralMode.Coast,
                CurrentLimitConfiguration.complex(40, 60, 1000),
                StatusFrameConfiguration.normal());
    }

    private final WPI_TalonSRX frontLeft, rearLeft, frontRight, rearRight;
    private final MotorControllerGroup left, right;
    private final DifferentialDrive drive;

    @AutoLogOutput(key = "Chassis/Target")
    private ChassisSpeeds targetSpeeds;

    public Chassis() {
        frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_CAN_ID);
        rearLeft = new WPI_TalonSRX(Constants.REAR_LEFT_CAN_ID);
        frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_CAN_ID);
        rearRight = new WPI_TalonSRX(Constants.REAR_RIGHT_CAN_ID);

        Constants.CONFIG.apply(frontLeft, rearLeft, frontRight, rearRight);

        left = new MotorControllerGroup(frontLeft, rearLeft);
        right = new MotorControllerGroup(frontRight, rearRight);
        drive = new DifferentialDrive(left, right);

        targetSpeeds = new ChassisSpeeds();
    }

    public Command defaultCommand(Supplier<ChassisSpeeds> supplier) {
        return run(() -> targetSpeeds = supplier.get());
    }

    @Override
    public void periodic() {
        drive.arcadeDrive(targetSpeeds.vxMetersPerSecond, targetSpeeds.omegaRadiansPerSecond, false);
    }
}
