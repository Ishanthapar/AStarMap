import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ishan on 2/2/17.
 */
public class ShortestDistanceAI implements AIModule{

    private final ArrayList<Point> path = new ArrayList<Point>();

    //private double totalCost;

    //private double tempCost;
    TreeSet<Point> temp = new TreeSet<>();
    //private double heuristicEstimate;

    public List<Point> createPath(final TerrainMap map){
        final ArrayList<Point> explored = new ArrayList<Point>();
        //final ArrayList<Point> neighbors = new ArrayList<Point>();
        Point CurrentPoint = map.getStartPoint();
        path.add(new Point(CurrentPoint));

        //double totalCost = getHeuristic(CurrentPoint,map.getEndPoint());
        //totalCost = Double.MAX_VALUE;
        double tempCost;
        //Point[] neighbors = new Point[0];

        while(map.getEndPoint().x != CurrentPoint.x && map.getEndPoint().y != CurrentPoint.y)
        {
            //System.out.println("Beginning while");
            Point[] neighbors = new Point[0];
            neighbors = map.getNeighbors(CurrentPoint);
            Point tempPoint = null;
            double compareCost = Double.MAX_VALUE;
            for (int i = 0; i < neighbors.length; i++)
            {
                //System.out.println("Beginning For");
                if (explored.contains(neighbors[i]))
                    continue;
                //explored.add(neighbors[i]);
                //tempCost = totalCost + map.getCost(CurrentPoint, neighbors[i]) + getHeuristic(neighbors[i], map.getEndPoint());
                //if (compareCost > tempCost)
                  //  continue;
                tempPoint = neighbors[i];
                explored.add(neighbors[i]);
                //CurrentPoint = neighbors[i];
                //path.add(neighbors[i]);
                //totalCost = tempCost;
            }
            CurrentPoint = tempPoint;
            System.out.println("New Current Point: (" + CurrentPoint.x + "," + CurrentPoint.y + ")");
            path.add(CurrentPoint);
            //totalCost = Integer.MAX_VALUE;

        }

        /*Point[] neighbors = new Point[0];
        neighbors = map.getNeighbors(CurrentPoint);
        for (int i = 0; i < neighbors.length; i++){
            System.out.println("("+neighbors[i].x+","+neighbors[i].y+")\n");
        }
        System.out.println("Current Point: ("+CurrentPoint.x+","+CurrentPoint.y+")\n");
        System.out.println(map.getCost(CurrentPoint,neighbors[2]));
        System.out.println("End Point: ("+map.getEndPoint().x+","+map.getEndPoint().y+")");*/
        path.add(CurrentPoint);
        return path;
    }

    /**
     * Computes and returns heuristic cost for going from current point
     * to destination point. The heuristic function is Euclidean
     * distance between current and destination point.
     * @param pt1 The current point
     * @param pt2 The destination point
     * @return The heuristic cost for going from current point to destination point in the map
     */
    /*private double getHeuristic(final Point pt1, final Point pt2)
    {
        double heuristicEstimate;
        double tempX = pt2.x - pt1.x;
        double tempY = pt2.y - pt1.y;
        tempX = tempX * tempX;
        tempY = tempY * tempY;
        heuristicEstimate = Math.sqrt(tempX + tempY);
        return heuristicEstimate;
    }*/
}
