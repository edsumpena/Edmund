package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

public class Autonomous4 extends LinearOpMode {
    DcMotor leftfrontMotor;     //identify the motors and sensors
    DcMotor leftbackMotor;
    DcMotor rightfrontMotor;
    DcMotor rightbackMotor;
    ModernRoboticsI2cGyro sensorGyro;
    Servo climbers;

    @Override
    public void runOpMode() throws InterruptedException {
        leftfrontMotor = hardwareMap.dcMotor.get("leftfront_motor");     //grab the configure file on the phone
        leftbackMotor = hardwareMap.dcMotor.get("leftback_motor");       //and compare it to the motors/sensors
        rightfrontMotor = hardwareMap.dcMotor.get("rightfront_motor");  //in the code
        rightbackMotor = hardwareMap.dcMotor.get("rightback_motor");
        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("sensorGyro");
        climbers = hardwareMap.servo.get("climbers");

        waitForStart();

        moveDistance(0.3, 0.3, 14400);

    }

    private void turnAngle(double theta) throws InterruptedException {
        //add double theta, to tell how far we want to turn
        double e = theta;                   //identify that the error(how far we are from reaching our destination) is equal to theta
        double a = sensorGyro.getHeading(); //make double a the gyro's current heading
        telemetry.addData("Heading", a);    //print out what the current heading is
        //theta = theta + a;                  //make it so we don't have to calibrate the gyro because we
        telemetry.addData("Theta", theta);  //are adding the current heading to how far we want to turn
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
        double u;           //identify double u which will be the power of the motors

        while(e > 1 ^ e < -1) {              //create a while loop which will go on until error is close to 0
            a = sensorGyro.getHeading();      //refresh the current heading for a
            telemetry.addData("Heading", a);  //print out the current heading

            if (a > 180) {
                a = a - 360;
            }
            e = theta - a;                    //calculate error through what angle we want to be at by subtracting
            telemetry.addData("Error", e);    //the current heading

            if (e < -180) {     //create a while loop based on the current error
                e = e + 360;    //when the error is less then -180, you need to add 360 so you won't
            }                   //turn multiple times

            if (e > 180) {      //create another while loops based on the error
                e = e - 360;    //when the error is more than 180, you minus 360 so you won't turn
            }                   //multiple times

            //there are different types of turning speeds based on the errors
            if (e < -60){       //if the error is less then -60, then set the power to -0.7 to
                u = -0.7;       //turn right at a fast speed
            }else if (e < 0){   //or else if error is less then 0, set the power less, to -0.4
                u = -0.4;       //to be more precise
            }else if (e < 60){  //although, if you over shoot it, or you start at a positive error,
                u = 0.4;        //the power is set to reverse at positive 0.4
            }else{              //when you are over 60 error, the power must be set at 0.7 to
                u = 0.7;        //maximize the speed the robot turns
            }

            //if (e < -60){
            //    u = -0.6;
            //}else if (e < -30){
            //    u = -0.5;
            //}else if (e < 0){
            //    u = -0.4;
            //}else if (e < 30){
            //    u = 0.4;
            //}else if (e < 60){
            //    u = 0.5;
            //}else{
            //    u = 0.6;
            //}
            telemetry.addData("Power", u); //print out what the current power is
            tankDrive(u, -u);              //send the power to the wheels
        }
        tankDrive(0.0, 0.0);               //after the while loop finishes, set the power to 0
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

    private void moveDistance(double leftY, double rightY, int distance) throws InterruptedException {

        leftbackMotor.setTargetPosition(distance);
        rightbackMotor.setTargetPosition(distance);
        //one rotation is 1440

        leftbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightbackMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftfrontMotor.setPower(leftY);
        leftbackMotor.setPower(leftY);
        rightfrontMotor.setPower(rightY);
        rightbackMotor.setPower(rightY);

        while(rightbackMotor.getPower() != 0 && leftbackMotor.getPower() != 0) {
            //waiting
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
