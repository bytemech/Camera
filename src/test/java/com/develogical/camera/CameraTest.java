package com.develogical.camera;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class CameraTest {

    @Test
    //Switching the camera on powers up the sensor
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor,memorycard,writeCompleteListener);
        underTest.powerOn();
        verify(sensor).powerUp();
    }

    @Test
    //Switching the camera off powers down the sensor
    public void switchingTheCameraOffPowersDownTheSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor,memorycard,writeCompleteListener);
        underTest.dataCopyCompleted();
        underTest.powerOff();
        verify(sensor).powerDown();
    }

    @Test
    //Pressing the shutter with the power on copies data from the sensor to the memory card
    public void pressShutterandDataCopiestoMemoryCard() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor,memorycard,writeCompleteListener);
        underTest.powerOn();
        underTest.pressShutter();
        verify(sensor).readData();
        verify(memorycard).write(sensor.readData(), writeCompleteListener);
    }

    @Test
    //Pressing the shutter when the power is off does nothing
    public void pressShutterWhenPowerOffDoesNothing() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor,memorycard,writeCompleteListener);

        underTest.pressShutter();
        verifyNoMoreInteractions(sensor);
    }

    //If data is currently being written, switching the camera off does not power down the sensor
    @Test
    public void dataCurrentlyBeingWrittenDontPowerDownSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor, memorycard, writeCompleteListener);
        underTest.powerOff();
        verifyNoMoreInteractions(sensor);
    }

    //Once writing the data has completed, then the camera powers down the sensor
    @Test
    public void dataFinishedWritingDoPowerDownSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memorycard = mock(MemoryCard.class);
        WriteCompleteListener writeCompleteListener = mock(WriteCompleteListener.class);
        Camera underTest = new Camera(sensor, memorycard, writeCompleteListener);
        underTest.dataCopyCompleted();
        underTest.powerOff();
        verify(sensor).powerDown();
    }
}
