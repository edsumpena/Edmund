package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

public class Autonomous extends LinearOpMode {
    DcMotor leftfrontMotor;     //identify the motors and sensors
    DcMotor leftbackMotor;
    DcMotor rightfrontMotor;
    DcMotor rightbackMotor;
    //ModernRoboticsI2cGyro sensorGyro;
    Servo climbers;

    @Override
    public void runOpMode() throws InterruptedException {
        leftfrontMotor = hardwareMap.dcMotor.get("leftfront_motor");     //grab the configure file on the phone
        leftbackMotor = hardwareMap.dcMotor.get("leftback_motor");       //and compare it to the motors/sensors
        rightfrontMotor = hardwareMap.dcMotor.get("rightfront_motor");  //in the code
        rightbackMotor = hardwareMap.dcMotor.get("rightback_motor");
        //sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("sensorGyro");
        climbers = hardwareMap.servo.get("climbers");

        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        leftbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        moveDistance(0.2, 0.2, x);
        tankDrive(0.5, -0.5, 525);
        moveDistance(0.2, 0.2, x);
        tankDrive(0.5, -0.5, 525);
        moveDistance(0.2, 0.2, x);
        armDrive(0.2, 1000);
        moveDistance(-0.2, -0.2, x);
        tankDrive(0.5, -0.5, 1050);
        moveDistance(0.2, 0.2, x);

    }

    private void tankDrive(double leftY, double rightY) throws InterruptedException {
        rightY = -rightY;               //flip the power of the right side

        leftfrontMotor.setPower(leftY); //set the according power to each motor
        leftbackMotor.setPower(leftY);
        rightfrontMotor.setPower(rightY);
        rightbackMotor.setPower(rightY);

    }

    private void tankDrive(double leftY, double rightY, long sleepAmount) throws InterruptedException {
        tankDrive(leftY, rightY); //use the tankDrive function to add power

        sleep(sleepAmount);       //sleep for a certain amount of milliseconds

        tankDrive(0.0, 0.0);      //stop the motors

    }

    private void armDrive(double armPosition, long sleepAmount) throws InterruptedException {
        climbers.setPosition(armPosition); //set the arm power according to the double armPower

        sleep(sleepAmount);     //sleep for a certain amount of milliseconds

        climbers.setPosition(1.0);      //stop the motors
    }

    private void moveDistance(double leftY, double rightY, double distance) throws InterruptedException {
        rightY = -rightY;

        distance = distance*1440;

        leftbackMotor.setTargetPosition((int)distance);
        rightbackMotor.setTargetPosition((int)distance);
        //one rotation is 1440

        leftbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        leftfrontMotor.setPower(leftY);
        leftbackMotor.setPower(-leftY);
        rightfrontMotor.setPower(rightY);
        rightbackMotor.setPower(-rightY);

        while(rightbackMotor.getCurrentPosition() < distance && leftbackMotor.getCurrentPosition() < distance) {
            telemetry.addData("RightBackMotor: ", rightbackMotor.getCurrentPosition());
            telemetry.addData("LeftBackMotor: ", leftbackMotor.getCurrentPosition());
        }

        rightfrontMotor.setPower(0);
        leftfrontMotor.setPower(0);
    }

    /*private void encoderDrive(double leftY, double rightY, int encoderLength) throws InterruptedException {
        tankDrive(leftY, rightY);

        leftfrontMotor.setTargetPosition(encoderLength);
        leftbackMotor.setTargetPosition(encoderLength);
        rightfrontMotor.setTargetPosition(encoderLength);
        rightbackMotor.setTargetPosition(encoderLength);
    }*/
}
