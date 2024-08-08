package com.jwt.iu;

import com.jwt.iu.jwt.Jwts;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Date date = new Date();
        System.out.println((date.getTime() / 1000L));
        // Jwts jwts = new Jwts(null, null);
    }
}
