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

    public void drive() {

        this.travel();

        this.position = this.findNewDirection();
        this.position.driveThrough(this);
        roadsDriven++;
        this.travelConditions();
    }

    private void travelConditions() {
        if (roadsDriven % 5 == 0) {
            environmentCentre.sendData(this);
        }
        if (roadsDriven % 7 == 0) {
            environmentCentre.askPermission(this);
        }
    }

    private void travel() {
        int travelTime = 3 + (int)(Math.random() * 18);
        System.out.println(travelTime);

        try {
            Thread.sleep(travelTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Crossroad findNewDirection() {
        Set<Crossroad> adjacentCrossroads = environmentCentre.getCityRoadsystem().getAdjacentCrossroads(position);
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

    public void sendDataToEnvironmentCentre() {
        environmentCentre.sendData(this);
    }

    public void askEnvironmentCentreForPermission() {
        environmentCentre.askPermission(this);
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
}
