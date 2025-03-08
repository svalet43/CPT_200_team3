/* This code was created by Clayton Barkalge on 3/2/2025
   The purpose behind this code is to make a simple web page
   design to allow users to input the food they ate, the 
   calories per serving, and how many servings they ate.
   This the simple layout, due to not having a design chosen.
*/
// This code was updated by Clayton Barklage on 3/8/2025
// added explanation comments
import java.util.Scanner;
public class Health_Metric_Logging {
    class Main {
        public static void main(String[] args) {
          Scanner healthLogging = new Scanner(System.in);
      
          System.out.println("Enter name of the food, cals/serving,and servings you ate:");
      
          String food = healthLogging.nextLine();
          int cps = healthLogging.nextInt(); // cps = Calories Per Serving
          int servings = healthLogging.nextInt();

      
          /*This section will be replaced with code to transport
            the data to the database when we have it completed
          */
          System.out.println(food);
          System.out.println(cps);
          System.out.println(servings);
        }
    }
}
