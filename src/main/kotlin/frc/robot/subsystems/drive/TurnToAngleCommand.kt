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

    override fun execute() {

        // put math here
        val error = /* measured minus setpoint */ (DriveSubsystem.gyro() - wantedAngle).radian

        // output is error times kp
        val leftOutput = error * kP

        DriveSubsystem.tankDrive(leftOutput, -leftOutput)
    }

    companion object {
        const val kP = 0.1
    }

}