import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ishan on 2/3/17.
 */
public class AstarSearchAI implements AIModule
{
    private final ArrayList<Point> path = new ArrayList<Point>();
    public List<Point> createPath(TerrainMap map)
    {
        final ArrayList<Point> closedSet = new ArrayList<Point>();
        final ArrayList<Point> openSet = new ArrayList<Point>();
        Point CurrentPoint = map.getStartPoint();
        Point current = CurrentPoint;
        openSet.add(CurrentPoint);
        path.add(CurrentPoint);
        double totalScore = Double.MAX_VALUE;
        double gScore = 0.0;
        double fScore = Double.MAX_VALUE;
        double tempgScore = 0.0;

        while (!openSet.isEmpty())
        {
            for (int i = 0; i < openSet.size(); i++)
            {
                tempgScore = gScore + map.getCost(current, openSet.get(i));
                double tempFscore = tempgScore + getHeuristic(openSet.get(i), map.getEndPoint());
                if (tempFscore < totalScore)
                {
                    //System.out.println(totalScore);
                    current = openSet.get(i);
                    totalScore = tempFscore;
                }
            }
            //System.out.println("Chosen Point: (" + current.x + "," + current.y + ")");
            totalScore = Double.MAX_VALUE;

            if (current.x == map.getEndPoint().x && current.y == map.getEndPoint().y){
                //System.out.println("Inside if");
                //closedSet.add(current);
                path.add(current);
                //return path;
                return reconstructPath(path, map);
                //return reconstructPath(closedSet, map);
                //break;
            }

            openSet.remove(current);
            closedSet.add(current);
            gScore = tempgScore;

            Point[] neighbors = map.getNeighbors(current);
            for (int i = 0; i < neighbors.length; i++)
            {
                if (closedSet.contains(neighbors[i]))
                    continue;

                double tempScore = gScore + map.getCost(current, neighbors[i]);
                //System.out.println(tempScore);
                //System.out.println(map.getCost(map.getStartPoint(), neighbors[i]));

                if (!openSet.contains(neighbors[i]))
                    openSet.add(neighbors[i]);

                else if (tempScore >= map.getCost(map.getStartPoint(), neighbors[i]))
                    continue;

                System.out.println("Chosen Point: (" + neighbors[i].x + "," + neighbors[i].y + ")");
                path.add(neighbors[i]);
            }
        }

        //return reconstructPath(path, map);
        return null;
    }

    /**
     * Computes and returns heuristic cost for going from current point
     * to destination point. The heuristic function is Euclidean
     * distance between current and destination point.
     * @param pt1 The current point
     * @param pt2 The destination point
     * @return The heuristic cost for going from current point to destination point in the map
     */
    private double getHeuristic(final Point pt1, final Point pt2)
    {
        double heuristicEstimate;
        double tempX = pt2.x - pt1.x;
        double tempY = pt2.y - pt1.y;
        tempX = tempX * tempX;
        tempY = tempY * tempY;
        heuristicEstimate = Math.sqrt(tempX + tempY);
        return heuristicEstimate;
    }


    private ArrayList<Point> reconstructPath(ArrayList<Point> path, TerrainMap map)
    {
        System.out.println("Inside reconstructPath");
        ArrayList<Point> reconstructedPath = new ArrayList<Point>();
        reconstructedPath.add(path.get(0));
        for (int i = 0; i < reconstructedPath.size(); i++)
        {
            for (int j = 0; j < path.size(); j++)
            {
                if (!map.isAdjacent(reconstructedPath.get(i), path.get(j)))
                {
                    System.out.println("Inside if");
                    reconstructedPath.add(path.get(j));
                    path.remove(path.get(j));
                    break;
                }
            }
        }
        for (int i = 0; i < reconstructedPath.size(); i++)
        {
            System.out.println("Chosen Point: (" + reconstructedPath.get(i).x + "," + reconstructedPath.get(i).y + ")");
        }
        return reconstructedPath;
    }
}
