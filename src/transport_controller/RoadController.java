package transport_controller;

import bird.SingingBird;
import car.Car;
import car.engine.DieselEngine;
import car.engine.ElectricEngine;
import car.engine.LemonadeEngine;
import car.engine.PetrolEngine;
import city.RoadSystem;
import city.crossroad.CityEntranceCrossroad;
import city.crossroad.Crossroad;
import city.crossroad.MaintenanceCrossroad;
import city.road.BadOldRoad;
import city.road.Road;
import environment_centre.EnvironmentCentre;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoadController {

    public static void main(String[] args) {
        createCityRoadSystem();
    }

    public static void createCityRoadSystem() {
        RoadSystem roadSystem = new RoadSystem();
        EnvironmentCentre environmentCentre = new EnvironmentCentre(roadSystem);

        Crossroad crossroad = new CityEntranceCrossroad(environmentCentre);
        Crossroad crossroad1 = new CityEntranceCrossroad(environmentCentre);
        Crossroad crossroad2 = new CityEntranceCrossroad(environmentCentre);
        Crossroad crossroad3 = new CityEntranceCrossroad(environmentCentre);

        Crossroad crossroad4 = new MaintenanceCrossroad();
        Crossroad crossroad5 = new MaintenanceCrossroad();
        Crossroad crossroad6 = new MaintenanceCrossroad();
        Crossroad crossroad7 = new MaintenanceCrossroad();

        Crossroad crossroad8 = new Crossroad();
        Crossroad crossroad9 = new Crossroad();
        Crossroad crossroad10 = new Crossroad();
        Crossroad crossroad11 = new Crossroad();

        roadSystem.addRoad(new Road(crossroad, crossroad10, "York St"));
        roadSystem.addRoad(new Road(crossroad6, crossroad10, "Crawford St"));
        roadSystem.addRoad(new Road(crossroad6, crossroad1, "Porter St"));
        roadSystem.addRoad(new Road(crossroad10, crossroad8, "Dorset St"));
        roadSystem.addRoad(new Road(crossroad10, crossroad5, "Kenrick Pl"));
        roadSystem.addRoad(new Road(crossroad1, crossroad11, "Broadstone Pl"));
        roadSystem.addRoad(new Road(crossroad5, crossroad11, "Blandford St"));
        roadSystem.addRoad(new Road(crossroad5, crossroad8, "Baker St"));
        roadSystem.addRoad(new Road(crossroad4, crossroad8, "George St"));
        roadSystem.addRoad(new Road(crossroad4, crossroad2, "Gloucester Pl"));
        roadSystem.addRoad(new Road(crossroad9, crossroad4, "Aybrook St"));
        roadSystem.addRoad(new Road(crossroad9, crossroad3, "Cramer St"));
        roadSystem.addRoad(new Road(crossroad9, crossroad7, "Thayler St"));

        roadSystem.addRoad(new BadOldRoad(crossroad3, crossroad7, "Montagu St"));
        roadSystem.addRoad(new BadOldRoad(crossroad11, crossroad7, "Gloucester Palace Mews"));

        List<Crossroad> cityEntranceList = new ArrayList<>();
        cityEntranceList.add(crossroad);
        cityEntranceList.add(crossroad1);
        cityEntranceList.add(crossroad2);
        cityEntranceList.add(crossroad3);

//        testCarGroup(cityEntranceList);
        buildCars(cityEntranceList);

        flyBird(environmentCentre);
    }

    private static void flyBird(EnvironmentCentre ec) {
        new SingingBird(ec).start();
    }

    private static void buildCars(List<Crossroad> cityEntranceList) {

        ExecutorService e = Executors.newFixedThreadPool(200);
        Random r = new Random();

        for (int i = 0; i < 90; i++) {
            int randomIndex = r.nextInt(cityEntranceList.size());
            e.execute(new Car(cityEntranceList.get(randomIndex), new DieselEngine()));
        }

        for (int i = 0; i < 90; i++) {
            int randomIndex = r.nextInt(cityEntranceList.size());
            e.execute(new Car(cityEntranceList.get(randomIndex), new PetrolEngine()));
        }

        for (int i = 0; i < 10; i++) {
            int randomIndex = r.nextInt(cityEntranceList.size());
            e.execute(new Car(cityEntranceList.get(randomIndex), new LemonadeEngine()));
        }

        for (int i = 0; i < 10; i++) {
            int randomIndex = r.nextInt(cityEntranceList.size());
            e.execute(new Car(cityEntranceList.get(randomIndex), new ElectricEngine()));
        }
    }

    private static void testCarGroup(List<Crossroad> cityEntranceList) {
        Car car = new Car(cityEntranceList.get(0), new DieselEngine());
        Car car1 = new Car(cityEntranceList.get(1), new DieselEngine());
        Car car2 = new Car(cityEntranceList.get(2), new DieselEngine());
        Car car3 = new Car(cityEntranceList.get(3), new DieselEngine());
        Car car33 = new Car(cityEntranceList.get(3), new DieselEngine());
        Car carE1 = new Car(cityEntranceList.get(3), new ElectricEngine());
        Car carP1 = new Car(cityEntranceList.get(3), new PetrolEngine());
        car.start();
        car1.start();
        car2.start();
        car3.start();
        car33.start();
        carE1.start();
        carP1.start();
    }
}
