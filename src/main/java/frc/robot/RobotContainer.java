package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;

public class RobotContainer {
    private static final class Constants {
        private static final int CONTROLLER_PORT = 0;

        public static final double JOYSTICK_DEADBAND = 0.05;

        private static final int PDP_CAN_ID = 1;
        private static final int PCM_CAN_ID = 0;
    }

    private final PowerDistribution pdp = new PowerDistribution(Constants.PDP_CAN_ID,
            PowerDistribution.ModuleType.kCTRE);
    private final PneumaticsControlModule pcm = new PneumaticsControlModule(Constants.PCM_CAN_ID);
    private final Chassis chassis = new Chassis();
    private final Intake intake = new Intake(pcm);
    private final CommandXboxController controller = new CommandXboxController(Constants.CONTROLLER_PORT);

    public RobotContainer() {
        configureBindings();
    }

    private ChassisSpeeds getChassisSpeeds() {
        return new ChassisSpeeds(
                -MathUtil.applyDeadband(controller.getLeftY(), Constants.JOYSTICK_DEADBAND),
                0.0,
                -MathUtil.applyDeadband(controller.getLeftX(), Constants.JOYSTICK_DEADBAND));
    }

    private void configureBindings() {
        chassis.setDefaultCommand(chassis.defaultCommand(this::getChassisSpeeds));

        controller.rightTrigger()
                .onTrue(Commands.parallel(
                        intake.open(),
                        intake.down(),
                        intake.intake()))
                .onFalse(Commands.parallel(
                        intake.close(),
                        intake.up(),
                        intake.hold()));

        controller.b()
                .onTrue(Commands.parallel(
                        intake.stop(),
                        intake.open()
                // TODO: Start spinners
                ))
                .onFalse(Commands.sequence(
                        // TODO: Punch
                        Commands.waitSeconds(0.5),
                        // TODO: Un-punch
                        intake.close()));
    }
}
