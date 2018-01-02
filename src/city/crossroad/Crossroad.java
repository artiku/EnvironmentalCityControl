package city.crossroad;

import car.Car;
import environment_centre.EnvironmentCentre;

public class Crossroad {

    private static int crossroadCounter = 0;
    private int crossroadId;

    public Crossroad() {
        this.crossroadId = crossroadCounter++;
    }

    public void driveThrough(Car car) {
        //Nothing in this class, what can't be said about classes that extend
    }

    public int getCrossroadId() {
        return crossroadId;
    }
}
