package frc.robot.subsystems.drive

import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.twodim.geometry.Rotation2d
import org.ghrobotics.lib.mathematics.units.derived.degree
import org.ghrobotics.lib.mathematics.units.derived.toRotation2d

class TurnToAngleCommand : FalconCommand(DriveSubsystem) {

    lateinit var wantedAngle: Rotation2d

    override fun initialize() {
        wantedAngle = DriveSubsystem.gyro() + 90.degree.toRotation2d()
    }

    var prevError = 0.0

    override fun execute() {

        // put math here
        val error = /* measured minus setpoint */ (DriveSubsystem.gyro() - wantedAngle).radian

        // output is error times kp
        val leftOutput = error * kP + kD * (error - prevError)

        DriveSubsystem.tankDrive(leftOutput, -leftOutput)
        prevError = error
    }

    companion object {
        const val kP = 0.1
        const val kD = 0.0
    }

}