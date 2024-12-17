public class City {
 private final int x;
 private final int y;
 private final String name;
 private static int cityCounter = 1;

 //constructor with random coordinates
 public City(){
    this.x = (int) (Math.random() * 1000);
    this.y = (int) (Math.random() * 780);
    this.name = "City: " + cityCounter++;
 }

 //constructor with specified parameters
 public City(int x, int y, String name){
    this.x = x;
    this.y = y;
    this.name = name;
 } 
 //get functions
 public int getX(){ return x;}
 public int getY(){ return y;}
 public String getName(){ return name;}
 
 //euclidean distance for two points in 2D lane
 public double distanceTo(City city){
    int xDistance = Math.abs(this.x - city.getX());
    int yDistance = Math.abs(this.y - city.getY());
    return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
 }

 @Override //override because the output would be classname and address of object
public String toString() {
    return name + ", x=" + x + ", y=" + y;
}
}
