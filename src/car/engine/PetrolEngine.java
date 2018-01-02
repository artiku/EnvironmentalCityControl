package car.engine;

public class PetrolEngine implements Engine {
    @Override
    public float emitPollution() {
        return 2f;
    }
}
