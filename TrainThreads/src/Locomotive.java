import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Locomotive {

    private String name;
    private TrainStation homeStation, startStation, endStation;
    private int idNumber;
    static int counter = 0;
    private int maxNumOfCars, maxNumOfCarsWithElConnect;
    private double maxCargoWeight;
    private double startSpeed;
    private double speed;
    private ArrayList<Car> cars;
    private List<TrainStation> route;
    private Thread moveThread;
    private Thread speedThread;
    private Thread saveThread;
    private boolean isMoving;
    private TrainStation current;
    private boolean isReturning;
    private double completedDistance;

    public Locomotive(String name, TrainStation homeStation, TrainStation startStation, TrainStation endStation, int maxNumOfCars, double maxCargoWeight, int maxNumOfCarsWithElConnect, double startSpeed) {
        this.name = name;
        this.homeStation = homeStation;
        this.startStation = startStation;
        this.endStation = endStation;
        counter++;
        this.idNumber = counter;
        this.maxNumOfCars = maxNumOfCars;
        this.maxCargoWeight = maxCargoWeight;
        this.maxNumOfCarsWithElConnect = maxNumOfCarsWithElConnect;
        this.startSpeed = startSpeed;
        this.speed = startSpeed;
        this.cars = new ArrayList<>();
        this.isMoving = false;
        this.isReturning = false;
        this.current = startStation;
        this.completedDistance = 0;
    }

    public void setRoute(List<TrainStation> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public int getMaxNumOfCars() {
        return maxNumOfCars;
    }

    public void addCar(Car car) throws CargoWeightException, CarsWithElConnectionException, CarOutOfBoundsExeption{
        if(cars.size() < maxNumOfCars) {
            int carsWithElConnectionCounter = (int) (cars.stream()
                    .filter(c -> c instanceof ElectricalConnection)
                    .count()
            );
            if((carsWithElConnectionCounter + 1) < maxNumOfCarsWithElConnect) {
                double weightCounter = cars.stream()
                        .mapToDouble(c -> c.getNetWeight() + c.getGrossWeight())
                        .sum();
                if ((weightCounter + (car.getGrossWeight() + car.getNetWeight())) < maxCargoWeight) {
                    cars.add(car);
                } else {
                    throw new CargoWeightException(this);
                }
            }
            else {
                throw new CarsWithElConnectionException(this);
            }
        } else {
            throw new CarOutOfBoundsExeption(this);
        }
    }

    public void deleteCar(Car car) throws Exception{
        if(!cars.isEmpty()) {
            cars.remove(car);
        } else {
            throw new Exception();
        }
    }

    public List<TrainStation> foundRoute(TrainStation from, TrainStation to) {
        Set<TrainStation> visited = new HashSet<>();
        Stack<TrainStation> stack = new Stack<>();
        Map<TrainStation, TrainStation> parentMap = new HashMap<>();

        visited.add(from);
        stack.push(from);
        parentMap.put(from, null);

        while(!stack.isEmpty()) {
            TrainStation current = stack.pop();

            if (current == to) {
                return getPath(to, parentMap);
            }

            for(Map.Entry<TrainStation, Integer> entry : current.getConnections().entrySet()) {
                TrainStation neighbour = entry.getKey();
                if(!(visited.contains(neighbour))) {
                    visited.add(neighbour);
                    stack.push(neighbour);
                    parentMap.put(neighbour, current);
                }
            }
        }
        return null;
    }

    private List<TrainStation> getPath(TrainStation to, Map<TrainStation, TrainStation> parentMap) {
        List<TrainStation> path = new ArrayList<>();
        path.add(to);
        TrainStation parent = parentMap.get(to);
        while (parent != null) {
            path.add(parent);
            parent = parentMap.get(parent);
        }
        Collections.reverse(path);
        return path;
    }

    public void move() throws InterruptedException{
        route = foundRoute(startStation, endStation);
        //printRoute();
        while(!current.equals(endStation)) {
            moveForward();
            //System.out.println("Train " + idNumber + " is waiting for 2 seconds on the station " + current.getName() + ". Current speed is " + speed);
            Thread.sleep(2000);
        }

        //System.out.println("Train " + idNumber + " is on the end station " + current.getName() + ". It will wait for 30 seconds.");
        Thread.sleep(30000);
        makeReturnRoad();
       // System.out.println("=============================Train " + idNumber + " is starting return route======================================");
    }

    public void makeReturnRoad() {
        homeStation = endStation;
        endStation = startStation;
        startStation = homeStation;
        isReturning = !isReturning;

        if (isReturning) {
            startStation.setOccupied(false);
            startStation.setReturningOccupied(true);
        } else {
            startStation.setReturningOccupied(false);
            startStation.setOccupied(true);
        }
    }

    public void startMoving() {
        this.moveThread = new Thread(() -> {
            while(true) {
                try {
                    move();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.speedThread = new Thread(() -> {
            while (true) {
                try {
                        speedChange();
                } catch (RailRoadHazard e) {
                    e.printStackTrace();
                }
            }
        });
        this.saveThread = new Thread(() -> {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new FileWriter("AppState", true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    if (printWriter != null) {
                        printWriter.println(RouteElements.movingInfoForAll());
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } /*finally {
                    try {
                        printWriter = new PrintWriter(new FileWriter("AppState", false));
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });
        moveThread.start();
        speedThread.start();
        saveThread.start();
    }

    public void moveForward() {
        if(route.indexOf(current) < route.size() - 1) {
            TrainStation next = route.get(route.indexOf(current) + 1);

                synchronized (next) {
                    while (!isReturning ? next.isOccupied() : next.isReturningOccupied()) {
                        try {
                            isMoving = false;
                            //System.out.println("Oooops.... Train " + idNumber + " is waiting ... waiting... waiting... on the station " + current.getName());
                            next.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!isReturning)
                    next.setOccupied(true);
                else
                    next.setReturningOccupied(true);

            isMoving = true;
            synchronized (speedThread) {
                speedThread.notify();
            }

            synchronized (current) {
                if (!isReturning)
                    current.setOccupied(false);
                else
                    current.setReturningOccupied(false);
                current.notify();
            }

            double distanceToMove = distanceBetweenTwoStations();
            while (distanceToMove > 0) {
                distanceToMove -= distance();
                completedDistance += distance();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;

            while(speedThread.getState() != Thread.State.WAITING)
                ;

            current = next;
        }
    }

    public void speedChange() throws RailRoadHazard {
        while(isMoving) {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                speed += speed * 0.03;
            }
            else {
                speed -= speed * 0.03;
            }
            //System.out.println("Train " + idNumber + " is moving to the station: " + route.get(route.indexOf(current) + 1).getName() + ". My speed is " + speed + " km/h.");
            if(speed > 200) {
                throw new RailRoadHazard(this);
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                //e.getMessage();
                e.printStackTrace();
            }
        }
        speed = 0;
        synchronized (speedThread) {
            while (!isMoving) {
                try {
                    speedThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        speed = startSpeed;
    }

    public double distanceBetweenTwoStations() {
        return current.getConnections().get(route.get(route.indexOf(current) + 1));
    }

    public double distance() {
        return speed * 0.15;
        //return speed / 3600;
    }

    public double completedRouteBetweenTwoStations() {
        double distanceFromStartToCurrent = route.stream()
                .filter(trainStation -> route.indexOf(trainStation) < route.indexOf(current))
                .mapToDouble(trainStation -> trainStation.getConnections().get(route.get(route.indexOf(trainStation) + 1)))
                .sum();
        double completedRouteBetweenTwoStations = completedDistance -distanceFromStartToCurrent;
        return (completedRouteBetweenTwoStations/distanceBetweenTwoStations()) * 100;
    }

    public double completedRouteBetweenStartEnd() {
        return (completedDistance / distanceBetweenStartEnd()) * 100;
    }

    public double distanceBetweenStartEnd() {
        return route.stream()
                .filter(trainStation -> route.indexOf(trainStation) + 1 < route.size())
                .mapToDouble((c) -> c.getConnections().get(route.get(route.indexOf(c)+ 1)))
                .sum();
    }



    public void printRoute() {
        String routePrint = route.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", " -> ");
        System.out.println("Locomotive " + idNumber + " is starting to move. Now it is on the station " + current.getName() + ". Current speed is " + speed + " km/h. His route is " + routePrint);
    }

    public String movingInformation() {
        return "Locomotive:" +
                "ID Number " + idNumber +
                "name " + name +
                "current speed = " + speed +
                "isReturning = " + isReturning +
                "current Station = " + current +
                "consist of cars -> " + carsToString();
    }

    public String carsToString() {
        StringBuilder carsInfo = new StringBuilder();
        //cars.sort((l1, l2) -> Double.compare(l1.getGrossWeight()+l1.getNetWeight(),l2.getGrossWeight()+l2.getNetWeight()));
        for (Car car : cars) {
            carsInfo.append(car.toString()).append(", ");
        }
        return carsInfo.toString();
    }

    @Override
    public String toString() {
        return "Locomotive:" +
                " ID Number " + idNumber +
                ", name " + name +
                ", homeStation is" + homeStation +
                ", startStation is" + startStation +
                ", endStation is" + endStation +
                ", maxNumOfCars = " + maxNumOfCars +
                ", maxNumOfCarsWithElConnect = " + maxNumOfCarsWithElConnect +
                ", maxCargoWeight = " + maxCargoWeight +
                ", startSpeed = " + startSpeed +
                ", current speed = " + speed +
                ", isReturning = " + isReturning +
                ", current Station = " + current +
                ", completed distance = " + completedDistance;

    }
}




  