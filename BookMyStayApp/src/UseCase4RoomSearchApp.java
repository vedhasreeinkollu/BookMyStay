import java.util.HashMap;
import java.util.Map;

abstract class Room1 {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room1(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq ft");
        System.out.println("Price per night: $" + pricePerNight);
    }
}

class SingleRoom1 extends Room1 {
    public SingleRoom1(int numberOfBeds, int squareFeet, double pricePerNight) {
        super(numberOfBeds, squareFeet, pricePerNight);
    }
}

class DoubleRoom1 extends Room1 {
    public DoubleRoom1(int numberOfBeds, int squareFeet, double pricePerNight) {
        super(numberOfBeds, squareFeet, pricePerNight);
    }
}

class SuiteRoom1 extends Room1 {
    public SuiteRoom1(int numberOfBeds, int squareFeet, double pricePerNight) {
        super(numberOfBeds, squareFeet, pricePerNight);
    }
}

class RoomInventory1 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory1() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 0);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Map<String, Room1> rooms) {
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println(entry.getKey() + " Room Available: " + entry.getValue());
                rooms.get(entry.getKey()).displayRoomDetails();
                System.out.println();
            }
        }
    }
}

public class UseCase4RoomSearchApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Map<String, Room1> rooms = new HashMap<>();
        rooms.put("Single", new SingleRoom1(1, 250, 100.0));
        rooms.put("Double", new DoubleRoom1(2, 350, 150.0));
        rooms.put("Suite", new SuiteRoom1(3, 500, 300.0));

        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);
    }
}


