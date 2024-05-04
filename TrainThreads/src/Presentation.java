import java.util.ArrayList;

public class Presentation {

    public static void main(String[] args) {

        //creating stations on the first line
        TrainStation s1 = new TrainStation("Warszawa Ochota");
        TrainStation s2 = new TrainStation("Warszawa Centralna");
        TrainStation s3 = new TrainStation("Warszawa Srodmiescie");
        TrainStation s4 = new TrainStation("Warszawa Stadion");
        TrainStation s5 = new TrainStation("Warszawa Praga");

        //making connections between stations
        s1.addBranch(s2, 100);

        s2.addBranch(s1, 100);
        s2.addBranch(s3, 100);

        s3.addBranch(s2, 100);
        s3.addBranch(s4, 100);

        s4.addBranch(s3, 100);
        s4.addBranch(s5, 100);

        s5.addBranch(s4, 100);

        //adding another one line to have an intersection
        TrainStation s6 = new TrainStation("Warszawa Koło");
        TrainStation s7 = new TrainStation("Warszawa Kasprzaka");

        TrainStation s9 = new TrainStation("Warszawa Jerozolimskie");
        TrainStation s10 = new TrainStation("Warszawa Rakowiec");

        //making connections between stations
        s6.addBranch(s7, 100);

        s7.addBranch(s6, 100);
        s7.addBranch(s3, 100);

        s3.addBranch(s7, 100);
        s3.addBranch(s9, 100);

        s9.addBranch(s3, 100);
        s9.addBranch(s10, 100);

        s10.addBranch(s9, 100);

        //adding all stations to the Arraylist
        ArrayList<TrainStation> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);
        stations.add(s4);
        stations.add(s5);
        stations.add(s6);
        stations.add(s7);
        stations.add(s9);
        stations.add(s10);

        //printing all connections, here is visible that one station (s3) has more than two connections
        stations.forEach(TrainStation::printConnections);

        //creating locomotives, two for one line
        Locomotive l1 = new Locomotive("L1", s1, s1, s5, 10, 500, 4, 205);
        Locomotive l2 = new Locomotive("L2", s1, s1, s5, 3, 300, 2, 120);
        Locomotive l3 = new Locomotive("L3", s6, s6, s10, 3, 800, 4, 185);
        Locomotive l4 = new Locomotive("L4", s6, s6, s10, 5, 300, 2, 120);

        //creating cars
        Car c1 = new PassengerCar("AB", "TY", 70, 20, 60, "Reserving seats", 20);
        Car c2 = new PassengerCar("AB", "TY", 300, 530, 60, "Reserving seats", 20);

        //here is presented how working three different exceptions
        try {
            l1.addCar(c1);
            l1.addCar(c1);
            l1.addCar(c1);
            l1.addCar(c1);
            l1.addCar(c1);
        } catch (CargoWeightException e) {
            e.printStackTrace();
        } catch (CarsWithElConnectionException e) {
            e.printStackTrace();
        } catch (CarOutOfBoundsExeption e) {
            e.printStackTrace();
        }

        try {
            l2.addCar(c2);
        } catch (CargoWeightException e) {
            e.printStackTrace();
        } catch (CarsWithElConnectionException e) {
            e.printStackTrace();
        } catch (CarOutOfBoundsExeption e) {
            e.printStackTrace();
        }

        try {
            l3.addCar(c1);
            l3.addCar(c1);
            l3.addCar(c1);
            l3.addCar(c1);
        } catch (CargoWeightException e) {
            e.printStackTrace();
        } catch (CarsWithElConnectionException e) {
            e.printStackTrace();
        } catch (CarOutOfBoundsExeption e) {
            e.printStackTrace();
        }

        //generating route
        l1.setRoute(l1.foundRoute(s1, s5));
        l2.setRoute(l2.foundRoute(s1, s5));
        l3.setRoute(l3.foundRoute(s6, s10));
        l4.setRoute(l4.foundRoute(s6, s10));

        System.out.println("================================================================================");

        //printing route
        l1.printRoute();
        l2.printRoute();
        l3.printRoute();
        l4.printRoute();


        //In these two methods i'm generating 100 stations and 25 locomotives, reading names of the stations and making connections between them from the file
        RouteElements.makeStations();
        RouteElements.makeLocomotives();
        RouteElements.makeCars();


        RouteElements.printStations();
        RouteElements.printLocomotives();



    }
}
