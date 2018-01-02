package city.road;

import city.crossroad.Crossroad;

public class Road {

    private String roadName;
    private Crossroad crossroadA, crossroadB;

    public Road(Crossroad crossroadA, Crossroad crossroadB, String roadName) {
        this.crossroadA = crossroadA;
        this.crossroadB = crossroadB;
        this.roadName = roadName;
    }

    public Crossroad getCrossroadA() {
        return crossroadA;
    }

    public Crossroad getCrossroadB() {
        return crossroadB;
    }
}
