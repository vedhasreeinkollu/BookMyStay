import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UseCase10BookingCancellationInventoryRollback {

    static class CancellationService {
        private Stack<String> releasedRoomIds;
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (reservationRoomTypeMap.containsKey(reservationId)) {
                String roomType = reservationRoomTypeMap.remove(reservationId);
                releasedRoomIds.push(reservationId);
                inventory.addRoom(roomType);
                System.out.println("Booking cancelled: " + reservationId);
            } else {
                System.out.println("Reservation not found: " + reservationId);
            }
        }

        public void showRollbackHistory() {
            System.out.println("Rollback History:");
            while (!releasedRoomIds.isEmpty()) {
                System.out.println("Cancelled Reservation ID: " + releasedRoomIds.pop());
            }
        }
    }

    static class RoomInventory {
        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 2);
            rooms.put("Double", 2);
            rooms.put("Suite", 1);
        }

        public void addRoom(String roomType) {
            rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
        }
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Register bookings
        service.registerBooking("R101", "Single");
        service.registerBooking("R102", "Double");

        // Cancel bookings
        service.cancelBooking("R101", inventory);
        service.cancelBooking("R999", inventory); // not found

        // Show rollback history
        service.showRollbackHistory();
    }
}