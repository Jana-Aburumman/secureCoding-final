package finaljana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import finaljana.Hash;
import finaljana.secure;

public class FlightCrew {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Do you want to login? (yes/no)");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                Login(scan);
                break;
            } else if (answer.equalsIgnoreCase("no")) {
                System.out.println("Exiting the system.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        scan.close();
    }

    public static void Login(Scanner scan) {
        System.out.print("Please enter your username: ");
        String username = scan.nextLine();
        if (!isValidInput(username, 5, 15)) { 
            System.out.println("Invalid username length.");
            return;
        }
        if (secure.isAccountLocked()) { 
            System.out.println("Account is locked. Try again later.");
            return;
        }

        System.out.print("Please enter your password: ");
        String password = scan.nextLine();
        if (!isValidInput(password, 8, 30)) { 
            System.out.println("Invalid password length.");
            return;
        }


        String hashedInput = Hash.hash(password);

        try (BufferedReader reader = new BufferedReader(new FileReader("FlightCrewInfo.txt"))) {
            boolean isAuthenticated = false;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(hashedInput)) {
                    isAuthenticated = true;
                    break;
                }
            }
            if (isAuthenticated) {
                secure.resetLoginAttempts();
                System.out.println("Login successful! Welcome, " + username + ".");
                MyLogger.writeToLog("Login successful for username: " + username);
               System.out.println("Do you want view your assigned flight information (yes/no)");
               String answer = scan.nextLine();
               if (answer.equalsIgnoreCase("yes")) {
                   DisplayFlightInformation(scan);
                } else if (answer.equalsIgnoreCase("no")) {
                    System.out.println("Returning to the main menu.");
                } else {
                    secure.registerFailedAttempt();
                    MyLogger.writeToLog("Failed login attempt for username: " + username);
                    System.out.println("Invalid input. Returning to the main menu.");
                }
            } } 
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
                        if (details.length == 7 && details[4].equals(flightnumberToSearch)) {
                            System.out.println("Flight Number: " + details[4]);
                            System.out.println("Departure Time: " + details[5]);
                            System.out.println("Assigned Gate: " + details[6]);
                            flightfound = true;
                            MyLogger.writeToLog("Displayed flight information for flight number: " + flightnumberToSearch);
                            break;
                        }
                    }
        
                    if (!flightfound) {
                        MyLogger.writeToLog("No flight found for flight number: " + flightnumberToSearch);
                        System.out.println("No flight found with the given flight number.");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading passenger data: " + e.getMessage());
                }
            }
            public static boolean isValidInput(String input, int minLength, int maxLength) {
                return input != null && input.length() >= minLength && input.length() <= maxLength;
            }


        }


    