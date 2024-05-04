public class RailRoadHazard extends Exception{

    Locomotive locomotive;

    public RailRoadHazard(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Oooops! Speed of the train is over the limit. Information about the train : " + locomotive.toString());
    }
}
