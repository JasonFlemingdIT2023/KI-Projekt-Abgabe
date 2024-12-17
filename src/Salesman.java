import java.util.ArrayList;
import java.util.Collections;

public class Salesman {
  private final ArrayList<City> cities;
  private ArrayList<City> bestRoute;
  private double bestDistance;

  //constructor
  public Salesman(){
    this.cities = new ArrayList<>();
    this.bestRoute = new ArrayList<>();
    this.bestDistance = Double.MAX_VALUE;
  }
  //get functions
  public ArrayList<City> getCities(){ return cities; }
  public ArrayList<City> getBestRoute(){ return bestRoute; }
  public double getBestDistance(){ return bestDistance; }
  
  //add cities to list array
  public void addCities(City city){
    this.cities.add(city);
  } 
  
  //first route for simulated annealing
  public ArrayList<City> getFirstRoute(){
    ArrayList<City> firstRoute = new ArrayList<>(cities);
    Collections.shuffle(firstRoute); //randomize the city order
    return firstRoute;
  }

  public double calcRouteDistance(ArrayList<City> route){
    double totalDistance = 0;
    for (int i = 0; i < route.size() - 1; i++) {
        City fromCity = route.get(i);
        City toCity = route.get(i + 1);
        totalDistance += fromCity.distanceTo(toCity);
    }
    totalDistance += route.get(route.size() - 1).distanceTo(route.get(0));//last to first city
    return totalDistance;
  }
  //set new bestRoute
  public void setBestRoute(ArrayList<City> route){
   this.bestRoute = new ArrayList<>(route);
   this.bestDistance = calcRouteDistance(route);
  }

}
