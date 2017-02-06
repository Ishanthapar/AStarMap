import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Ishan on 2/3/17.
 */
public class AstarSearchAIBetter implements AIModule
{
    private final ArrayList<Point> path = new ArrayList<Point>();
    private final ArrayList<Point> restructuredPath = new ArrayList<>();
    public List<Point> createPath(TerrainMap map)
    {
//        final ArrayList<Point> closedSet = new ArrayList<Point>();
        HashSet<Point> closedSet = new HashSet<Point>();
        final ArrayList<Point> openSet = new ArrayList<Point>();
        Point CurrentPoint = map.getStartPoint();
        Point current = CurrentPoint;
        openSet.add(CurrentPoint);
        //path.add(CurrentPoint);
        double totalScore = Double.MAX_VALUE;
        double gScore = 0.0;
        double fScore = Double.MAX_VALUE;
        double tempgScore = 0.0;
        HashMap<Point, Point> camefrom = new HashMap<>();
        HashMap<Point, Double> gscore = new HashMap<Point, Double>();
        HashMap<Point, Double> Fscore = new HashMap<>();
        gscore.put(CurrentPoint, 0.0);
        Fscore.put(CurrentPoint, getHeuristic(map, CurrentPoint, map.getEndPoint()));
        //System.out.println(gscore.get(CurrentPoint));

        while (!openSet.isEmpty())
        {
            for (int i = 0; i < openSet.size(); i++)
            {
                //tempgScore = gscore.get(openSet.get(i)) + map.getCost(current, openSet.get(i));
                //double tempFscore = tempgScore + getHeuristic(openSet.get(i), map.getEndPoint());
                double tempFscore = Fscore.get(openSet.get(i));
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
                //return reconstructPath(path, map);
                return reconstructPath(camefrom, current);
                //return reconstructPath(closedSet, map);
                //break;
            }

            openSet.remove(current);
            closedSet.add(current);
            //gScore = tempgScore;

            Point[] neighbors = map.getNeighbors(current);
            for (int i = 0; i < neighbors.length; i++)
            {
                if (closedSet.contains(neighbors[i]))
                    continue;

                double tempScore = gscore.get(current) + map.getCost(current, neighbors[i]);
                //System.out.println(tempScore);
                //System.out.println(map.getCost(map.getStartPoint(), neighbors[i]));

                if (!openSet.contains(neighbors[i]))
                    openSet.add(neighbors[i]);

                else if (tempScore >= gscore.get(neighbors[i]))
                    continue;

                //System.out.println("Chosen Point: (" + neighbors[i].x + "," + neighbors[i].y + ")");
                camefrom.put(neighbors[i],current);
                gscore.put(neighbors[i], tempScore);
                Fscore.put(neighbors[i], gscore.get(neighbors[i]) + getHeuristic(map, neighbors[i], map.getEndPoint()));
                //path.add(neighbors[i]);
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
    private double getHeuristic(TerrainMap map, final Point pt1, final Point pt2)
    {
//        return 0;
        double h1 = map.getTile(pt1);
        double h2 = map.getTile(pt2);

        double dx = Math.abs(pt2.x - pt1.x);
        double dy = Math.abs(pt2.y - pt1.y);

        // moving up
        double numSteps = (dx + dy) + -1 * Math.min(dx, dy);
        if(h2 >= h1)
            return 1 * ((dx + dy) + -1 * Math.min(dx, dy));
//            return Math.sqrt(dx * dx + dy * dy);
        else
            return Math.pow(2, (h2 - h1) / numSteps) * numSteps;
    }


    private ArrayList<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point currentNode)
    {
        //System.out.println("Inside reconstructPath");
        //System.out.println("("+currentNode.x+","+currentNode.y+")");
        //path.add(currentNode);
        while (cameFrom.containsKey(currentNode))
        {
            //System.out.println("Inside while in reconstruct path");
            currentNode = cameFrom.get(currentNode);
            path.add(currentNode);
        }

        //System.out.println("Final Path:");

        for (int i = path.size()-1; i >= 0; i--)
        {
            //System.out.println("("+path.get(i).x+","+path.get(i).y+")");
            restructuredPath.add(path.get(i));
        }

        /*for (int i = 0; i < restructuredPath.size(); i++)
        {
            System.out.println("(" + restructuredPath.get(i).x + "," + restructuredPath.get(i).y + ")");
        }*/

        //Collections.reverse(path);
        return restructuredPath;

        /*
        ArrayList<Point> reconstructedPath = new ArrayList<Point>();
        reconstructedPath.add(path.get(0));
        for (int i = 0; i < reconstructedPath.size(); i++)
        {
            for (int j = 0; j < path.size(); j++)
            {
                if (!map.isAdjacent(reconstructedPath.get(i), path.get(j)))
                {
                    //System.out.println("Inside if");
                    reconstructedPath.add(path.get(j));
                    path.remove(path.get(j));
                    break;
                }
            }
        }
        //for (int i = 0; i < reconstructedPath.size(); i++)
        //{
            //System.out.println("Chosen Point: (" + reconstructedPath.get(i).x + "," + reconstructedPath.get(i).y + ")");
        //}
        return reconstructedPath;*/
    }
}
