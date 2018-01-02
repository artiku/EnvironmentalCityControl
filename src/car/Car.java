package car;

import car.engine.Engine;
import city.crossroad.Crossroad;
import environment_centre.EnvironmentCentre;

import java.util.Random;
import java.util.Set;

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
                environmentCentre.askPermission(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void travel() {
        int travelTime = 30 + (int)(Math.random() * 180);

        System.out.println(this.toString() + "Travelled " + travelTime + "ms");

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

    public Crossroad getPosition() {
        return position;
    }

    public void setPosition(Crossroad position) {
        this.position = position;
    }

    public int getRoadsDriven() {
        return roadsDriven;
    }

    public Engine getEngineType() {
        return engineType;
    }

    @Override
    public String toString() {
        return String.format("Car #%s with %s engine.", this.carId, this.engineType.toString());
    }
}
