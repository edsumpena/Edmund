package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Rosa on 11/29/2015.
 */
public class MVMSTeleOpTankMode extends OpMode {
    //motor variables
    /*final double LEFT_OPEN_POSITION = 0.0;
    final double LEFT_CLOSED_POSITION = 0.5;
    final double RIGHT_OPEN_POSITION = 1.0;
    final double RIGHT_CLOSED_POSITION = 0.5;
    */
    DcMotor leftback_motor;
    DcMotor rightback_motor;
    DcMotor leftfront_motor;
    DcMotor rightfront_motor;
    //DcMotor left_arm;
    //Servo left_hand;
    //Servo right_hand;

    @Override
    public void init() {
        //Makeing all motors on the robot recognizable
        leftback_motor = hardwareMap.dcMotor.get("leftback_motor");
        rightback_motor = hardwareMap.dcMotor.get("rightback_motor");
        leftback_motor = hardwareMap.dcMotor.get("leftfront_motor");
        rightback_motor = hardwareMap.dcMotor.get("rightfront_motor")
        //left_arm = hardwareMap.dcMotor.get("left_arm ");
        //left_hand = hardwareMap.servo.get("left_hand");
        //right_hand = hardwareMap.servo.get("right_hand");
    }

    @Override
    public void loop() {
        //setting the power on the two DcMotors in a leaner function
        float rightY = gamepad1.right_stick_y;
        float leftY = -gamepad1.left_stick_y;
        leftY = Range.clip(leftY, -1,1);
        rightY = Range.clip(rightY, -1, 1);
        leftY = (float)scaleInput(leftY);
        rightY = (float)scaleInput(rightY);
        leftback_motor.setPower(leftY);
        rightback_motor.setPower(rightY);
        leftfront_motor.setPower(leftY);
        rightfront_motor.setPower(rightY);

        /**setting power to the arm
        if(gamepad1.y) {
            leftArm.setPower(0.1);
        } else if (gamepad1.b){
            leftArm.setPower(-0.1);
        } else {
            leftArm.setPower(0.0);
        }
        //Moving the servo on the main arm
        if(gamepad1.x) {
            leftGripper.setPosition(LEFT_OPEN_POSITION);
            rightGripper.setPosition(RIGHT_OPEN_POSITION);
        }
        if(gamepad1.a) {
            leftGripper.setPosition(LEFT_CLOSED_POSITION);
            rightGripper.setPosition(RIGHT_CLOSED_POSITION);
        }**/

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
