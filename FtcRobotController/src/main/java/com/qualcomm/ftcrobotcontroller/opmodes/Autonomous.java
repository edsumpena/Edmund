package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Autonomous extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        waitForStart();

        leftMotor.setPower(-1.0);
        rightMotor.setPower(1.0);
        sleep(500);

        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);
        sleep(2000);

        leftMotor.setPower(-1.0);
        rightMotor.setPower(1.0);
        sleep(500);

        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);
        sleep(1000);

    }
}