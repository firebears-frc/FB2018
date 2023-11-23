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

public class Chassis extends SubsystemBase {
    private static final class Constants {
        private static final int FRONT_LEFT_CAN_ID = 2;
        private static final int REAR_LEFT_CAN_ID = 3;
        private static final int FRONT_RIGHT_CAN_ID = 4;
        private static final int REAR_RIGHT_CAN_ID = 5;

        public static final int PEAK_CURRENT_LIMIT = 60;
        public static final int PEAK_CURRENT_DURATION = 1000;
        public static final int CONTINUOUS_CURRENT_LIMIT = 40;
    }

    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_CAN_ID);
    private final WPI_TalonSRX rearLeft = new WPI_TalonSRX(Constants.REAR_LEFT_CAN_ID);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_CAN_ID);
    private final WPI_TalonSRX rearRight = new WPI_TalonSRX(Constants.REAR_RIGHT_CAN_ID);
    private final MotorControllerGroup left = new MotorControllerGroup(frontLeft, rearLeft);
    private final MotorControllerGroup right = new MotorControllerGroup(frontRight, rearRight);
    private final DifferentialDrive drive = new DifferentialDrive(left, right);

    private ChassisSpeeds target = new ChassisSpeeds();

    public Chassis() {
        frontLeft.configFactoryDefault();
        frontLeft.setInverted(false);
        frontLeft.setNeutralMode(NeutralMode.Coast);
        frontLeft.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        frontLeft.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        frontLeft.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);

        rearLeft.configFactoryDefault();
        rearLeft.setInverted(false);
        rearLeft.setNeutralMode(NeutralMode.Coast);
        rearLeft.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        rearLeft.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        rearLeft.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);
        
        frontRight.configFactoryDefault();
        frontRight.setInverted(false);
        frontRight.setNeutralMode(NeutralMode.Coast);
        frontRight.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        frontRight.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        frontRight.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);
    
        rearRight.configFactoryDefault();
        rearRight.setInverted(false);
        rearRight.setNeutralMode(NeutralMode.Coast);
        rearRight.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        rearRight.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        rearRight.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);
    }

    private void drive(ChassisSpeeds speeds) {
        target = speeds;

        drive.arcadeDrive(speeds.vxMetersPerSecond, speeds.omegaRadiansPerSecond, false);
    }

    @AutoLogOutput
    public ChassisSpeeds targetSpeeds() {
        return target;
    }

    public Command defaultCommand(Supplier<ChassisSpeeds> supplier) {
        return run(() -> drive(supplier.get()));
    }
}
