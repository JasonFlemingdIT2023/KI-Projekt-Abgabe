import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RouteVisualizer extends JPanel {
    private final ArrayList<City> cities;
    
    public RouteVisualizer(ArrayList<City> cities) {
        this.cities = cities;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        
        //draw citys as points
        for (City city : cities) {
            g.fillOval(city.getX() - 5, city.getY() - 5, 10, 10);
            g.drawString(city.getName(), city.getX() + 5, city.getY() - 5);
        }

        //mark first city 
        City firstCity = cities.get(0);
        g.setColor(Color.YELLOW);
        g.fillOval(firstCity.getX() - 7, firstCity.getY() - 7, 14, 14);
        g.setColor(Color.BLACK); 
        g.drawString("Start", firstCity.getX() - 20, firstCity.getY() - 15);
  
        //mark last city  
        City lastCity = cities.get(cities.size() - 1);
        g.setColor(Color.YELLOW);
        g.fillOval(lastCity.getX() - 7, lastCity.getY() - 7, 14, 14);
        g.setColor(Color.BLACK); 
        g.drawString("Goal", lastCity.getX() - 20, lastCity.getY() - 15);

        //paint route
        g.setColor(Color.RED);
        for (int i = 0; i < cities.size() - 1; i++) {
            City city1 = cities.get(i);
            City city2 = cities.get(i + 1);
            g.drawLine(city1.getX(), city1.getY(), city2.getX(), city2.getY());
        }

        //last route from end to start city
        g.drawLine(lastCity.getX(), lastCity.getY(), firstCity.getX(), firstCity.getY());
    }

    public static void visualizeRoute(ArrayList<City> route) {
        JFrame frame = new JFrame("Route Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new RouteVisualizer(route));
        frame.setVisible(true);
    }
}