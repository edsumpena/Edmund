package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robert Lee on 12/26/2015.
 */
public class EncoderTesting extends LinearOpMode {

    DcMotor leftfrontMotor;
    DcMotor leftbackMotor;
    DcMotor rightfrontMotor;
    DcMotor rightbackMotor;
    //ModernRoboticsI2cGyro sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {
        leftfrontMotor = hardwareMap.dcMotor.get("leftfront_motor");
        leftbackMotor = hardwareMap.dcMotor.get("leftback_motor");
        rightfrontMotor = hardwareMap.dcMotor.get("rightfront_motor");
        rightbackMotor = hardwareMap.dcMotor.get("rightback_motor");

        leftfrontMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightfrontMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        //sensorGyro.calibrate();

        waitForStart();

        leftfrontMotor.setPower(1);
        leftbackMotor.setPower(1);
        rightfrontMotor.setPower(-1);
        rightbackMotor.setPower(-1);

        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        leftbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftfrontMotor.setTargetPosition(2000);
        leftbackMotor.setTargetPosition(2000);
        rightfrontMotor.setTargetPosition(2000);
        rightbackMotor.setTargetPosition(2000);

    }

}