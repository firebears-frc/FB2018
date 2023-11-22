package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private static final class Constants {
        private static final int CONTROLLER_PORT = 0;

        public static final double JOYSTICK_DEADBAND = 0.05;

        private static final int PDP_CAN_ID = 1;
        private static final int PCM_0_CAN_ID = 0;
        private static final int PCM_1_CAN_ID = 1;
    }

    private final PowerDistribution pdp = new PowerDistribution(Constants.PDP_CAN_ID,
            PowerDistribution.ModuleType.kCTRE);
    private final PneumaticsControlModule pcm_0 = new PneumaticsControlModule(Constants.PCM_0_CAN_ID);
    private final PneumaticsControlModule pcm_1 = new PneumaticsControlModule(Constants.PCM_1_CAN_ID);
    private final Chassis chassis = new Chassis();
    private final Intake intake = new Intake(pcm_0, pcm_1);
    private final Shooter shooter = new Shooter(pcm_0);
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

        controller.a()
                .onTrue(Commands.parallel(
                        intake.stop(),
                        intake.open(),
                        shooter.spin()))
                .onFalse(Commands.sequence(
                        shooter.punch(),
                        Commands.waitSeconds(0.5),
                        shooter.retract(),
                        intake.close()));
    }
}
