package com.jasperlu.test;

import com.jasperlu.doppler.Doppler;

/**
 * Created by Jasper on 3/24/2015.
 */
public class TheDoppler {
    private static Doppler doppler;

    public static Doppler getDoppler() {
        if (doppler == null) {
            doppler = new Doppler();
        }
        return doppler;
    }
}
