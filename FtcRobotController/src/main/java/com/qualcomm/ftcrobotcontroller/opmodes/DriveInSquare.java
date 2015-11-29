package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.util.Range;

/*
 * An example linear op mode where the pushbot
 * will drive in a square pattern using sleep()
 * and a for loop.
 */
public class DriveInSquare extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    ModernRoboticsI2cGyro sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro.calibrate();

        waitForStart();
        double k = 1.0/90.0;

        for(int i = 0; i < 4; i ++) {
            //telemetry.addData("Hello", "");
            double a;
            telemetry.addData("Heading", sensorGyro.getHeading());
            sensorGyro.calibrate();
            while(sensorGyro.isCalibrating());
            double e = 90;

            while(e > 0.1 || e < -0.1){
                a = sensorGyro.getHeading();
                telemetry.addData("Heading", sensorGyro.getHeading());
                e = 90 - a;
                double u = e * k;
                u = Range.clip(u,-1, 1);
                /*if(u<0.1 && u>0){
                    u = 0.1;
                }
                else if(u<0 && u>-0.1){
                    u = -0.1;
                }*/
                telemetry.addData("error", e);
                leftMotor.setPower(u);
                rightMotor.setPower(-u);
            }

            sensorGyro.calibrate();
            while(sensorGyro.isCalibrating());

            telemetry.addData("error",e);
            telemetry.addData("Turned ", i);
            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);
            sleep(2000);
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
        }
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }
}
