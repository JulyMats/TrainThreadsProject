import java.util.HashMap;
import java.util.Map;

public class TrainStation {

    private String name;
    private int idNumber;
    static int counter;
    private boolean isOccupied;
    private boolean isReturningOccupied;

    private Map<TrainStation, Integer> connections = new HashMap<>();

    public TrainStation(String name) {
        this.name = name;
        counter++;
        this.idNumber = counter;
    }

    public void addBranch(TrainStation trainStation, Integer distance) {
        connections.put(trainStation, distance);
    }

    public Map<TrainStation, Integer> getConnections() {
        return connections;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isReturningOccupied() {
        return isReturningOccupied;
    }

    public void setReturningOccupied(boolean returningOccupied) {
        isReturningOccupied = returningOccupied;
    }

    public String getName() {
        return name;
    }

    public void printConnections() {
        System.out.println("\n" + this.toString());
        this.connections.entrySet().forEach(entry -> {
            System.out.println(" has connection with " + entry.getKey().getName() + " ,distance between stations is " + entry.getValue());
        });
    }

    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public String toString() {
        return " (ID Number " + idNumber +
                ") ---> " + name;
    }
}
