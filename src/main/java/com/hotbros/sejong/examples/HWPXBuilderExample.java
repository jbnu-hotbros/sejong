package com.hotbros.sejong.examples;

import com.hotbros.sejong.builder.HWPXBuilder;

public class HWPXBuilderExample {

    public static void main(String[] args) {
        try {
            HWPXBuilder builder = new HWPXBuilder();
            builder.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
