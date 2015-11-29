package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
    DcMotor left_motor;
    DcMotor right_motor;
    //DcMotor left_arm;
    //Servo left_hand;
    //Servo right_hand;

    @Override
    public void init() {
        //Makeing all of the motors on the robot recognizable
        left_motor = hardwareMap.dcMotor.get("left_drive");
        right_motor = hardwareMap.dcMotor.get("right_drive");
        //left_arm = hardwareMap.dcMotor.get("left_arm ");
        //left_hand = hardwareMap.servo.get("left_hand");
        //right_hand = hardwareMap.servo.get("right_hand");
    }

    @Override
    public void loop() {
        //setting the power on the two DcMotors in a leaner function
        float rightY = -gamepad1.right_stick_y;
        float leftY = -gamepad1.left_stick_y;
        left_motor.setPower(leftY);
        right_motor.setPower(rightY);

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
}
