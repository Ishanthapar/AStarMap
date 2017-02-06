import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Team Members: Ishan Thapar, 999104208 and Samir Rahman, 998988726
 * Following wikipedia pseudocode: https://en.wikipedia.org/wiki/A*_search_algorithm
 */
public class AstarSearchAIBetter implements AIModule
{
    private final ArrayList<Point> path = new ArrayList<Point>();
    private final ArrayList<Point> restructuredPath = new ArrayList<>();
    public List<Point> createPath(TerrainMap map)
    {
        HashSet<Point> closedSet = new HashSet<Point>();
        final ArrayList<Point> openSet = new ArrayList<Point>();
        Point CurrentPoint = map.getStartPoint();
        Point current = CurrentPoint;
        openSet.add(CurrentPoint);
        double totalScore = Double.MAX_VALUE;
        HashMap<Point, Point> camefrom = new HashMap<>();
        HashMap<Point, Double> gscore = new HashMap<Point, Double>();
        HashMap<Point, Double> Fscore = new HashMap<>();
        gscore.put(CurrentPoint, 0.0);
        Fscore.put(CurrentPoint, getHeuristic(map, CurrentPoint, map.getEndPoint()));

        while (!openSet.isEmpty())
        {
            for (int i = 0; i < openSet.size(); i++)
            {
                double tempFscore = Fscore.get(openSet.get(i));
                if (tempFscore < totalScore)
                {
                    current = openSet.get(i);
                    totalScore = tempFscore;
                }
            }
            totalScore = Double.MAX_VALUE;

            if (current.x == map.getEndPoint().x && current.y == map.getEndPoint().y)
            {
                path.add(current);
                return reconstructPath(camefrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            Point[] neighbors = map.getNeighbors(current);
            for (int i = 0; i < neighbors.length; i++)
            {
                if (closedSet.contains(neighbors[i]))
                    continue;

                double tempScore = gscore.get(current) + map.getCost(current, neighbors[i]);

                if (!openSet.contains(neighbors[i]))
                    openSet.add(neighbors[i]);

                else if (tempScore >= gscore.get(neighbors[i]))
                    continue;

                camefrom.put(neighbors[i],current);
                gscore.put(neighbors[i], tempScore);
                Fscore.put(neighbors[i], gscore.get(neighbors[i]) + getHeuristic(map, neighbors[i], map.getEndPoint()));
            }
        }
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
    private double getHeuristic(TerrainMap map, final Point pt1, final Point pt2)
    {
        // Heuristic for Bizzaro World Cost Function
        double h1 = map.getTile(pt1);
        double h2 = map.getTile(pt2);

        double dx = Math.abs(pt2.x - pt1.x);
        double dy = Math.abs(pt2.y - pt1.y);

        double numSteps = (dx + dy) + -1 * Math.min(dx, dy);
        double tempHeight = h1;
        double deltah = h1 - h2;
        double cost = 0;
        for(int i = 0; i < numSteps; i++)
        {
            double nextHeightDifference = deltah / numSteps;
            cost += tempHeight / (tempHeight - nextHeightDifference + 1);
            tempHeight -= nextHeightDifference;
        }
        return cost;

        // Heuristic for Power of the height difference cost function
          /*double h1 = map.getTile(pt1);
        double h2 = map.getTile(pt2);

        double dx = Math.abs(pt2.x - pt1.x);
        double dy = Math.abs(pt2.y - pt1.y);

        // moving up
        double numSteps = (dx + dy) + (1 - 2) * Math.min(dx, dy);
        if(h2 >= h1)
            return 1 * ((dx + dy) + (1 - 2) * Math.min(dx, dy));
        else
            return Math.pow(2, (h2 - h1) / numSteps) * ((dx + dy) + (1 - 2) * Math.min(dx, dy));*/

    }

    /**
     * Reconstructs optimal path to goal from a given HashMap containing all explored nodes.
     * @param cameFrom HashMap containing explored nodes with possible optimal paths to goal
     * @param currentNode Goal Node
     * @return Shortest, most optimal path to goal beginning from start point and ending at end point
     */
    private ArrayList<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point currentNode)
    {
        while (cameFrom.containsKey(currentNode))
        {
            currentNode = cameFrom.get(currentNode);
            path.add(currentNode);
        }

        for (int i = path.size()-1; i >= 0; i--)
        {
            restructuredPath.add(path.get(i));
        }
        return restructuredPath;

    }
}
