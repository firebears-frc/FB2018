package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    private static final class Constants {
        private static final int CONTROLLER_PORT = 0;

        private static final int PDP_CAN_ID = 1;
    }

    private final CommandXboxController controller = new CommandXboxController(Constants.CONTROLLER_PORT);
    private final PowerDistribution pdp;

    public RobotContainer() {
        pdp = new PowerDistribution(Constants.PDP_CAN_ID, PowerDistribution.ModuleType.kCTRE);

        configureBindings();
    }

    private void configureBindings() {
    }
}
