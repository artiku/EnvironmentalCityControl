package transport_controller;

import city.RoadSystem;
import city.crossroad.CityEntranceCrossroad;
import city.crossroad.Crossroad;
import city.crossroad.MaintenanceCrossroad;
import city.road.Road;
import environment_centre.EnvironmentCentre;

public class RoadController {
    private EnvironmentCentre environmentCentre;

    public static void main(String[] args) {

    }

    public void createCityRoadSystem() {
        RoadSystem roadSystem = new RoadSystem();

        Crossroad crossroad = new CityEntranceCrossroad(0);
        Crossroad crossroad1 = new CityEntranceCrossroad(1);
        Crossroad crossroad2 = new CityEntranceCrossroad(2);
        Crossroad crossroad3 = new CityEntranceCrossroad(3);

        Crossroad crossroad4 = new MaintenanceCrossroad(4);
        Crossroad crossroad5 = new MaintenanceCrossroad(5);
        Crossroad crossroad6 = new MaintenanceCrossroad(6);
        Crossroad crossroad7 = new MaintenanceCrossroad(7);

        Crossroad crossroad8 = new Crossroad(8);
        Crossroad crossroad9 = new Crossroad(9);
        Crossroad crossroad10 = new Crossroad(10);
        Crossroad crossroad11 = new Crossroad(11);

        roadSystem.addroad(new Road(crossroad, crossroad10, "York St"));
        roadSystem.addroad(new Road(crossroad6, crossroad10, "Crawford St"));
        roadSystem.addroad(new Road(crossroad6, crossroad1, "Porter St"));
        roadSystem.addroad(new Road(crossroad10, crossroad8, "Dorset St"));
        roadSystem.addroad(new Road(crossroad10, crossroad5, "Kenrick Pl"));
        roadSystem.addroad(new Road(crossroad1, crossroad11, "Broadstone Pl"));
        roadSystem.addroad(new Road(crossroad5, crossroad11, "Blandford St"));
        roadSystem.addroad(new Road(crossroad5, crossroad8, "Baker St"));
        roadSystem.addroad(new Road(crossroad4, crossroad8, "George St"));
        roadSystem.addroad(new Road(crossroad4, crossroad2, "Gloucester Pl"));
        roadSystem.addroad(new Road(crossroad9, crossroad4, "Aybrook St"));
        roadSystem.addroad(new Road(crossroad9, crossroad3, "Cramer St"));
        roadSystem.addroad(new Road(crossroad9, crossroad7, "Thayler St"));

    }

}
