package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("sensorGyro");

        /*leftfrontMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightfrontMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightbackMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        leftfrontMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        leftbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightfrontMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);*/

        sensorGyro.calibrate();
        while(sensorGyro.isCalibrating());


        waitForStart();

        //tankDrive(leftY, rightY, sleep for how many milsec)
        //tankDrive(1.0, 1.0, 500);

        //turn 45 degrees
        //turnAngle(-45.0);

        //move forward for 2500 ms
        //tankDrive(1.0, 1.0, 1500);

        //turn 90 degrees
        turnAngle(-90.0);

        //move forward for 1000 ms
        //tankDrive(1.0, 1.0, 750);

    }

    private void turnAngle(double theta) throws InterruptedException {

        double e = theta;
        double a;

        //double[] turnPowerArray = {-1.0, -1.0, -1.0, -1.0, -0.8, -0.4, 0.1, 0.4, 0.8, 1.0, 1.0, 1.0, 1.0};

        a = sensorGyro.getHeading();
        telemetry.addData("Heading", a);
        theta = theta + a;

        // We have desired position adjustment in theta, which is a [-179, 179]
        // the current orientation is zero after being calibrated
        // goal is to adjust direction so the final orientation is theta
        // since the reading from gyro is a [0, 359], we need to do number re-mapping for every
        // turn of the minute steering

        // loop until error is within 0.1 degree of the 360 degree circle
        // read the current orientation
        // compare desired orientation to the current.  If the difference is positive [0, 179]
        // we will need to do right turn action
        // If the difference is negative [-180, -1], we shall do left turn action

        while(e > 0.1 || e < -0.1){
            a = sensorGyro.getHeading();      // read the gyro heading to a
            telemetry.addData("Heading", a);  // range of a is 0-359

            e = theta - a;
            telemetry.addData("Error", e);

            while(e < -180){
                e = e + 360;}

            while(e > 180){
                e = e - 360;}

            double u;

            if (e < -60){
                u = -1;
            }else if (e < -30){
                u = -0.8;
            }else if (e < 0){
                u = -0.4;
            }else if (e < 30){
                u = 0.4;
            }else if (e < 60){
                u = 0.8;
            }else{
                u = 1.0;
            }
            telemetry.addData("Power", u);
            tankDrive(u, -u);
        }
        tankDrive(0, 0);
    }

    private void tankDrive(double leftY, double rightY) throws InterruptedException {
        rightY = -rightY;

        leftfrontMotor.setPower(leftY);
        leftbackMotor.setPower(leftY);
        rightfrontMotor.setPower(rightY);
        rightbackMotor.setPower(rightY);

    }

    private void tankDrive(double leftY, double rightY, long sleepAmount) throws InterruptedException {
        tankDrive(leftY, rightY);

        sleep(sleepAmount);

        tankDrive(0.0, 0.0);

    }

    private void encoderDrive(double leftY, double rightY, int encoderLength) throws InterruptedException {
        tankDrive(leftY, rightY);

        leftfrontMotor.setTargetPosition(encoderLength);
        leftbackMotor.setTargetPosition(encoderLength);
        rightfrontMotor.setTargetPosition(encoderLength);
        rightbackMotor.setTargetPosition(encoderLength);
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