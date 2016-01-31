
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Juan Pablo Martinez on 11/29/2015.
 */
public class MVMSTeleOpTankMode extends MVTeleOpTelemetry {
    DcMotor leftback_motor;     //identify all of the motors
    DcMotor rightback_motor;
    DcMotor leftfront_motor;
    DcMotor rightfront_motor;
    DcMotor arm;
    Servo climbers;

    final double open = 0.0;
    final double close = 0.5;

    @Override
    public void init() {
        leftback_motor = hardwareMap.dcMotor.get("leftback_motor");     //link each motor to each
        leftfront_motor = hardwareMap.dcMotor.get("leftfront_motor");   //of the motors in the
        rightback_motor = hardwareMap.dcMotor.get("rightback_motor");   //configure file on the
        rightfront_motor = hardwareMap.dcMotor.get("rightfront_motor"); //phone
        arm = hardwareMap.dcMotor.get("arm");
        climbers = hardwareMap.servo.get("climbers");
    }

    @Override
    public void loop() {                        //create a loop where the code goes

        float rightY = gamepad1.right_stick_y;  //create a float based off of the y axis of the left
        float leftY = -gamepad1.left_stick_y;   //and right joysticks

        telemetry.addData("RightY", rightY);    //print out the current y axis of both joysticks
        telemetry.addData("LeftY", leftY);

        leftY = (float) scaleInput(leftY);      //use the scaleInput function on the power to scale
        rightY = (float) scaleInput(rightY);    //it

        if (gamepad1.right_bumper) {
            leftY = leftY / 2;
            rightY = rightY / 2;
        }
        if (gamepad1.left_bumper) {
            leftY = leftY / 4;
            rightY = rightY / 4;
        }

        leftback_motor.setPower(leftY);         //set the power to each corresponding motor
        rightback_motor.setPower(rightY);
        leftfront_motor.setPower(leftY);
        rightfront_motor.setPower(rightY);

        boolean armUp = gamepad1.dpad_up;       //because the dpad is a boolean, we must use a
        boolean armDown = gamepad1.dpad_down;   //boolean data type

        telemetry.addData("Arm up", armUp);     //print out weather if it true or false
        telemetry.addData("Arm down", armDown);

        if (armUp){                             //if armUp is true
            arm.setPower(0.4);                  //set power to 0.4
        } else if (armDown) {                   //if armDown is true
            arm.setPower(-0.4);                 //set power to -0.4
        } else {                                //or else
            arm.setPower(0.0);                  //set power to 0
        }

        if (gamepad1.x) {
            climbers.setPosition(0.3);
            return;
        } else {
            climbers.setPosition(1.0);
            return;
        }
    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
