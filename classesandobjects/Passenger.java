package classesandobjects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Passenger {
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

        String hashedPassword = Hash.hash(password);

        try (BufferedReader reader = new BufferedReader(new FileReader("PassengerUsers.txt"))) {
            boolean isAuthenticated = false;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2 && credentials[0].equals(username) && credentials[1].equals(hashedPassword)) {
                    isAuthenticated = true;
                    break;
                }
            }

            if (isAuthenticated) {
                System.out.println("Login successful! Welcome, " + username + ".");
                displayMenu(scan);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }

    public static void displayMenu(Scanner scan) {
        System.out.println("Please choose a number for what you want to do:");
        System.out.println("1. Display your information");
        System.out.println("2. Update your information");

        int choice = scan.nextInt();
        scan.nextLine(); 

        switch (choice) {
            case 1:
                displayYourInformation(scan);
                break;
            case 2:
                UpdateYourInformation(scan);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void displayYourInformation(Scanner scan) {
        System.out.println("Please enter your passport number to display your information:");
        String passportToSearch = scan.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("PassengersInformation.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 6 && details[1].equals(passportToSearch)) {
                    System.out.println("Passenger Information:");
                    System.out.println("Name: " + details[0]);
                    System.out.println("Passport Number: " + details[1]);
                    System.out.println("Contact Number: " + details[2]);
                    System.out.println("Flight Number: " + details[3]);
                    System.out.println("Departure Time: " + details[4]);
                    System.out.println("Assigned Gate: " + details[5]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No passenger found with the given passport number.");
            }
        } catch (IOException e) {
            System.out.println("Error reading passenger data: " + e.getMessage());
        }
    }

    public static void UpdateYourInformation(Scanner scan) {
        System.out.println("Please enter your passport number to update information:");
        String passportToUpdate = scan.nextLine();
        StringBuilder updatedData = new StringBuilder();
        boolean passengerFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("PassengersInformation.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 6 && details[1].equals(passportToUpdate)) {
                    passengerFound = true;
                    System.out.println("Enter what you want to update. If you want to keep the current value, press Enter:");

                    System.out.print("Name (" + details[0] + "): ");
                    String newName = scan.nextLine();
                    newName = newName.isEmpty() ? details[0] : newName;

                    System.out.print("Passport Number (" + details[1] + "): ");
                    String newPassportNumber = scan.nextLine();
                    newPassportNumber = newPassportNumber.isEmpty() ? details[1] : newPassportNumber;

                    System.out.print("Contact Number (" + details[2] + "): ");
                    String newContact = scan.nextLine();
                    newContact = newContact.isEmpty() ? details[2] : newContact;

                    System.out.print("Flight Number (" + details[3] + "): ");
                    String newFlightNumber = scan.nextLine();
                    newFlightNumber = newFlightNumber.isEmpty() ? details[3] : newFlightNumber;

                    System.out.print("Departure Time (" + details[4] + "): ");
                    String newDepartureTime = scan.nextLine();
                    newDepartureTime = newDepartureTime.isEmpty() ? details[4] : newDepartureTime;

                    System.out.print("Assigned Gate (" + details[5] + "): ");
                    String newAssignedGate = scan.nextLine();
                    newAssignedGate = newAssignedGate.isEmpty() ? details[5] : newAssignedGate;

                    updatedData.append(newName).append(",")
                            .append(newPassportNumber).append(",")
                            .append(newContact).append(",")
                            .append(newFlightNumber).append(",")
                            .append(newDepartureTime).append(",")
                            .append(newAssignedGate).append("\n");
                } else {
                    updatedData.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading passenger data: " + e.getMessage());
            return;
        }

        if (!passengerFound) {
            System.out.println("Passenger not found.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("PassengersInformation.txt"))) {
                writer.write(updatedData.toString());
                System.out.println("Changes saved to file.");
            } catch (IOException e) {
                System.out.println("Error saving updated passenger data: " + e.getMessage());
            }
        }
    }
}
