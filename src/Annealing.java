import java.util.ArrayList;
import java.util.Collections;

public class Annealing {
  private double temperature;
  private final double coolingRate;
  private ArrayList<City> bestRoute;
  private double bestDistance;
  private final ArrayList<String> localMinimas;
  
  //annealing constructor
  public Annealing(double firstTemperature, double coolingRate, Salesman salesman){
    this.temperature = firstTemperature;
    this.coolingRate = coolingRate;
    this.bestRoute = salesman.getBestRoute();
    this.bestDistance = salesman.calcRouteDistance(bestRoute);
    this.localMinimas = new ArrayList<>();
  }
  //getters
  public ArrayList<City> getBestRoute(){ return bestRoute; }
  public double getBestDistance(){ return bestDistance; }
  public ArrayList<String> getLocalMinimaLog() { return localMinimas; }
  
  //mutation, in this case swapping random cities
  private ArrayList<City> generateRoutes(ArrayList<City> route){
    ArrayList<City> newRoute = new ArrayList<>(route);
    int number1 = (int) (newRoute.size() * Math.random());
    int number2 = (int) (newRoute.size() * Math.random());
    Collections.swap(newRoute, number1, number2);
    return newRoute;
  }

  //acceptanceProbability
  private double acceptanceProbability(double currentDistance, double newDistance, double temperature){
    if (newDistance < currentDistance) {
        return 1.0; 
    } 
    return Math.exp((currentDistance - newDistance) / temperature);
    }


  //runs the annealing process
  public void runAnnealing(Salesman salesman, String coolingStrategy){
    ArrayList<City> currentRoute = new ArrayList<>(bestRoute);
    double currentDistance = bestDistance;
    int round = 0;

    //for local minimas
    int noImprovementCounter = 0;
    int maxNoImprovement = calculateMaxNoImprovement(coolingStrategy); 

    while( temperature > 1){
      ArrayList<City> newRoute = generateRoutes(currentRoute);
      double newDistance = salesman.calcRouteDistance(newRoute);

      if(acceptanceProbability(currentDistance, newDistance, temperature) > Math.random()){
         currentRoute = new ArrayList<>(newRoute);
         currentDistance = newDistance;
      }

      if(currentDistance < bestDistance){
         bestRoute = new ArrayList<>(currentRoute);
         bestDistance = currentDistance;  
         noImprovementCounter = 0;
      } else {
         noImprovementCounter++;
      }

      //find local minimas
      if (noImprovementCounter >= maxNoImprovement && temperature > 0.01) {
      //check if the distance is already logged
        boolean isDuplicate = false;
        for (String entry : localMinimas) {
          if (entry.contains(String.format("Distance=%.2f", currentDistance))) {
            isDuplicate = true;
            break;
          }
        }
        //new entry, if there are no duplicates
        if (!isDuplicate) {
          String logEntry = String.format(
          "Local Minima at round %d: Distance=%.2f, Temperature=%.2f",
          round, currentDistance, temperature
          );
          localMinimas.add(logEntry);
        }
        noImprovementCounter = 0; 
      }
  

      //the different cooling strategys
      if (coolingStrategy.equalsIgnoreCase("linear")) {
        temperature -= 0.1;
      } else if (coolingStrategy.equalsIgnoreCase("exponential")) {
        temperature *= coolingRate;
      }
      //progress updates
      if (round % 1000 == 0) { 
         System.out.printf("Round %d: current best distance: %.2f\n", round, bestDistance);
      }
      round++;
   }
   System.out.printf("Simulated annealing completed after %d iterations.\n", round);
  }

  //calculate MaxNoImprovement based on coolingStrategy and Temperature
  //to avoid too much localMinima output
  private int calculateMaxNoImprovement(String coolingStrategy) {
    if (coolingStrategy.equalsIgnoreCase("linear")) {
        return (int) (1000 * (temperature / 100) * 0.1); 
    } else {
      //log() to stabilize exponential behaviour for high and low temperatures
        double logTemp = Math.log(temperature + 1); 
        return (int) (100 * logTemp * coolingRate); 
    } 
  }
}
