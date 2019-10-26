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

        // unit circle gang
        val isToTheLeft = error > 0

        val leftOutput = if(isToTheLeft) 1.0 else -1.0

        DriveSubsystem.tankDrive(leftOutput, -leftOutput)

    }

}