public class CarsWithElConnectionException extends Throwable {

    Locomotive locomotive;

    public CarsWithElConnectionException(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Oooops! Number of cars that required electrical connections is over the limit. Information about the train : " + locomotive.toString());
    }
}
