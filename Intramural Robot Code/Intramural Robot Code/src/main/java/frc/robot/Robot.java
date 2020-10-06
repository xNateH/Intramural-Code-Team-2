/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.awt.Button;

//-----Imports-----\\
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  // -----Declarations-----\\
  WPI_TalonSRX masterFrontLeft;
  WPI_TalonSRX masterFrontRight;

  WPI_VictorSPX discipleBackLeft;
  WPI_VictorSPX discipleBackRight;

  WPI_TalonSRX rampMotor;
  //-----Shooter Motors-----\\
  WPI_TalonSRX shooterTopMotor;
  WPI_TalonSRX shooterBottomMotor;

  XboxController controller;

  double rightSide;
  double leftside;

  boolean toggle;
  boolean rampLoop;

  double fiveFeet;
  double tenFeet;
  double fifteenFeet;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    controller = new XboxController(0);

    //-----Initializing Motor Controllers-----\\
    masterFrontLeft = new WPI_TalonSRX(11);
    masterFrontLeft = new WPI_TalonSRX(12);
    discipleBackLeft = new WPI_VictorSPX(13);
    discipleBackRight = new WPI_VictorSPX(14);

    rampMotor = new WPI_TalonSRX(15);
    shooterTopMotor = new WPI_TalonSRX(9);
    shooterBottomMotor = new WPI_TalonSRX(10);


    rampLoop = false;
    toggle = true;

  fiveFeet = 0.1;
  tenFeet = 0.3;
  fifteenFeet = 0.6;
   

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    controller.getRawAxis(1);

    // -----Setting up the Axis-----
    rightSide = controller.getRawAxis(1) - controller.getRawAxis(4);
    leftside = controller.getRawAxis(1) - controller.getRawAxis(4);

    //-----Setting up Motors-----\\
    masterFrontRight.set(ControlMode.PercentOutput, rightSide);
    masterFrontLeft.set(ControlMode.PercentOutput, leftside);
    
    discipleBackRight.follow(masterFrontRight);
    discipleBackLeft.follow(masterFrontLeft);

    if (toggle && controller.getAButton()) {  // Only execute once per Button push
      toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
      if (rampLoop) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
        rampLoop= false;
        rampMotor.set(1);
      } else {
        rampLoop= true;
        rampMotor.set(0);
      }
    } else if(controller.getAButton() == false) { 
        toggle = true; // Button has been released, so this allows a re-press to activate the code above.
    }
    if (controller.getXButton()) {
      shooterBottomMotor.set(ControlMode.PercentOutput, fiveFeet);
      shooterTopMotor.set(ControlMode.PercentOutput, -fiveFeet);
    } else if(controller.getYButton()) {
      shooterBottomMotor.set(ControlMode.PercentOutput, tenFeet);
      shooterTopMotor.set(ControlMode.PercentOutput, -tenFeet);

    } else if (controller.getBButton()) {
      shooterBottomMotor.set(ControlMode.PercentOutput, fifteenFeet);
      shooterTopMotor.set(ControlMode.PercentOutput, -fifteenFeet);

    } else {
      shooterBottomMotor.set(ControlMode.PercentOutput, 0);
      shooterTopMotor.set(ControlMode.PercentOutput, 0);

    }



  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
