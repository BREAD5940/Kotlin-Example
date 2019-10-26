package frc.robot.subsystems.drive

import asSource
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.SPI
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnit
import org.ghrobotics.lib.motors.ctre.FalconSRX

object DriveSubsystem : FalconSubsystem() {

    val gyro = AHRS(SPI.Port.kMXP).asSource()
    val compressor = Compressor(9)

    val leftMotor: FalconSRX<NativeUnit> = FalconSRX(id = 1, model = DefaultNativeUnitModel).apply { /* this: FalconSRX<NativeUnit> */
        talonSRX.configFactoryDefault()
        outputInverted = false // TODO Replace me with what you found works for the leftMotor
    }

    val leftFollower: FalconSRX<NativeUnit> = FalconSRX(id = 2, model = DefaultNativeUnitModel).apply { /* this: FalconSRX<NativeUnit> */
        talonSRX.configFactoryDefault()
        outputInverted = false // TODO Replace me with what you found works for the leftFollower
        follow(leftMotor)
    }

    val rightMotor: FalconSRX<NativeUnit> = FalconSRX(id=3, model=DefaultNativeUnitModel).apply { /* this: FalconSRX<NativeUnit> */
        talonSRX.configFactoryDefault()
        outputInverted = true // TODO Replace me with what you found works for the rightMotor
    }

    val rightFollower: FalconSRX<NativeUnit> = FalconSRX(id = 4, model = DefaultNativeUnitModel).apply { /* this: FalconSRX<NativeUnit> */
        talonSRX.configFactoryDefault()
        outputInverted = true // TODO Replace me with what you found works for the rightFollower
        follow(rightMotor)
    }

    override fun lateInit() {
        defaultCommand = DriveCommand()
    }
}