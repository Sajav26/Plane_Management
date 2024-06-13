import java.util.InputMismatchException;
import java.util.Scanner;

public class W2053757_PlaneManagement {
    static int[][] seats = new int[4][14]; // 2D array to represent seats
    static Scanner scanner = new Scanner(System.in);
    static Ticket[] ticketsSold = new Ticket[56]; // Array to store sold tickets

    public static void main(String[] args) {
        System.out.println("Welcome to Plane Management System");

        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("********************");
            System.out.println("       Menu:        ");
            System.out.println("********************");
            System.out.println("\n1. Buy a seat");
            System.out.println("2. Cancel a seat");
            System.out.println("3. Find first available seat");
            System.out.println("4. Show seating plan");
            System.out.println("5. Print tickets info and total sales");
            System.out.println("6. Search for a ticket");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            String choiceInput = scanner.next();

            if (!isInteger(choiceInput)) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(choiceInput);

            switch (choice) {
                case 0:
                    System.out.println("\nExiting program. Thank you!");
                    continueProgram = false;
                    break;
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_ticket_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            if (continueProgram) {
                System.out.print("\nDo you want to continue? (yes for y/ no for n): ");
                String continueChoice = scanner.next();
                continueProgram = continueChoice.equalsIgnoreCase("y");
            }
        }
    }

    public static void buy_seat() {
        char row;
        do {
            System.out.print("Enter row letter (A-D): ");
            row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid Letter. Please enter a row between A to D.");
            }
        } while (row < 'A' || row > 'D');
        int rowIndex = row - 'A';
    
        int seatNumber = 0;
    
        while (true) {
            System.out.print("Enter seat number (1-14): ");
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > 14) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and 14.");
                } else if ((row == 'B' || row == 'C') && (seatNumber == 13 || seatNumber == 14)) {
                    System.out.println("Invalid seat number for rows B and C.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); 
            }
        }
        int seatIndex = seatNumber - 1;
    
        if (rowIndex >= 0 && rowIndex < 4 && seatIndex >= 0 && seatIndex < 14 && seats[rowIndex][seatIndex] == 0) {
            System.out.println("This seat is available and ready to be booked.");
    
            // Validate first name and surname
            String firstName;
            do {
                System.out.print("\nEnter First Name: ");
                firstName = scanner.next();
                if (!firstName.matches("[a-zA-Z]+")) {
                    System.out.println("Invalid first name. Please enter alphabetic characters only.");
                }
            } while (!firstName.matches("[a-zA-Z]+"));
    
            String surName;
            do {
                System.out.print("Enter Sur Name: ");
                surName = scanner.next();
                if (!surName.matches("[a-zA-Z]+")) {
                    System.out.println("Invalid surname. Please enter alphabetic characters only.");
                }
            } while (!surName.matches("[a-zA-Z]+"));
    
            // Validate email
            String emailValid;
            do {
                System.out.print("Enter your email: ");
                emailValid = scanner.next();
                if (!isValidEmail(emailValid)) {
                    System.out.println("Invalid email format. Please enter a valid email address.");
                }
            } while (!isValidEmail(emailValid));
    
            Person person = new Person(firstName, surName, emailValid);
    
            double price;
            if (seatNumber >= 1 && seatNumber <= 5) {
                price = 200.0;
            } else if (seatNumber >= 6 && seatNumber <= 9) {
                price = 150.0;
            } else {
                price = 180.0;
            }
    
            Ticket ticket = new Ticket(String.valueOf(row), seatNumber, price, person);
    
            seats[rowIndex][seatIndex] = 1;
    
            for (int i = 0; i < ticketsSold.length; i++) {
                if (ticketsSold[i] == null) {
                    ticketsSold[i] = ticket;
                    break;
                }
            }
    
            ticket.saveToFile();
        } else {
            System.out.println("Invalid seat or seat already booked.");
        }
    }
    
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }
    

    public static void cancel_seat() {
        char row;
        do {
            System.out.print("Enter row letter (A-D): ");
            row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid Letter. Please enter a row between A to D.");
            }
        } while (row < 'A' || row > 'D');
        int rowIndex = row - 'A';

        int seatNumber = 0;
        while (true) {
            System.out.print("Enter seat number (1-14): ");
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > 14) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and 14.");
                } else if ((row == 'B' || row == 'C') && (seatNumber == 13 || seatNumber == 14)) {
                    System.out.println("Invalid seat number for rows B and C.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        int seatIndex = seatNumber - 1;

        if (rowIndex >= 0 && rowIndex < 4 && seatIndex >= 0 && seatIndex < 14 && seats[rowIndex][seatIndex] == 1) {
            seats[rowIndex][seatIndex] = 0;

            for (int i = 0; i < ticketsSold.length; i++) {
                if (ticketsSold[i] != null && ticketsSold[i].getRow().equals(String.valueOf(row))
                        && ticketsSold[i].getSeat() == seatNumber) {
                    ticketsSold[i] = null;
                    break;
                }
            }

            System.out.println("Seat canceled successfully.");
        } else {
            System.out.println("Ticket not found or seat not booked.");
        }
    }

    public static void find_first_available() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    char row = (char) ('A' + i);
                    System.out.println("First available seat found at Row " + row + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }

    public static void show_seating_plan() {
        System.out.println("  1  2  3  4  5  6  7  8  9 10 11 12 13 14");

        for (int i = 0; i < seats.length; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + " ");

            for (int j = 0; j < seats[i].length; j++) {
                if ((row == 'B' || row == 'C') && (j == 12 || j == 13)) {
                    System.out.print("   ");
                } else {
                    if (seats[i][j] == 0) {
                        System.out.print("O  ");
                    } else {
                        System.out.print("X  ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void print_ticket_info() {
        int ticketsSoldCount = 0;
        double totalAmount = 0.0;
        System.out.println("\nTickets sold:");

        for (Ticket ticket : ticketsSold) {
            if (ticket != null) {
                ticket.printTicketInfo();
                totalAmount += ticket.getPrice();
                ticketsSoldCount++;
                System.out.println();
            }
        }

        System.out.println("Total tickets sold: " + ticketsSoldCount);
        System.out.println("Total Amount: £" + totalAmount);
    }

    public static void search_ticket() {
        char row;
        do {
            System.out.print("Enter row letter (A-D): ");
            row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid Letter. Please enter a row letter between A to D.");
            }
        } while (row < 'A' || row > 'D');
        int rowIndex = row - 'A';

        int seatNumber = 0;
        while (true) {
            System.out.print("Enter seat number (1-14): ");
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > 14) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and 14.");
                } else if ((row == 'B' || row == 'C') && (seatNumber == 13 || seatNumber == 14)) {
                    System.out.println("Invalid seat number for rows B and C.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        int seatIndex = seatNumber - 1;

        boolean found = false;

        if (rowIndex >= 0 && rowIndex < 4 && seatIndex >= 0 && seatIndex < 14) {
            for (Ticket ticket : ticketsSold) {
                if (ticket != null && ticket.getRow().equals(String.valueOf(row)) && ticket.getSeat() == seatNumber) {
                    System.out.println("Ticket found and seat is booked:");
                    ticket.printTicketInfo();
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("This seat is available.");
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
