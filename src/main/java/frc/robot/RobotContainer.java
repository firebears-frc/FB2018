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

    private final PowerDistribution pdp;
    private final PneumaticsControlModule pcm_0, pcm_1;

    private final Chassis chassis;
    private final Intake intake;
    private final Shooter shooter;

    private final CommandXboxController controller;

    public RobotContainer() {
        pdp = new PowerDistribution(Constants.PDP_CAN_ID, PowerDistribution.ModuleType.kCTRE);
        pcm_0 = new PneumaticsControlModule(Constants.PCM_0_CAN_ID);
        pcm_1 = new PneumaticsControlModule(Constants.PCM_1_CAN_ID);

        chassis = new Chassis();
        intake = new Intake(pcm_0, pcm_1);
        shooter = new Shooter(pcm_0);

        controller = new CommandXboxController(Constants.CONTROLLER_PORT);

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
                .onTrue(Commands.sequence(
                        intake.down(),
                        intake.open(),
                        intake.intake()))
                .onFalse(Commands.sequence(
                        intake.close(),
                        Commands.waitSeconds(0.5),
                        intake.up(),
                        intake.hold()));

        controller.a()
                .onTrue(Commands.sequence(
                        shooter.spin(),
                        intake.stop(),
                        intake.open()))
                .onFalse(Commands.sequence(
                        shooter.punch(),
                        Commands.waitSeconds(0.5),
                        shooter.retract(),
                        intake.close()));
    }
}
