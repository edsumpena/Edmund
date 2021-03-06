
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robert Lee on 12/19/2015.
 */
public class MVMSTeleOpArcadeMode extends MVTeleOpTelemetry {
    DcMotor leftback_motor;     //identify each motor
    DcMotor rightback_motor;
    DcMotor leftfront_motor;
    DcMotor rightfront_motor;

    @Override
    public void init() {
        leftback_motor = hardwareMap.dcMotor.get("leftback_motor");     //link each motor we
        leftfront_motor = hardwareMap.dcMotor.get("leftfront_motor");   //identified to the
        rightback_motor = hardwareMap.dcMotor.get("rightback_motor");   //configure file on the
        rightfront_motor = hardwareMap.dcMotor.get("rightfront_motor"); //phone
    }

    @Override
    public void loop() {                                    //create a loop where the code goes
        float y = -gamepad1.right_stick_y;                  //create a float which is based off
        float x = gamepad1.right_stick_x;                   //of what the current x/y of the right
                                                            //joystick is
        telemetry.addData("Y axis", gamepad1.right_stick_y);//print out the current x/y axis of
        telemetry.addData("X axis", gamepad1.right_stick_x);//the joystick

        float left = y + x;                                 //set power to turn when x axis is used
        float right = y - x;                                //but to move forwards and backwards
                                                            //when y axis is used
        left = (float)scaleInput(left);                     //use the scaleInput function to scale
        right = (float)scaleInput(right);                   //the power

        leftback_motor.setPower (left);                     //set the left power to each left motor
        leftfront_motor.setPower (left);
        rightback_motor.setPower (-right);                  //set the right power to each right motor
        rightfront_motor.setPower (-right);

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
