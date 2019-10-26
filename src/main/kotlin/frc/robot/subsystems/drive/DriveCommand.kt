package frc.robot.subsystems.drive

import edu.wpi.first.wpilibj.GenericHID
import frc.robot.Controls
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.utils.withDeadband
import org.ghrobotics.lib.wrappers.hid.getX
import org.ghrobotics.lib.wrappers.hid.getY
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.withSign

class DriveCommand : FalconCommand(DriveSubsystem) {
        override fun isFinished() = false

        override fun execute() {
            // negative because xbox is negative Y when pushed forward
            val forward = -speedSource() // same as -1 * speedSource.invoke()
            val turn = rotationSource()

            val wantedLeftOutput: Double // these will change once we add turning
            var wantedRightOutput: Double

            val maximum = max(forward.absoluteValue, turn.absoluteValue).withSign(forward)

            if (forward >= 0) {
                if (turn >= 0) {
                    // turn angle is in 1st quadrant
                    wantedLeftOutput = maximum
                    wantedRightOutput = forward - turn
                } else {
                    // turn angle is in 2nd quadrant
                    wantedLeftOutput = forward + turn
                    wantedRightOutput = maximum
                }
            } else {
                if (turn >= 0) {
                    // turn angle is in 3rd quadrant
                    wantedLeftOutput = forward + turn
                    wantedRightOutput = maximum
                } else {
                    // turn angle is in 4th quadrant
                    wantedLeftOutput = maximum
                    wantedRightOutput = forward - turn
                }
            }

            DriveSubsystem.leftMotor.setDutyCycle(wantedLeftOutput)
            DriveSubsystem.rightMotor.setDutyCycle(wantedRightOutput)

        }

        override fun end(interrupted: Boolean) {
            DriveSubsystem.leftMotor.setNeutral()
            DriveSubsystem.rightMotor.setNeutral()
        }

        companion object {
            private const val kDeadband = 0.05
            val speedSource by lazy { Controls.driverFalconXbox.getY(GenericHID.Hand.kLeft).withDeadband(kDeadband) }
            val rotationSource by lazy { Controls.driverFalconXbox.getX(GenericHID.Hand.kRight).withDeadband(kDeadband) }
        }
    }
