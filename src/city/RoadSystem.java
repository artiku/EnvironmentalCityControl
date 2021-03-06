package city;

import city.crossroad.Crossroad;
import city.road.Road;

import java.util.*;
import java.util.stream.Collectors;

public class RoadSystem {
    private Set<Road> roads = new HashSet<>();
    private Map<Crossroad, Set<Road>> cityRoads = new HashMap<>();

    public boolean addRoad(Road road) {
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

    public Road findRoad(Crossroad crossroadA,Crossroad crossroadB) {
        for (Road road : roads) {
            if ((road.getCrossroadA() == crossroadA && road.getCrossroadB() == crossroadB) ||
                    (road.getCrossroadA() == crossroadB && road.getCrossroadB() == crossroadA)) return road;
        }
        // Should never happen
        return null;
    }

}
