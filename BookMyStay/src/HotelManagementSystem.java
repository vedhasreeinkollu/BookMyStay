import java.io.*;
import java.util.*;

/**
 * COMPLETE HOTEL SYSTEM
 */
public class HotelManagementSystem {

    // ================= ROOM INVENTORY =================
    static class RoomInventory {
        private Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 2);
            rooms.put("Double", 2);
            rooms.put("Suite", 1);
        }

        public synchronized boolean allocateRoom(String roomType) {
            int count = rooms.getOrDefault(roomType, 0);
            if (count > 0) {
                rooms.put(roomType, count - 1);
                return true;
            }
            return false;
        }

        public synchronized void addRoom(String roomType) {
            rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
        }

        public Map<String, Integer> getRoomAvailability() {
            return rooms;
        }

        public void updateAvailability(String roomType, int count) {
            rooms.put(roomType, count);
        }

        public void showInventory() {
            System.out.println("Inventory: " + rooms);
        }
    }

    // ================= FILE PERSISTENCE =================
    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry :
                        inventory.getRoomAvailability().entrySet()) {

                    writer.write(entry.getKey() + "-" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory: " + e.getMessage());
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No file found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("-");

                    if (parts.length != 2) continue;

                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    inventory.updateAvailability(roomType, count);
                }

                System.out.println("Inventory loaded successfully.");

            } catch (Exception e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    // ================= BOOKING SERVICE =================
    static class BookingService {
        private Map<String, String> bookings = new HashMap<>();

        public boolean bookRoom(String reservationId, String roomType, RoomInventory inventory) {
            if (inventory.allocateRoom(roomType)) {
                bookings.put(reservationId, roomType);
                System.out.println("Booking successful: " + reservationId);
                return true;
            } else {
                System.out.println("Booking failed (No rooms): " + reservationId);
                return false;
            }
        }

        public Map<String, String> getBookings() {
            return bookings;
        }
    }

    // ================= CANCELLATION SERVICE =================
    static class CancellationService {
        private Stack<String> cancelled = new Stack<>();
        private Map<String, String> bookings;

        public CancellationService(Map<String, String> bookings) {
            this.bookings = bookings;
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (bookings.containsKey(reservationId)) {
                String roomType = bookings.remove(reservationId);
                inventory.addRoom(roomType);
                cancelled.push(reservationId);
                System.out.println("Cancelled: " + reservationId);
            } else {
                System.out.println("Not found: " + reservationId);
            }
        }

        public void showHistory() {
            System.out.println("Cancellation History:");
            for (String id : cancelled) {
                System.out.println(id);
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService fileService = new FilePersistenceService();
        BookingService bookingService = new BookingService();

        String filePath = "inventory.txt";

        // Load inventory
        fileService.loadInventory(inventory, filePath);

        inventory.showInventory();

        // Book rooms
        bookingService.bookRoom("R101", "Single", inventory);
        bookingService.bookRoom("R102", "Double", inventory);
        bookingService.bookRoom("R103", "Suite", inventory);
        bookingService.bookRoom("R104", "Suite", inventory); // should fail

        inventory.showInventory();

        // Cancel booking
        CancellationService cancelService =
                new CancellationService(bookingService.getBookings());

        cancelService.cancelBooking("R101", inventory);
        cancelService.cancelBooking("R999", inventory); // not found

        inventory.showInventory();

        cancelService.showHistory();

        // Save inventory
        fileService.saveInventory(inventory, filePath);
    }
}