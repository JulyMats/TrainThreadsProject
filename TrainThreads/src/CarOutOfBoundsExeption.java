public class CarOutOfBoundsExeption extends Throwable {

    Locomotive locomotive;

    public CarOutOfBoundsExeption(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Oooops! Number of cars which you can add to the train is over the limit. Information about the train : " + locomotive.toString());
    }
}
