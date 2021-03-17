package com.develogical.camera;

import sun.applet.resources.MsgAppletViewer_es;

public class Camera {
    private Sensor sensor;
    private MemoryCard memoryCard;
    private WriteCompleteListener writeCompleteListener;
    private boolean powerState = false;
    private boolean safeToPowerOff = false;

    public Camera (Sensor sensor, MemoryCard memoryCard, WriteCompleteListener writeCompleteListener) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
        this.writeCompleteListener = writeCompleteListener;
    }

    public void pressShutter() {
        if (powerState != false) {
            byte[] data = this.sensor.readData();
            this.memoryCard.write(data, this.writeCompleteListener);
        } else {
            return;
        }
    }

    public void dataCopyCompleted() {
        this.safeToPowerOff = true;
    }

    public void powerOn() {
        this.powerState = true;
        this.sensor.powerUp();
    }

    public void powerOff() {
        writeCompleteListener.writeComplete();
        if (safeToPowerOff) {
            this.sensor.powerDown();
            this.powerState = false;
        }
    }
}

