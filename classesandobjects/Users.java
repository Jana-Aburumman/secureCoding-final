
package classesandobjects;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class Users {
    
    public static void main(String[] args) {
       CreateUsers();
    }



    
    public static void  CreateUsers() {
       try{

     
          
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("CheckinAgentUsers.txt"))) {
                writer.write("admin," + Hash.hash("admin12345")); 
                writer.newLine();
                writer.write("agent1," + Hash.hash("pass12345")); 
                writer.newLine();
            }


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("PassengerUsers.txt"))) {
                writer.write("jana," + Hash.hash("janajana12345"));
                writer.newLine();
                writer.write("halazayyadeen," + Hash.hash("halush123"));
                writer.newLine();
            }


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("FlightCrewUsers.txt"))) {
                writer.write("crew1," + Hash.hash("crew12345"));
                writer.newLine();
                writer.write("pilot," + Hash.hash("pilot12345"));
                writer.newLine();
            }

            System.out.println("users were created successfully!");
        } catch (IOException e) {
            System.out.println("Error writing user files: " + e.getMessage());
        }
    }

} 