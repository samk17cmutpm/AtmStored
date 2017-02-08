package com.sam_nguyen.atmstored.data;

import java.util.UUID;

/**
 * Created by samnguyen on 2/8/17.
 */

public final class Atm {

    private final String id;

    private final String name;

    private final String address;

    private final float latitude;

    private final float longitude;

    public Atm(String name, String address, float latitude, float longitude) {
        this(UUID.randomUUID().toString(), name, address, latitude, longitude);
    }

    public Atm(String id, String name, String address, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
