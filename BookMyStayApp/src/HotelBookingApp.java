
        public class HotelBookingApp{
            public static void main(String[] args) {
                Room singleRoom = new SingleRoom(1, 250, 100.0);
                Room doubleRoom = new DoubleRoom(2, 350, 150.0);
                Room suiteRoom = new SuiteRoom(3, 500, 300.0);

                int singleAvailability = 5;
                int doubleAvailability = 3;
                int suiteAvailability = 2;

                System.out.println("Single Room:");
                singleRoom.displayRoomDetails();
                System.out.println("Available: " + singleAvailability);
                System.out.println();

                System.out.println("Double Room:");
                doubleRoom.displayRoomDetails();
                System.out.println("Available: " + doubleAvailability);
                System.out.println();

                System.out.println("Suite Room:");
                suiteRoom.displayRoomDetails();
                System.out.println("Available: " + suiteAvailability);
            }
        }

        abstract class Room {
            protected int numberOfBeds;
            protected int squareFeet;
            protected double pricePerNight;

            public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
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

        class SingleRoom extends Room {
            public SingleRoom(int numberOfBeds, int squareFeet, double pricePerNight) {
                super(numberOfBeds, squareFeet, pricePerNight);
            }
        }

        class DoubleRoom extends Room {
            public DoubleRoom(int numberOfBeds, int squareFeet, double pricePerNight) {
                super(numberOfBeds, squareFeet, pricePerNight);
            }
        }

        class SuiteRoom extends Room {
            public SuiteRoom(int numberOfBeds, int squareFeet, double pricePerNight) {
                super(numberOfBeds, squareFeet, pricePerNight);

            }
        }


