package com.jwt.iu;

import com.jwt.iu.jwt.Jwts;

import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String a = "123123.123123.123";
        String[] split = a.split("\\.");
        System.out.println(Arrays.toString(split));
        Date date = new Date();
        System.out.println((date.getTime() / 1000L));
        // Jwts jwts = new Jwts(null, null);
    }
}
