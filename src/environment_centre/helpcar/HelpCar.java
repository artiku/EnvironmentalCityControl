package environment_centre.helpcar;

import car.Car;
import city.crossroad.Crossroad;
import environment_centre.EnvironmentCentre;

import java.util.*;

import static constants.Constants.CAR_LOWER_TRAVEL_LIMIT;
import static constants.Constants.CAR_UPPER_TRAVEL_LIMIT;
import static constants.Constants.CONSOLE_RED_LOG_ON;

public class HelpCar extends Thread{

    private Set<Car> carsWantingHelp;
    private Crossroad position;
    private Random randomGenerator = new Random();
    private EnvironmentCentre environmentCentre;

    public HelpCar(Crossroad startPosition, Set<Car> carsWantingHelp, EnvironmentCentre environmentCentre) {
        this.position = startPosition;
        this.carsWantingHelp = carsWantingHelp;
        this.environmentCentre = environmentCentre;
        this.position = this.findNewDirection();
    }


    @Override
    public void run() {
        while(carsWantingHelp.size() > 0) {
            drive();
        }
        environmentCentre.finishHelpCarDuty();
    }

    private void drive() {
        this.travel();

        this.helpCarsOnCrossroad();
        this.position = this.findNewDirection();
    }

    private void helpCarsOnCrossroad() {
        List<Car> found = new ArrayList<>();
        for (Car car : carsWantingHelp) {

            if (car.getPosition() == this.position) {
                car.repairWheels();
                found.add(car);
                if (CONSOLE_RED_LOG_ON) System.err.println("Car #" + car.getCarId() + " wheels was repaired!");
            }
        }
        carsWantingHelp.removeAll(found);
    }

    private void travel() {
        int travelTime = CAR_LOWER_TRAVEL_LIMIT + (int)(Math.random() * CAR_UPPER_TRAVEL_LIMIT);

        if (CONSOLE_RED_LOG_ON) System.err.println("Help car is searching for those who need help! (" + travelTime + "ms)");

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
        for(Crossroad crossroad : adjacentCrossroads) {
            if (i == randomIndex) {
                return crossroad;
            }
            i++;
        }
        // Should never happen
        return null;
    }

}
