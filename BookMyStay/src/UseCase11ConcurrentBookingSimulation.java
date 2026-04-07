import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class UseCase11ConcurrentBookingSimulation {

    static class BookingRequest {
        private String guestName;
        private String roomType;

        public BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

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

        public synchronized void showInventory() {
            System.out.println("Current Inventory: " + rooms);
        }
    }

    static class BookingQueue {
        private Queue<BookingRequest> queue = new LinkedList<>();

        public synchronized void addRequest(BookingRequest request) {
            queue.add(request);
            notifyAll();
        }

        public synchronized BookingRequest getRequest() {
            while (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return queue.poll();
        }
    }

    static class BookingProcessor extends Thread {
        private BookingQueue queue;
        private RoomInventory inventory;

        public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
            this.queue = queue;
            this.inventory = inventory;
        }

        public void run() {
            while (true) {
                BookingRequest request = queue.getRequest();
                boolean success = inventory.allocateRoom(request.getRoomType());

                if (success) {
                    System.out.println("Booking confirmed for " +
                            request.getGuestName() + " (" + request.getRoomType() + ")");
                } else {
                    System.out.println("Booking failed for " +
                            request.getGuestName() + " (" + request.getRoomType() + ")");
                }
            }
        }
    }


    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        BookingProcessor p1 = new BookingProcessor(queue, inventory);
        BookingProcessor p2 = new BookingProcessor(queue, inventory);

        p1.start();
        p2.start();

        queue.addRequest(new BookingRequest("Abhi", "Single"));
        queue.addRequest(new BookingRequest("Subha", "Double"));
        queue.addRequest(new BookingRequest("Vanmathi", "Suite"));
        queue.addRequest(new BookingRequest("Rahul", "Single"));
        queue.addRequest(new BookingRequest("Anita", "Suite"));
    }
}