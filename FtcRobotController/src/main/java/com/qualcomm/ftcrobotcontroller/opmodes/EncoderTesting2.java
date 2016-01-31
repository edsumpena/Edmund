package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Robert Lee on 1/24/2015.
 */
public class EncoderTesting2 extends LinearOpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    //ModernRoboticsI2cGyro sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        //sensorGyro.calibrate();

        waitForStart();

        moveDistance(1.0, 1.0, 10);
        moveDistance(0.0, 0.0, 0);
    }

    private void moveDistance(double left, double right, double distance) throws InterruptedException {

        leftMotor.setTargetPosition((int) (distance * 1440));
        rightMotor.setTargetPosition((int) (distance * 1440));

        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(left);
        rightMotor.setPower(right);

    }
}