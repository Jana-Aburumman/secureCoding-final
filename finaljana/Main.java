package finaljana;
import java.util.Scanner;


public class Main {
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to SkyPort!");
        System.out.println("Please select your role:");
        System.out.println("1. Check-in Agent");
        System.out.println("2. Passenger");
        System.out.println("3. Flight Crew");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        switch (roleChoice) {
            case 1:
                System.out.println("You selected: Check-in Agent");
                CheckinAgent.main(new String[]{}); 
                break;
            case 2:
                System.out.println("You selected: Passenger");
                Passenger.main(new String[]{}); 
                break;
            case 3:
                System.out.println("You selected: Flight Crew");
                FlightCrew.main(new String[]{}); 
                break;
            default:
                System.out.println("Invalid choice. Exiting the program.");
        }

        scanner.close();
    }
}  