package classesandobjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FlightCrew {
    

    public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);

         System.out.println("Do you want to login? (yes/no)");
         String answer = scan.nextLine();
         if (answer.equalsIgnoreCase("yes")) {
             Login(scan);
         }
        } 
    public static void Login(Scanner scan) {
        System.out.print("Please enter your username: ");
        String username = scan.nextLine();
        System.out.print("Please enter your password: ");
        String password = scan.nextLine();

        String hashedInput = Hash.hash(password);

        try (BufferedReader reader = new BufferedReader(new FileReader("FlightCrewUsers.txt"))) {
            boolean isAuthenticated = false;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2 && credentials[0].equals(username) && credentials[1].equals(hashedInput)) {
                    isAuthenticated = true;
                    break;
                }
            }
            if (isAuthenticated) {
                System.out.println("Login successful! Welcome, " + username + ".");
               System.out.println("Do you want view your assigned flight information (yes/no)");
               String answer = scan.nextLine();
               if (answer.equalsIgnoreCase("yes")) {
                   DisplayFlightInformation(scan);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            } } } 

         catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }   
    } 


        public static void DisplayFlightInformation(Scanner scan){
                System.out.println("Please enter your flight number to display your assigned flight information:");
                String flightnumberToSearch = scan.nextLine();
        
                try (BufferedReader reader = new BufferedReader(new FileReader("FlightCrewInfo.txt"))) {
                    String line;
                    boolean flightfound = false;
        
                    while ((line = reader.readLine()) != null) {
                        String[] details = line.split(",");
                        if (details.length >= 6 && details[3].equals(flightnumberToSearch)) {
                            System.out.println("Flight Number: " + details[3]);
                            System.out.println("Departure Time: " + details[4]);
                            System.out.println("Assigned Gate: " + details[5]);
                            flightfound = true;
                            break;
                        }
                    }
        
                    if (!flightfound) {
                        System.out.println("No flight found with the given flight number.");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading passenger data: " + e.getMessage());
                }
            }
        


        }


    