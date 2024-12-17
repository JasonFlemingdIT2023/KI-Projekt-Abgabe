import java.util.ArrayList;
import java.util.Scanner;

public class Main{
  public static void main(String[] args){

    Scanner scanner = new Scanner(System.in);
    Salesman salesman = new Salesman(); //add cities to salesman object
    boolean continueAnnealing = true;
    //cities
    int numCities = InputValidation.validateInt(scanner, "Enter the number of cities (positive integer): ", 
    "\\d+", "Invalid input. Please enter a positive integer.");

    //leave the choice for user to specify city by himself/herself
    String userChoice = InputValidation.validateString(scanner, "Do you want to specify coordinates for each city? (yes/no): ",
    "(?i)yes|no", "Invalid input. Please enter yes or no.");
      

    //get the information if users wants to customize city
    for (int i = 0; i < numCities; i++) {
       if (userChoice.equalsIgnoreCase("yes")) {
       System.out.println("Enter details for city " + (i + 1) + ":");
       int x = InputValidation.validateInt(scanner, "Enter x coordinate (integer): ", 
       "-?\\d+", "Invalid input. Please enter an integer for x coordinate.");

       int y = InputValidation.validateInt(scanner, "Enter y coordinate (integer): ", 
       "-?\\d+", "Invalid input. Please enter an integer for y coordinate.");

       String name = InputValidation.validateString(scanner, "Enter city name (letters/numbers, max 50 chars): ", 
       "[a-zA-Z0-9_- ]{1,50}", 
       "Invalid input. Please enter a valid city name (letters, numbers, underscores, dashes, max 50 chars).");

  
       salesman.addCities(new City(x, y, name)); //specified city
       } else {
       salesman.addCities(new City()); //randomized city
       }  
    }

    //set the firstRoute as best route for salesman object
    ArrayList<City> firstRoute = salesman.getFirstRoute();
    salesman.setBestRoute(firstRoute);

    //repeat annealing for a session
    while (continueAnnealing) {

      //cooling strategy
      String coolingStrategy = InputValidation.validateString(scanner, "Choose a cooling strategy (linear/exponential): ",
      "(?i)linear|exponential", 
      "Invalid strategy. Please choose a valid one.");

      //temperature
      double firstTemperature = InputValidation.validateDouble(scanner, "Enter the first temperature (positive number): ",
      "^[+]?\\d*\\.?\\d+$", "Invalid input. Please enter a positive number.");

      //coolingrate
      double coolingRate;
      if (coolingStrategy.equalsIgnoreCase("linear")) {
        coolingRate = 0.01; // fixed value for linear strategy
        System.out.println("Linear strategy selected. Cooling rate is set to a fixed value of " + coolingRate);
      } else {
        coolingRate = InputValidation.validateDouble(scanner, "Enter the cooling rate (0 < rate < 1): ",
        "^(0(\\.\\d+)?|1(\\.0+)?)$", "Invalid input. Please enter a rate between 0 and 1.");
      }

      
      Annealing annealing = new Annealing(firstTemperature, coolingRate, salesman);
      System.out.println("Starting simulated annealing...");
      System.out.printf("First distance: %.2f\n", annealing.getBestDistance());
      annealing.runAnnealing(salesman, coolingStrategy);

      //Print result
      System.out.printf("Final best distance: %.2f\n", annealing.getBestDistance());
      System.out.println("Best route found:");
      for (City city : annealing.getBestRoute()) {
          System.out.println(city);
      }
      RouteVisualizer.visualizeRoute(annealing.getBestRoute());

      //Output local minima
      System.out.println("\nLocal minima found during the process:");
      ArrayList<String> localMinimaLog = annealing.getLocalMinimaLog();
      if (localMinimaLog.isEmpty()) {
          System.out.println("No local minima were found.");
      } else {
          for (String logEntry : localMinimaLog) {
              System.out.println(logEntry);
          }
      }

      //ask after every run, if user wants to continue
      String response = InputValidation.validateString(scanner, "Would you like to try another annealing attempt with the same route? (yes/no): ",
      "(?i)yes|no", "Invalid input. Please enter yes or no.");
      if (response.equalsIgnoreCase("no")) {
          continueAnnealing = false; // end while
      }
    }

    scanner.close();
    System.out.println("Thank you for using the program!");
  }
}