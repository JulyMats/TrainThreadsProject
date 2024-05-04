import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RouteElements {

    private static ArrayList<TrainStation> stations = new ArrayList<>();;
    private static ArrayList<Car> cars = new ArrayList<>();
    private static ArrayList<Locomotive> locomotives = new ArrayList<>();

    public static ArrayList<TrainStation> getStations() {
        return stations;
    }

    public static void setStations(ArrayList<TrainStation> stations) {
        RouteElements.stations = stations;
    }

    public static ArrayList<Car> getCars() {
        return cars;
    }

    public static void setCars(ArrayList<Car> cars) {
        RouteElements.cars = cars;
    }

    public static ArrayList<Locomotive> getLocomotives() {
        return locomotives;
    }

    public static void setLocomotives(ArrayList<Locomotive> locomotives) {
        RouteElements.locomotives = locomotives;
    }

    public static void createStation(String name) {
        stations.add(new TrainStation(name));
    }

    public static void removeStation(int name) {
        stations.removeIf(trainStation -> trainStation.getIdNumber() == name);
    }

    public static void createLocomotive(String name, int homeStation, int startStation, int endStation, int maxNumOfCars, double maxCargoWeight, int maxNumOfCarsWithElConnect, double startSpeed) {
        Locomotive locomotive = new Locomotive(name, stations.get(homeStation), stations.get(startStation), stations.get(endStation), maxNumOfCars, maxCargoWeight, maxNumOfCarsWithElConnect, startSpeed);
        locomotives.add(locomotive);
        locomotive.startMoving();
    }

    public static void removeLocomotive(String name) {
        locomotives.removeIf(locomotive -> locomotive.getName().equals(name));
    }

    public static void makeStations () {
        //making stations
        ArrayList<String> stationNames = new ArrayList<>();
        try {
            File file = new File("stationList.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!(stationNames.contains(line) || line.isEmpty()) && !line.equals("end"))
                    stationNames.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file " + e.getMessage());
        }

        for(String name : stationNames) {
            stations.add(new TrainStation(name));
        }

        //making connections for stations
        try {
            File file = new File("stationList.txt");
            Scanner scanner = new Scanner(file);
            String prevLine = null;
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if(!line.isEmpty() && !line.equals("end")) {
                    if (!nextLine.isEmpty() && !nextLine.equals("end")) {
                        stations.get(stationNames.indexOf(line))
                                .addBranch(stations.get(stationNames.indexOf(nextLine)), (int)((Math.random() * 25) + 25));
                    }
                    if (prevLine != null && !prevLine.isEmpty())
                        stations.get(stationNames.indexOf(line))
                                .addBranch(stations.get(stationNames.indexOf(prevLine)), (int)((Math.random() * 25) + 25));
                }
                prevLine = line;
                line = nextLine;
            }
        } catch (IOException e) {
            System.out.println("Error reading file " + e.getMessage());
        }
    }

    public static void makeLocomotives() {

        List<String> locomotiveNames = List.of(
                "KX", "DZ", "WJ", "EB", "QU",
                "OF", "GN", "PM", "HY", "CT",
                "AR", "VS", "LI", "MJ", "UF",
                "GS", "PY", "NB", "ZC", "TH",
                "XR", "QI", "WE", "OY", "DA"
        );

        for (int i = 0; i < 25; i++) {
            locomotives.add(new Locomotive(
                    locomotiveNames.get(i),
                    stations.get((int)(Math.random() * stations.size())),
                    stations.get((int)(Math.random() * stations.size())),
                    stations.get((int)(Math.random() * stations.size())),
                    (int)(Math.random() * 10) + 5,
                    (int)(Math.random() * 300) + 200,
                    (int)(Math.random() * 2) + 1,
                    (int)(Math.random() * 50) + 100
            ));
        }
    }

    public static void makeCars() {
        List<String> safetyCategories = List.of("Train Management System", "Signaling System", "Automatic Train Control", "Level Crossings", "Train Horn", "Track Maintenance", "Fire Safety");
        List<String> senderNames = List.of("Crimea Railway", "Hector Rail", "Green Cargo", "Trans blue", "Arlanda Express", "Regiotrans", "Yamal Railway");
        List<String> seatTypesNames = List.of("Sleepers", "Couchettes", "Reclining seats", "Reserving seats");
        List<String> baggageTypes = List.of("Duffel Bags", "Wheeled Luggage", "Hardside Luggage");
        List<String> cuisineTypes = List.of("Italian", "Spanish", "Chinese", "Korean");
        List<String> liquidTypes = List.of("Water", "Milk", "Urine", "Bromine");
        List<String> gasTypes = List.of("Hydrogen", "Natural gas", "Propane", "Oxygen", "Helium");
        List<String> fuelTypes = List.of("Petrol", "Kerosene", "Diesel", "Natural gas", "Water gas");
        List<String> models = List.of("E2", "T6", "PB4", "HY5");

        for (int i = 0; i < 5; i++) {
            cars.add(new PassengerCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    seatTypesNames.get((int)(Math.random() * seatTypesNames.size())),
                    (int)(Math.random() * 15)
            ));
            cars.add(new PostOfficeCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    (int)(Math.random() * 15)
            ));
            cars.add(new BaggagePostOfficeCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    baggageTypes.get((int)(Math.random() * baggageTypes.size()))
            ));
            cars.add(new DiningCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    (int)(Math.random() * 15),
                    (int)(Math.random() * 10),
                    cuisineTypes.get((int)(Math.random() * cuisineTypes.size()))
            ));
            cars.add(new FreightCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200, (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100
            ));
            cars.add(new HeavyFreightCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100
            ));
            cars.add(new RefrigeratedCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    (int)(Math.random() * 25 - 40)
            ));
            cars.add(new LiquidTankCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    liquidTypes.get((int)(Math.random()*liquidTypes.size())),
                    models.get((int)(Math.random()*models.size()))
            ));
            cars.add(new GasTankCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    gasTypes.get((int)(Math.random()*gasTypes.size())),
                    models.get((int)(Math.random()*models.size()))
            ));
            cars.add(new ExplosiveCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    false,
                    Math.random() * 100
            ));
            cars.add(new ToxicCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    Math.random() * 100,
                    fuelTypes.get((int)(Math.random()*fuelTypes.size()))
            ));
            cars.add(new ToxicLiquidCar(
                    senderNames.get((int)(Math.random()*senderNames.size())),
                    safetyCategories.get((int)(Math.random()*safetyCategories.size())),
                    Math.random() * 100,
                    Math.random() * 400 + 200,
                    (int)(Math.random() * 100 + 20),
                    Math.random() * 500,
                    Math.random() * 100,
                    liquidTypes.get((int)(Math.random()*liquidTypes.size())),
                    models.get((int)(Math.random()*models.size()))
            ));
        }

        locomotives.forEach(c -> {
            int num = c.getMaxNumOfCars();
            for (int i = 0; i < num - 5; i++) {
                try {
                    c.addCar(cars.get((int) (Math.random() * cars.size())));
                } catch (CarOutOfBoundsExeption | CarsWithElConnectionException | CargoWeightException e) {
                    e.getMessage();
                    // e.printStackTrace();
                }
            }
        });
    }

    public static void startSimulation() {
        makeStations();
        makeLocomotives();
        makeCars();
        locomotives.forEach(Locomotive::startMoving);
    }

    public static String movingInfoForAll() {
        StringBuilder info = new StringBuilder();
        //locomotives.sort((l1, l2) -> Double.compare(l1.completedRouteBetweenStartEnd(), l2.completedRouteBetweenStartEnd()));
        for (Locomotive l: locomotives) {
            info.append("\n").append(l.movingInformation());
        }
        return info.toString();
    }


    public static void printStations() {
        stations.forEach(station -> System.out.println(station.toString()));
    }

    public static void printLocomotives() {
        locomotives.forEach(locomotive -> System.out.println(locomotive.toString()));
    }

    public static void printCars() {
        cars.forEach(car -> System.out.println(car.toString()));
    }
}
