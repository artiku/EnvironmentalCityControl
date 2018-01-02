package car;

import car.engine.Engine;
import city.crossroad.Crossroad;
import environment_centre.EnvironmentCentre;

import java.util.Random;
import java.util.Set;

import static constants.Constants.CAR_LOWER_TRAVEL_LIMIT;
import static constants.Constants.CAR_UPPER_TRAVEL_LIMIT;

public class Car extends Thread{

    private EnvironmentCentre environmentCentre;
    private int roadsDriven = 0;
    private Crossroad position;
    private Random randomGenerator = new Random();
    private Engine engineType;
    private static int carCounter = 0;
    private int carId = carCounter++;


    public Car(Crossroad startPosition, Engine engineType) {
        this.position = startPosition;
        this.engineType = engineType;
    }


    @Override
    public void run() {
        while(!Thread.interrupted()) {
            drive();
        }
    }


    private void drive() {

        this.position.driveThrough(this);
        this.travel();
        this.position = this.findNewDirection();
        roadsDriven++;
        this.travelConditions();
    }

    private void travelConditions() {
        if (roadsDriven % 5 == 0) {
            environmentCentre.sendData(this);
        }
        if (roadsDriven % 7 == 0) {
            try {
                while(!environmentCentre.askPermission(this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void travel() {
        int travelTime = CAR_LOWER_TRAVEL_LIMIT + (int)(Math.random() * CAR_UPPER_TRAVEL_LIMIT);

        System.out.println(this + " Travelled " + travelTime + "ms and " + roadsDriven % 7 + " roads");

        try {
            Thread.sleep(travelTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Crossroad findNewDirection() {
        Set<Crossroad> adjacentCrossroads = environmentCentre.getCityRoadSystem().getAdjacentCrossroads(position);
        int randomIndex = this.randomGenerator.nextInt(adjacentCrossroads.size());
        int i = 0;
        for(Crossroad crossroad : adjacentCrossroads)
        {
            if (i == randomIndex) {
                return crossroad;
            }
            i++;
        }
        // Should never happen
        return null;
    }

    public void setEnvironmentCentre(EnvironmentCentre environmentCentre) {
        this.environmentCentre = environmentCentre;
    }

    public int getCarId() {
        return carId;
    }

    public Engine getEngineType() {
        return engineType;
    }

    @Override
    public String toString() {
        return String.format("Car #%s with %s engine.", this.carId, this.engineType.toString());
    }
}
