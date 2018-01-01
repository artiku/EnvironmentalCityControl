package city_roads;

import java.util.*;
import java.util.stream.Collectors;

public class RoadSystem {
    private Set<Crossroad> maintenanceCrossroads = new HashSet<>();
    private Set<Crossroad> cityEntranceCrossroads = new HashSet<>();
    private Set<Road> roads = new HashSet<>();
    private Map<Crossroad, Set<Road>> cityRoads = new HashMap<>();


    public boolean addMaintenanceCentre(Crossroad crossroad) {
        return maintenanceCrossroads.add(crossroad);
    }


    public boolean addCityEntrance(Crossroad crossroad) {
        return cityEntranceCrossroads.add(crossroad);
    }

    public boolean addroad(Road road) {
        if (!roads.add(road)) return false;

        cityRoads.putIfAbsent(road.getCrossroadA(), new HashSet<>());
        cityRoads.putIfAbsent(road.getCrossroadB(), new HashSet<>());

        cityRoads.get(road.getCrossroadA()).add(road);
        cityRoads.get(road.getCrossroadB()).add(road);

        return true;
    }

    public Set<Crossroad> getAdjacentCrossroads(Crossroad crossroad) {
        return cityRoads.get(crossroad).stream()
                .map(road -> road.getCrossroadA().equals(crossroad) ? road.getCrossroadB() : road.getCrossroadA())
                .collect(Collectors.toSet());
    }

    public Set<Crossroad> getMaintenanceCrossroads() {
        return Collections.unmodifiableSet(maintenanceCrossroads);
    }

    public Set<Crossroad> getCityEntranceCrossroads() {
        return Collections.unmodifiableSet(cityEntranceCrossroads);
    }

}
