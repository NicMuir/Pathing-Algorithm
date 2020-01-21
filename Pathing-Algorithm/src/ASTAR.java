import java.awt.*;
import java.util.*;




public class ASTAR {
    public static cell arr[][];
    public static double[][] ArrpointsHeur;
    public static double[][] Arrpointsmanhattan;
    public static ArrayList<Point> Pts = new ArrayList<>();
    public static Point Start;
    public static Point End;
    public static String[][] Adj;

    public ASTAR(double[][] Heuris , double[][] manhat , ArrayList<Point> pnts ,Point start , Point end , String[][] adj)
{
        ArrpointsHeur = Heuris;
        Arrpointsmanhattan = manhat;
        Pts = pnts;
        Start = start;
        End = end;
        Adj = adj;

    }

    public static void main(){
        astar();
    }

    public static int astar(){
            Point camefrom[] = new Point[Pts.size()];
           /* ArrayList<cell> openset = new ArrayList<cell>();
             openset.add(new cell(Start , null , 0 , 0 , 0 ));
            */
             ArrayList<Point> open = new ArrayList<>();
             ArrayList<Point> closed = new ArrayList<>();
           boolean visited[] = new boolean[Pts.size()];
            open.add(Start);
            Point[] prev = new Point[Pts.size()];
            int[] gScore = new int[Pts.size()];
            double[] fScore = new double[Pts.size()];
            for(int k =0 ; k< gScore.length;k++){
                gScore[k] = Integer.MAX_VALUE;
                fScore[k] = Integer.MAX_VALUE;
                visited[k] = false;
            }

            gScore[Pts.indexOf(Start)] = 0;
            fScore[Pts.indexOf(Start)] = 0;


            while(!open.isEmpty()){
                int index = lowestfscore(fScore , visited);
                Point curr = new Point(open.get(open.indexOf(Pts.get(index))));
                if(curr.equals(End)){
                    recoPath(camefrom , curr);
                    return 1;
                }

                open.remove(curr);
                visited[Pts.indexOf(curr)] = true;
                closed.add(curr);
                for(int j =0;j< Pts.size() ;j++){
                    if(Adj[Pts.indexOf(curr)][j] == "0" || closed.contains(Pts.get(j))){
                        continue;
                    }
                    double tent_gScore = gScore[Pts.indexOf(curr)] + Arrpointsmanhattan[Pts.indexOf(curr)][j];
                    if(!open.contains(Pts.get(j))){
                        open.add(Pts.get(j));
                    }
                    if(tent_gScore < gScore[j]){
                            camefrom[j] = curr;
                            gScore[j] =(int) tent_gScore;
                            fScore[j] = gScore[j]+ArrpointsHeur[Pts.indexOf(curr)][j];

                    }

                }

            }
            System.out.println("-1");
            return -1;

    }

    public static int lowestfscore(double[] fScore , boolean[] visited){

        int tempindex = 0;
        double temp = Double.MAX_VALUE;
        for(int k =0;k<fScore.length;k++){
            if(temp > fScore[k] && visited[k] != true){
                temp = fScore[k];
                tempindex = k;
            }

        }

        return tempindex ;
    }
    public static void recoPath(Point[] prev, Point curr){
        ArrayList<Point> Path = new ArrayList<Point>();

        Point tmppt;
        tmppt = curr;
        while(!tmppt.equals(Start)){
            Path.add(tmppt);
            tmppt = prev[Pts.indexOf(tmppt)];

        }
            Path.add(Start);
        Collections.reverse(Path);


        for(int k =0;k<Path.size();k++){


          System.out.print(Pts.indexOf(Path.get(k)));


        }
    }


}
