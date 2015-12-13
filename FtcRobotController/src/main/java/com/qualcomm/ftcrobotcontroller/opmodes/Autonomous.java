package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.util.Range;

public class Autonomous extends LinearOpMode {
    DcMotor leftfrontMotor;
    DcMotor leftbackMotor;
    DcMotor rightfrontMotor;
    DcMotor rightbackMotor;
    ModernRoboticsI2cGyro sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {
        leftfrontMotor =hardwareMap.dcMotor.get("leftfront_motor");
        leftbackMotor =hardwareMap.dcMotor.get("leftback_motor");
        rightfrontMotor = hardwareMap.dcMotor.get("rightfront_motor");
        rightbackMotor = hardwareMap.dcMotor.get("rightback_motor");

        rightfrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightbackMotor.setDirection(DcMotor.Direction.REVERSE);

        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftbackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        leftfrontMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro.calibrate();

        waitForStart();

        //move forward for 250 ms
        leftfrontMotor.setPower(1.0);
        leftbackMotor.setPower(1.0);
        rightfrontMotor.setPower(1.0);
        rightbackMotor.setPower(1.0);
        sleep(50);

        //turn 45 degrees
        turnAngle(-45.0);

        //move forward for 4250 ms
        leftfrontMotor.setPower(1.0);
        leftbackMotor.setPower(1.0);
        rightfrontMotor.setPower(1.0);
        rightbackMotor.setPower(1.0);
        sleep(3250);

        //telemetry.addData("forward", "");
        //turn 90 degrees
        turnAngle(-90.0);

        //move forward for 1000 ms

        leftfrontMotor.setPower(1.0);
        leftbackMotor.setPower(1.0);
        rightfrontMotor.setPower(1.0);
        rightbackMotor.setPower(1.0);
        sleep(1000);

        //stop robot
        leftfrontMotor.setPower(0.0);
        leftbackMotor.setPower(0.0);
        rightfrontMotor.setPower(0.0);
        rightbackMotor.setPower(0.0);
    }

    private void turnAngle(double theta) throws InterruptedException {
        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftbackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        double e = theta;
        double k = 1.0/90.0;
        double a;

        telemetry.addData("Heading", sensorGyro.getHeading());
        sensorGyro.calibrate();
        while(sensorGyro.isCalibrating());


        while(e > 0.1 || e < -0.1){
            a = sensorGyro.getHeading();
            if (a > 180){
                a = a - 360;
            }
            telemetry.addData("Heading", a);
            e = theta - a;
            telemetry.addData("error", e);
            double u = e * k;
            u = Range.clip(u,-1, 1);
            telemetry.addData("error", e);

            leftfrontMotor.setPower(u);
            leftbackMotor.setPower(u);
            rightfrontMotor.setPower(-u);
            rightbackMotor.setPower(-u);
        }
        leftfrontMotor.setPower(0.0);
        leftbackMotor.setPower(0.0);
        rightfrontMotor.setPower(0.0);
        rightbackMotor.setPower(0.0);
        
    }



    /*private void moveDistance(double distance) throws InterruptedException {

        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);
        sleep(distance);


        leftMotor.setMode(DcMotorController.RunMode .RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        leftMotor.setTargetPosition((int)(distance*1440));
        rightMotor.setTargetPosition((int)(distance*1440));

        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
       rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);

        while(leftMotor.isBusy()&& rightMotor.isBusy());

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

    }*/
}