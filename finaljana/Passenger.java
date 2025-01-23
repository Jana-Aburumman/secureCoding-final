package finaljana;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import finaljana.Hash;
import finaljana.secure;

public class Passenger {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Do you want to login? (yes/no)");
        String answer = scan.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            Login(scan);
        }else {
            System.out.println("Exiting the system.");
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
        String hashedPassword = Hash.hash(password);

        try (BufferedReader reader = new BufferedReader(new FileReader("PassengersInformation.txt"))) {
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
                secure.resetLoginAttempts();
                MyLogger.writeToLog("Passenger login successful: " + username);
                System.out.println("Login successful! Welcome, " + username + ".");
                displayMenu(scan);
            } else {
                secure.registerFailedAttempt();
                MyLogger.writeToLog("Passenger login failed: " + username);
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (IOException e) {
            MyLogger.writeToLog("Error reading Passenger data", e);
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }
    public static boolean isValidInput(String input, int minLength, int maxLength) {
        return input != null && input.length() >= minLength && input.length() <= maxLength;
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
                if (details.length >= 7 && details[2].equals(passportToSearch)) {
                    System.out.println("Passenger Information:");
                    System.out.println("User name: " + details[0]);
                    System.out.println("password: " + details[1]);
                    System.out.println("Passport Number: " + details[2]);
                    System.out.println("Contact Number: " + details[3]);
                    System.out.println("Flight Number: " + details[4]);
                    System.out.println("Departure Time: " + details[5]);
                    System.out.println("Assigned Gate: " + details[6]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                MyLogger.writeToLog("No passenger found with the given passport number.");
                System.out.println("No passenger found with the given passport number.");
            }
        } catch (IOException e) {
            MyLogger.writeToLog("Error reading passenger data: " + e);
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
                if (details.length == 7 && details[2].equals(passportToUpdate)) {
                    passengerFound = true;
                    System.out.println("Enter what you want to update. If you want to keep the current value, press Enter:");

                    System.out.print("User name (" + details[0] + "): ");
                    String newUserName = scan.nextLine();
                    newUserName = newUserName.isEmpty() ? details[0] : newUserName;

                    System.out.print("Password (" + details[1] + "): ");
                    String newpassword= scan.nextLine();
                    newpassword = newpassword.isEmpty() ? details[1] : newpassword;

                    System.out.print("Passport Number (" + details[2] + "): ");
                    String newPassportNumber = scan.nextLine();
                    newPassportNumber = newPassportNumber.isEmpty() ? details[2] : newPassportNumber;

                    System.out.print("Contact Number (" + details[3] + "): ");
                    String newContact = scan.nextLine();
                    newContact = newContact.isEmpty() ? details[3] : newContact;

                    System.out.print("Flight Number (" + details[4] + "): ");
                    String newFlightNumber = scan.nextLine();
                    newFlightNumber = newFlightNumber.isEmpty() ? details[4] : newFlightNumber;

                    System.out.print("Departure Time (" + details[5] + "): ");
                    String newDepartureTime = scan.nextLine();
                    newDepartureTime = newDepartureTime.isEmpty() ? details[5] : newDepartureTime;

                    System.out.print("Assigned Gate (" + details[6] + "): ");
                    String newAssignedGate = scan.nextLine();
                    newAssignedGate = newAssignedGate.isEmpty() ? details[6] : newAssignedGate;

                    updatedData.append(newUserName).append(",")
                    .append(newpassword).append(",")
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
            MyLogger.writeToLog("Error updating Passenger data", e);
            System.out.println("Error reading passenger data: " + e.getMessage());
            return;
        }

        if (!passengerFound) {
            MyLogger.writeToLog("Passenger not found for update.");
            System.out.println("Passenger not found.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("PassengersInformation.txt"))) {
                writer.write(updatedData.toString());
                System.out.println("Changes saved to file.");
            } catch (IOException e) {
                MyLogger.writeToLog("Error saving updated Passenger data", e);
                System.out.println("Error saving updated passenger data: " + e.getMessage());
            }
        }
    }
}
