import java.util.HashMap;
import java.util.Scanner;

public class UseCase6HotelBookingApp {

    public static void main(String[] args) {

        // Room inventory
        HashMap<String, Integer> roomInventory = new HashMap<>();
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Deluxe Room", 2);

        // Room allocation tracker
        HashMap<String, Integer> roomNumbers = new HashMap<>();
        roomNumbers.put("Single Room", 101);
        roomNumbers.put("Double Room", 201);
        roomNumbers.put("Deluxe Room", 301);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hotel Booking Management System");

        System.out.print("Enter room type to book: ");
        String roomType = scanner.nextLine();

        if (roomInventory.containsKey(roomType)) {
            int available = roomInventory.get(roomType);

            if (available > 0) {

                // Allocate room number
                int allocatedRoom = roomNumbers.get(roomType);

                // Update inventory
                roomInventory.put(roomType, available - 1);

                // Update next room number
                roomNumbers.put(roomType, allocatedRoom + 1);

                System.out.println("\nBooking Confirmed!");
                System.out.println("Room Type: " + roomType);
                System.out.println("Allocated Room Number: " + allocatedRoom);
                System.out.println("Remaining Rooms: " + roomInventory.get(roomType));

            } else {
                System.out.println("Sorry, " + roomType + " is fully booked.");
            }
        } else {
            System.out.println("Invalid room type.");
        }

        scanner.close();
    }
}





