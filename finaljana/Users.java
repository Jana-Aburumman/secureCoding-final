
package finaljana;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Users {
    
    public static void main(String[] args) {
       CreateUsers();
    }
    public static void  CreateUsers() {     
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("CheckinAgentUsers.txt"))) {
                String adminPassword ="P@sswordAdmin123";
                if (isStrongPassword(adminPassword)) { 
                    writer.write("admin," + Hash.hash(adminPassword)); 
                    writer.newLine();
                } else {
                    System.out.println("Weak password for admin.");
                }
                writer.newLine();
               String agentPassword = "P@sswordAgent123";
               if (isStrongPassword(agentPassword)) { 
                writer.write("agent1," + Hash.hash(agentPassword)); 
                writer.newLine();
            } else {
                System.out.println("Weak password for agent1.");
            }
            }catch (IOException e) {
                System.out.println("Error creating Check-in Agent user: " + e.getMessage());
            } } 
            public static boolean isStrongPassword(String password) {
                String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                return password.matches(passwordPattern);
            }
        } 