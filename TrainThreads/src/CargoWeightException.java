public class CargoWeightException extends Throwable {

    Locomotive locomotive;

    public CargoWeightException(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Oooops! Weight of the train is over the limit. Information about the train : " + locomotive.toString());
    }
}
