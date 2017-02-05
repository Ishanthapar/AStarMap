/*
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
        this.fScore = fScore;
    }
    public double fScore;

    public int compareTo(PointF p2)
    {
        if(fScore < p2.fScore)
            return -1;
        else if(fScore == p2.fScore)
            return 0;
        else
            return -1;
    }
}
// Don't think we need this.
class pointCompare implements Comparator<PointF>
{
    public int compare(PointF p1, PointF p2)
    {
        if(p1.fScore < p2.fScore)
            return -1;
        else if(p1.fScore == p2.fScore)
            return 0;
        else
            return -1;
    }
}

public class ShortestPathAISlow implements AIModule
{
    public static Point start;
    public static Point endpoint;

    public java.util.List<Point> createPath(final TerrainMap map)
    {
        final ArrayList<Point> path = new ArrayList<Point>();
        Point start = map.getStartPoint();
        Point endpoint = map.getEndPoint();

        // Create a h(n) and initialize all points as euclidian distance.
        double[][] hScore = new double[map.getWidth()][map.getHeight()];

//        System.out.println("h width is " + hScore.length + "h height is " + hScore[0].length);
        for(int i = 0; i < hScore.length; i++)
        {
            for (int j = 0; j < hScore[0].length; j++)
            {
//                System.out.println("i is " + i + "j is " + j);
                hScore[i][j] = Math.sqrt(Math.abs(i - endpoint.x) * Math.abs(i - endpoint.x) +
                        Math.abs(j - endpoint.y) * Math.abs(j - endpoint.y));
            }
        }


        // fscoreMap maps the f() value to the actual Point. TreeMap = sorted map, so first element = min of fscoreMap
        // TreeMap<Double, Point> fscoreMap = new TreeMap<Double, Point>();

        // openSet is a TreeSet, meaning it is sorted in ascending order by fScore.
        TreeSet<PointF> openSet = new TreeSet<PointF>();
        openSet.add(new PointF(start, 0));
        HashMap<Point, Point> cameFrom = new HashMap<Point, Point>();
        // fscoreMap.put(hScore[start.x][start.y], map.getStartPoint());

        // HashSet instead of TreeSet because we don't care about order of closee set.
        HashSet<PointF> closedSet = new HashSet<PointF>();

        // Following wikipedia pseudo code: https://en.wikipedia.org/wiki/A*_search_algorithm
        double[][] gScore = new double[map.getWidth()][map.getHeight()];

        // fill only works for 1-d arrays :(
        for(int i = 0; i < gScore.length; i++)
        {
            for (int j = 0; j < gScore[0].length; j++)
                gScore[i][j] = Double.MAX_VALUE;
        }

        gScore[start.x][start.y] = 0;

        double[][] fScore = gScore.clone();

//        Arrays.fill(fScore, Double.MAX_VALUE);
        fScore[start.x][start.y] = hScore[start.x][start.y];
        ArrayList<Integer> x = new ArrayList<Integer>();
        while(!openSet.isEmpty())
        {
            // Retrieves point with lowest f score and removes from the set.
            Point current = openSet.first();
            if(current == endpoint)
                return reconstruct_path(cameFrom, current);
            openSet.pollFirst();
            // Just give everything f score of 0, it doesn't really matter.
            closedSet.add(new PointF(current, 0));
            Point[] neighbors = map.getNeighbors(current);

            for(Point neighborPoint : neighbors)
            {
                PointF neighbor = new PointF(neighborPoint, fScore[neighborPoint.x][neighborPoint.y]);
                if(closedSet.contains(neighbor))
                    continue;

                double tentative_g = gScore[current.x][current.y] + map.getCost(current, neighbor);
                if(!openSet.contains(neighbor))
                    openSet.add(new PointF(neighbor, tentative_g));
                else if(tentative_g >= gScore[neighbor.x][neighbor.y])
                    continue;

                // If we reach here, we have found the best path?
                cameFrom.put(neighbor, current);
                gScore[neighbor.x][neighbor.y] = tentative_g;
                fScore[neighbor.x][neighbor.y] = gScore[neighbor.x][neighbor.y]
                        + hScore[neighbor.x][neighbor.y];
            }
        }

        return path;
    }

    public java.util.List<Point> reconstruct_path(HashMap<Point, Point> cameFrom, Point current)
    {
        final ArrayList<Point> path = new ArrayList<Point>();
        path.add(current);
        while(cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            path.add(current);
        }

        return path;

    }

}*/
