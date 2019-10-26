package frc.robot

import frc.robot.subsystems.drive.TurnToAngleCommand
import org.ghrobotics.lib.wrappers.hid.* // ktlint-disable no-wildcard-imports

object Controls {

    // This is the xbox controller on port 0 of the driverstation
    val driverFalconXbox = xboxController(0) {
        registerEmergencyMode()

        // This command will run while you hold B
        button(kB).change(TurnToAngleCommand())
    }

    fun update() {
        driverFalconXbox.update() // this polls all the buttons and stuff
    }
}

// just a smol helper that configures emergency mode on an Xbox controller
private fun FalconXboxBuilder.registerEmergencyMode() {
    button(kBack).changeOn {
        Robot.activateEmergency()
    }
    button(kStart).changeOn {
        Robot.recoverFromEmergency()
    }
}