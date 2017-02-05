import java.awt.*;
import java.awt.List;
import java.util.*;
import java.awt.Point;

// This class adds the Fscore attribute to the Point class. Also implements comparison by F score.
class PointF extends Point implements Comparable<PointF>
{
    public PointF(Point p, double fScore)
    {
        this.x = p.x;
        this.y = p.y;
        this.point = p;
        this.fScore = fScore;
    }
    public double fScore;
    public Point point;

    public int compareTo(PointF p2)
    {
        if(x == p2.x && y == p2.y)
            return 0;
        if(fScore < p2.fScore)
            return -1;
        else
            return 1;
    }

    // set equal pointFs by inner point or by coordinates
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof PointF)) {
            return false;
        }
        PointF point2 = (PointF) o;
        return (point.x == point2.x && point.y == point2.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, x, y, fScore);
    }
}

public class ShortestPathAITest implements AIModule
{
    private final ArrayList<Point> path = new ArrayList<Point>();
    private final ArrayList<Point> restructuredPath = new ArrayList<>();
    public static HashMap<Point, Double> Fscore;
    public java.util.List<Point> createPath(TerrainMap map)
    {
        HashSet<PointF> closedSet = new HashSet<PointF>();
//        final ArrayList<Point> closedSet = new ArrayList<Point>();

        TreeSet<PointF> openSet = new TreeSet<PointF>();
        Point startPoint = map.getStartPoint();
        openSet.add(new PointF(startPoint, getHeuristic(startPoint, map.getEndPoint())));

        HashMap<Point, Point> camefrom = new HashMap<>();
        HashMap<Point, Double> gscore = new HashMap<Point, Double>();
        gscore.put(startPoint, 0.0);

        Fscore = new HashMap<>();
        Fscore.put(startPoint, getHeuristic(startPoint, map.getEndPoint()));
        //System.out.println(gscore.get(CurrentPoint));

//        System.out.println(openSet.contains(p2));
//        System.out.println(openSet.contains(new PointF(CurrentPoint, 20)));
//        openSet.add(new PointF(p, -1));

//        Point p3 = openSet.pollFirst();
//        System.out.println(p3.x + "." + p3.y);

        while (!openSet.isEmpty())
//        while (!openSet2.isEmpty())
        {
//            for (int i = 0; i < openSet.size(); i++)
//            {
//                //tempgScore = gscore.get(openSet.get(i)) + map.getCost(current, openSet.get(i));
//                //double tempFscore = tempgScore + getHeuristic(openSet.get(i), map.getEndPoint());
//                double tempFscore = Fscore.get(openSet2.get(i));
//                if (tempFscore < totalScore)
//                {
//                    //System.out.println(totalScore);
//                    current = openSet2.get(i);
//                    totalScore = tempFscore;
//                }
//            }
            PointF current = openSet.pollFirst();
            System.out.println("Chosen Point: (" + current.x + "," + current.y + ")");

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
            closedSet.add(current);
            //gScore = tempgScore;

            Point[] neighbors = map.getNeighbors(current.point);
            for (int i = 0; i < neighbors.length; i++)
            {
                // might be able to have 0 here.
                PointF neighbor = new PointF(neighbors[i], 0);
                if (closedSet.contains(neighbor))
                    continue;
                gscore.get(current.point);
                map.getCost(current.point, neighbors[i]);
                double tempScore = gscore.get(current.point) + map.getCost(current.point, neighbors[i]);

                neighbor.fScore = tempScore;
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);

                else if (tempScore >= gscore.get(neighbors[i]))
                    continue;

                camefrom.put(neighbors[i],current);
                gscore.put(neighbors[i], tempScore);
                Fscore.put(neighbors[i], gscore.get(neighbors[i]) + getHeuristic(neighbors[i], map.getEndPoint()));
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


    private ArrayList<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point currentNode)
    {
        while (cameFrom.containsKey(currentNode))
        {
            currentNode = cameFrom.get(currentNode);
            path.add(currentNode);
        }

        Collections.reverse(path);
        return path;
    }

    class pointCompare implements Comparator<Point>
    {
        public int compare(Point p1, Point p2)
        {
            if(p1.x == p2.x && p1.y == p2.y)
                return 0;
            else if(ShortestPathAITest.Fscore.get(p1) < ShortestPathAITest.Fscore.get(p2))
                return -1;
            else
                return 1;
        }
    }
}
