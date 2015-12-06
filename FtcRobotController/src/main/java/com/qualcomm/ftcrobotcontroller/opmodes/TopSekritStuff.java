package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Robert Lee on 12/6/2015.
 */
public class TopSekritStuff extends OpMode{

    DcMotor spinthing1;
    DcMotor spinthing2;

    @Override
    public void init() {
        spinthing1 = hardwareMap.dcMotor.get("spinthing1");
        spinthing2 = hardwareMap.dcMotor.get("spinthing2");
    }

    @Override
    public void loop() {

        if (gamepad1.right_bumper) {
            spinthing1.setPower(-1);
            spinthing2.setPower(1);
        }else{
            spinthing1.setPower(0);
            spinthing2.setPower(0);
        }

        if (gamepad1.left_bumper) {
            spinthing1.setPower(1);
            spinthing2.setPower(-1);
        }else{
            spinthing1.setPower(0);
            spinthing2.setPower(0);
        }
    }
}