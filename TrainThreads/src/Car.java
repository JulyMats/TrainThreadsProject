public abstract class Car {

    private final String sender;
    private final String safety;
    private double netWeight, grossWeight;
    private int seatsNumber;
    private int idNumber;
    static int counter = 0;

    public Car(String sender, String safety, double netWeight, double grossWeight, int seatsNumber) {
        this.sender = sender;
        this.safety = safety;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.seatsNumber = seatsNumber;
        counter++;
        this.idNumber = counter;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public String toString() {
        return "Car: " +
                "idNumber=" + idNumber +
                ", sender" + sender +
                ", safety='" + safety +
                ", netWeight=" + netWeight +
                ", grossWeight=" + grossWeight +
                ", seatsNumber=" + seatsNumber;
    }
}

class PassengerCar extends Car implements ElectricalConnection{

    private String seatType;
    private int loadPeople;
    private boolean electricalConnection;

    public PassengerCar(String sender, String safety, double netWeight, double grossWeight, int seatsNumber, String seatType, int loadPeople) {
        super(sender, safety, netWeight, grossWeight, seatsNumber);
        this.seatType = seatType;
        this.loadPeople = loadPeople;
        this.electricalConnection = false;
    }

    @Override
    public void makeElectricalConnection() {
        electricalConnection = true;
        System.out.println("Making electrical connection...");
    }

    @Override
    public void removeElectricalConnection() {
        electricalConnection = false;
        System.out.println("Removing electrical connection...");
    }

    @Override
    public String toString() {
        return "PassengerCar{" +
                "seatType='" + seatType + '\'' +
                ", loadPeople=" + loadPeople +
                ", electricalConnection=" + electricalConnection +
                '}';
    }
}

class PostOfficeCar extends Car implements ElectricalConnection{

    private boolean electricalConnection;
    private int loadPeople;

    public PostOfficeCar(String sender, String safety, double netWeight, double grossWeight, int seatsNumber, int loadPeople) {
        super(sender, safety, netWeight, grossWeight, seatsNumber);
        this.loadPeople = loadPeople;
        this.electricalConnection = false;
    }

    @Override
    public void makeElectricalConnection() {
        electricalConnection = true;
        System.out.println("Making electrical connection...");
    }

    @Override
    public void removeElectricalConnection() {
        electricalConnection = false;
        System.out.println("Removing electrical connection...");
    }

    @Override
    public String toString() {
        return "PostOfficeCar{" +
                "electricalConnection=" + electricalConnection +
                ", loadPeople=" + loadPeople +
                '}';
    }
}

class BaggagePostOfficeCar extends Car {

    private double baggageCapacity;
    private String baggageType;

    public BaggagePostOfficeCar(String sender, String safety, double netWeight, double grossWeight, int seatsNumber, double baggageCapacity, String baggageType) {
        super(sender, safety, netWeight, grossWeight, seatsNumber);
        this.baggageCapacity = baggageCapacity;
        this.baggageType = baggageType;
    }

    @Override
    public String toString() {
        return "BaggagePostOfficeCar{" +
                "baggageCapacity=" + baggageCapacity +
                ", baggageType='" + baggageType + '\'' +
                '}';
    }
}

class DiningCar extends Car implements ElectricalConnection{

    private int loadPeople;
    private int numOfTables;
    private String cuisineType;
    private boolean electricalConnection;


    public DiningCar(String sender, String safety, double netWeight, double grossWeight, int seatsNumber, int loadPeople, int numOfTables, String cuisineType) {
        super(sender, safety, netWeight, grossWeight, seatsNumber);
        this.loadPeople = loadPeople;
        this.numOfTables = numOfTables;
        this.cuisineType = cuisineType;
        this.electricalConnection = false;
    }

    @Override
    public void makeElectricalConnection() {
        electricalConnection = true;
        System.out.println("Making electrical connection...");
    }

    @Override
    public void removeElectricalConnection() {
        electricalConnection = false;
        System.out.println("Removing electrical connection...");
    }

    @Override
    public String toString() {
        return "DiningCar{" +
                "loadPeople=" + loadPeople +
                ", numOfTables=" + numOfTables +
                ", cuisineType='" + cuisineType + '\'' +
                ", electricalConnection=" + electricalConnection +
                '}';
    }
}

class FreightCar extends Car {

    private double maxCapacity;
    private double currentCapacity;

    public FreightCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber);
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    @Override
    public String toString() {
        return "FreightCar{" +
                "maxCapacity=" + maxCapacity +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
}

class HeavyFreightCar extends Car {

    private double maxCapacity;
    private double currentCapacity;

    public HeavyFreightCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber);
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    @Override
    public String toString() {
        return "HeavyFreightCar{" +
                "maxCapacity=" + maxCapacity +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
}

class RefrigeratedCar extends FreightCar implements ElectricalConnection {

    private final int temperature;
    private boolean electricalConnection;

    public RefrigeratedCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, int temperature) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.temperature = temperature;
        this.electricalConnection = false;
    }

    @Override
    public void makeElectricalConnection() {
        electricalConnection = true;
        System.out.println("Making electrical connection...");
    }

    @Override
    public void removeElectricalConnection() {
        electricalConnection = false;
        System.out.println("Removing electrical connection...");
    }

    @Override
    public String toString() {
        return "RefrigeratedCar{" +
                "temperature=" + temperature +
                ", electricalConnection=" + electricalConnection +
                '}';
    }
}

class LiquidTankCar extends FreightCar {

    private String liquidType;
    private String modelType;

    public LiquidTankCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, String liquidType, String modelType) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.liquidType = liquidType;
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return "LiquidTankCar{" +
                "liquidType='" + liquidType + '\'' +
                ", modelType='" + modelType + '\'' +
                '}';
    }
}

class GasTankCar extends FreightCar {

    private String gasType;
    private String modelType;

    public GasTankCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, String gasType, String ModelType) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.gasType = gasType;
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return "GasTankCar{" +
                "gasType='" + gasType + '\'' +
                ", modelType='" + modelType + '\'' +
                '}';
    }
}

class ExplosiveCar extends HeavyFreightCar {

    private boolean isRadioactive;
    private double explosiveCapacity;

    public ExplosiveCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, boolean isRadioactive, double explosiveCapacity) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.isRadioactive = isRadioactive;
        this.explosiveCapacity = explosiveCapacity;
    }

    @Override
    public String toString() {
        return "ExplosiveCar{" +
                "isRadioactive=" + isRadioactive +
                ", explosiveCapacity=" + explosiveCapacity +
                '}';
    }
}

class ToxicCar extends HeavyFreightCar {

    private double emission;
    private String fuelType;

    public ToxicCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, double emission, String fuelType) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.emission = emission;
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "ToxicCar{" +
                "emission=" + emission +
                ", fuelType='" + fuelType + '\'' +
                '}';
    }
}

class ToxicLiquidCar extends HeavyFreightCar  {

    private String liquidType;
    private String modelType;

    public ToxicLiquidCar(String consignor, String safety, double netWeight, double grossWeight, int seatsNumber, double maxCapacity, double currentCapacity, String liquidType, String modelType) {
        super(consignor, safety, netWeight, grossWeight, seatsNumber, maxCapacity, currentCapacity);
        this.liquidType = liquidType;
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return "ToxicLiquidCar{" +
                "liquidType='" + liquidType + '\'' +
                ", modelType='" + modelType + '\'' +
                '}';
    }
}

interface ElectricalConnection {

    void makeElectricalConnection();
    void removeElectricalConnection();

}

