package classesandobjects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CheckinAgent {
    
                public static void main(String[] args) {
                Scanner scan = new Scanner(System.in);
                try {
                    while (true) {
                        System.out.println("Do you want to login? (yes, no)");
                        String answer = scan.nextLine();
        
                        if (answer.equalsIgnoreCase("yes")) {
                            login(scan);
                        } else if (answer.equalsIgnoreCase("no")) {
                            System.out.println("Exiting program.");
                            break;
                        } else {
                            System.out.println("Please enter 'yes' or 'no'.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                } finally {
                   
                    scan.close();
                }
            }

                    private static void login(Scanner scan) {
                        System.out.print("Please enter your username: ");
                        String username = scan.nextLine();
                        System.out.print("Please enter your password: ");
                        String password = scan.nextLine();
                
                        String hashedInput = Hash.hash(password);
    
                        try (BufferedReader reader = new BufferedReader(new FileReader("CheckinAgentUsers.txt"))) {
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
                                System.out.println("Login successful! Welcome, " + username + ".");
                                System.out.println("What do you want to do?");
                                displayMenu(scan);
                            } else {
                                System.out.println("Invalid username or password. Please try again.");
                            }
                        } catch (IOException e) {
                            System.out.println("Error reading user data: " + e.getMessage());
                        }
                    }
            
                private static void displayMenu(Scanner scan) {
                    System.out.println("1. Register new passenger");
                    System.out.println("2. Update passenger information");
                    System.out.println("3. Display passenger information");
                    System.out.println("4. Register new Flight Crew");



                    System.out.print("Enter your choice: ");
                    int choice = scan.nextInt();
                    scan.nextLine(); 
                
                    switch (choice) {
                        case 1:
                           RegisterNewPassenger(scan);
                            break;
                        case 2:
                            updatePassengerInformation(scan);
                            break;
                        case 3:
                            displayPassengersInformation(scan);
                            break;

                            case 4:
                            RegisterNewFlightCrew(scan);
                            break;
                  
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }




                private static void RegisterNewPassenger(Scanner scan) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("PassengersInformation.txt", true))) {
                        System.out.println(" To registe new passenger please enter the following details:");

                        System.out.print("Name: ");
                        String name = scan.nextLine();

                        System.out.print("passport_number: ");
                        String passport_number = scan.nextLine();

                        System.out.print("contact_number: ");
                        String contact_number = scan.nextLine();

                        System.out.print("Flight Number: ");
                        String flightNumber = scan.nextLine();
                
                        System.out.print("Departure Time: ");
                        String departureTime = scan.nextLine();
                
                        System.out.print("Assigned Gate: ");
                        String assignedGate = scan.nextLine();
                       
            
                        writer.write(name + "," + passport_number + "," + contact_number + "," +
                        flightNumber + "," + departureTime + "," + assignedGate);
                         writer.newLine();

                        System.out.println("the passenger was registered sucssifully");

                    } catch (IOException e) {
                        System.out.println("Error writing customer information: " + e.getMessage());
                    }
                }
                private static void  displayPassengersInformation(Scanner scan) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("PassengersInformation.txt"))) {
                        String line;
                        boolean hasData = false;
                        while ((line = reader.readLine()) != null) {
                        hasData = true;
                        String[] details = line.split(",");
                        if (details.length == 6) {
                            System.out.println("Name: " + details[0]);
                            System.out.println("Passport Number: " + details[1]);
                            System.out.println("Contact Number: " + details[2]);
                            System.out.println("Flight Number: " + details[3]);
                            System.out.println("Departure Time: " + details[4]);
                            System.out.println("Assigned Gate: " + details[5]);
                            System.out.println("----------------"); }  } 

                            if (!hasData) {
                                System.out.println("No passenger data found.");
                            }

                        } catch (IOException e) {
                            System.out.println("Error reading passenger data: " + e.getMessage());
                        } } 



                    private static void updatePassengerInformation(Scanner scan) {

                        System.out.print("Enter the passport number of the passenger you want to update: ");
                        String updateUsingPassport = scan.nextLine();
                    
                        boolean passengerFound = false;
                        StringBuilder updatedData = new StringBuilder();
                    
                        try (BufferedReader reader = new BufferedReader(new FileReader("PassengersInformation.txt"))) {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                String[] details = line.split(",");
                                if (details.length == 6 && details[1].equals(updateUsingPassport)) {
                                    passengerFound = true;
                    
                    
                                    System.out.println("Current Details:");
                                    System.out.println("Name: " + details[0]);
                                    System.out.println("Passport Number: " + details[1]);
                                    System.out.println("Contact Number: " + details[2]);
                                    System.out.println("Flight Number: " + details[3]);
                                    System.out.println("Departure Time: " + details[4]);
                                    System.out.println("Assigned Gate: " + details[5]);

                    
                                    System.out.println("Enter what you want to update and if you want to keep current value press Enter:");
                    
                                    System.out.print("Name (" + details[0] + "): ");
                                    String newName = scan.nextLine();
                                    newName = newName.isEmpty() ? details[0] : newName;

                                    System.out.print("Passport Number (" + details[1] + "): ");
                                    String newPassportNumber= scan.nextLine();
                                    newPassportNumber = newPassportNumber.isEmpty() ? details[1] : newPassportNumber;

                                    System.out.print("Contact Number (" + details[2] + "): ");
                                    String newContact = scan.nextLine();
                                    newContact = newContact.isEmpty() ? details[2] : newContact;


                                    System.out.print("Flight Number (" + details[3] + "): ");
                                    String newFlightNumber= scan.nextLine();
                                    newFlightNumber = newFlightNumber.isEmpty() ? details[3] : newFlightNumber;

                                    
                                    System.out.print("Departure Time (" + details[4] + "): ");
                                    String newDepartureTime= scan.nextLine();
                                    newDepartureTime = newDepartureTime.isEmpty() ? details[4] : newDepartureTime;

                                    
                                    System.out.print("Assigned Gate (" + details[5] + "): ");
                                    String newAssignedGate= scan.nextLine();
                                    newAssignedGate = newAssignedGate.isEmpty() ? details[5] : newAssignedGate;

                                    updatedData.append(newName).append(",")
                                    .append(newPassportNumber).append(",")
                                    .append(newContact).append(",")
                                    .append(newFlightNumber).append(",")
                                    .append(newDepartureTime).append(",")
                                    .append(newAssignedGate).append("\n");

                                    System.out.println("Passenger details updated successfully!");
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
                        private static void  RegisterNewFlightCrew(Scanner scan) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("FlightCrewInfo.txt", true))) {

                                System.out.println(" To registe new flight crew please enter the following details:");

                                System.out.print("Name: ");
                                String name = scan.nextLine();
        
                                System.out.print("passport_number: ");
                                String passport_number = scan.nextLine();
        
                                System.out.print("contact_number: ");
                                String contact_number = scan.nextLine();
                               
                                System.out.print("flight_number: ");
                                String flight_number = scan.nextLine();

                                System.out.print("departure_time: ");
                                String departure_time = scan.nextLine();


                                System.out.print("assigned_gate: ");
                                String assigned_gate = scan.nextLine();

                    
                                writer.write(name + "," + passport_number + "," + contact_number + "," + flight_number + "," + departure_time  + "," + assigned_gate );
                                writer.newLine();
        
                                System.out.println("New Flight Crew added successfully!");
        
                            } catch (IOException e) {
                                System.out.println("Error writing New Flight Crew : " + e.getMessage());
                            }       
                } 
            }