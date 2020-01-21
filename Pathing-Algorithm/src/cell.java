import java.awt.*;

public class cell {
    public static Point Coords;
    public static Point Parent;
    public static int Cost;
    public static double Heuristic;
    public static double Netcost;


    public cell(Point coords ,Point parent , int cost ,double heuristic , double netcost ){
        this.Coords= coords;
        this.Parent = parent;
        this.Cost = cost;
        this.Heuristic = heuristic;
        this.Netcost = netcost;

    }

    public static Point getCoords(){

        return Coords;
    }

    public static Point getParent(){


        return Parent;

    }

    public static int getCost(){

        return Cost;
    }

    public static double getHeuristic(){

        return Heuristic;
    }
    public static double getNetcost(){

        return Netcost;
    }


    public static void setParent(Point pt){

        Parent = pt;
    }

    public static void setHeuristic(double ht){

        Heuristic = ht;
    }




}
