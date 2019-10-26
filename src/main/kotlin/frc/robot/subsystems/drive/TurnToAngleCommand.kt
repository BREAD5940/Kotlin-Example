package frc.robot.subsystems.drive

import edu.wpi.first.wpilibj.controller.PIDController
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.twodim.geometry.Rotation2d
import org.ghrobotics.lib.mathematics.units.derived.degree
import org.ghrobotics.lib.mathematics.units.derived.toRotation2d

class TurnToAngleCommand : FalconCommand(DriveSubsystem) {

    lateinit var wantedAngle: Rotation2d
    val controller = PIDController(kP, 0.0, kD)

    override fun initialize() {
        wantedAngle = DriveSubsystem.gyro() + 90.degree.toRotation2d()
    }

    override fun execute() {

        // put math here
        val error = /* measured minus setpoint */ (DriveSubsystem.gyro() - wantedAngle).radian

        // output is error times kp
        val leftOutput = controller.calculate(error, 0.0)

        DriveSubsystem.tankDrive(leftOutput, -leftOutput)
    }

    companion object {
        const val kP = 0.1
        const val kD = 1.0
    }

}