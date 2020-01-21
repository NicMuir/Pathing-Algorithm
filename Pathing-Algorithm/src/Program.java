import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;


public class Program extends JFrame {

    public static ArrayList<Point> ClosedList = new ArrayList<>();
    public static ArrayList<Point> Pts = new ArrayList<>();
    public static double[][] Arrpoints;
    public static double[][] Arrpointsmanhattan;
    public static Point[][] knnptstok;
    public static double[][] knnptsdist;

    public static ArrayList<Vector> vlist = new ArrayList<>();
    public static ArrayList<Vector> vobsticles = new ArrayList<>();


    public static String[][] Adjamatrix;
    public static int numn;
    public static Point startpte;
    public static Point endpte;
    public static Queue que;


    public Program() {
        setTitle("Graph");
        setSize(625, 625);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }


    public static void main(String[] args) throws IOException {

        Scanner rd = new Scanner(System.in);
        //read in the given info

        //first line
        int numneigh;
        int numobs;
        int numsmppts;
        int dimen;
        String Line;
        //Line = br.readLine();
        Line = rd.nextLine();
        String[] arrln = Line.split(" ", -2);
        numneigh = Integer.parseInt(arrln[0]);
        numobs = Integer.parseInt(arrln[1]);
        numsmppts = Integer.parseInt(arrln[2]);
        dimen = Integer.parseInt(arrln[3]);
        numn = numneigh;

        arrln = null;
        // first line complete

        //start,end
        Point startpt = new Point();
        Point endpt = new Point();

        Line = rd.nextLine();




        arrln = Line.split(",", -2);
        startpt.x = Integer.parseInt(arrln[0]);
        startpt.y = Integer.parseInt(arrln[1]);
        arrln = null;

        Line = rd.nextLine();




        arrln = Line.split(",", -2);
        endpt.x = Integer.parseInt(arrln[0]);
        endpt.y = Integer.parseInt(arrln[1]);
        arrln = null;
        Pts.add(startpt);
        Pts.add(endpt);
        startpte = startpt;
        endpte = endpt;
        //start,end comp


        //construct array of Obsticles
        for (int k = 0; k < numobs; k++) {
            //Line = br.readLine();
            Line = rd.nextLine();
            arrln = Line.split(";", -2);
            createObsticles(arrln[0], arrln[1]);
            arrln = null;
        }
        // obsticles complete


        //construct array of points
        for (int t = 0; t < numsmppts; t++) {
            // Line = br.readLine();
            Line = rd.nextLine();
            //System.out.println(Line);
            arrln = Line.split(",", -2);
            Point temppoint = new Point();

            temppoint.x = Integer.parseInt(arrln[0]);
            temppoint.y = Integer.parseInt(arrln[1]);
            Pts.add(temppoint);
            arrln = null;

        }

        // Array of points complete


        //check for overlaps and remove

        for (int p = 0; p < Pts.size(); p++) {

            if (ClosedList.contains(Pts.get(p))) {
                Pts.remove(p);
                p--;

            }

        }







        //create vectors
        for (int f = 0; f < Pts.size(); f++) {
            for (int u = 0; u < Pts.size(); u++) {
                if (Pts.get(f).equals(Pts.get(u))) {
                    continue;
                } else {
                    Vector<Point> v1 = new Vector<>(CreateVectors(Pts.get(f), Pts.get(u)));
                    Vector<Point> test = new Vector<>();
                    test.add(Pts.get(f));
                    test.add(Pts.get(u));
                    if (vlist.contains(test)) {
                        continue;
                    } else {
                        vlist.add(v1);
                    }
                }
            }


        }


        KNN(numneigh);
        Vcheck2D();
        AdjMatrix(numneigh);


//to display Adj matrix
       /* for (int y = 0; y < Pts.size(); y++) {
            for (int u = 0; u < Pts.size(); u++) {

                System.out.print(Adjamatrix[y][u]);
                System.out.print(" ");
            }
            if (y == Pts.size() - 1) {
                continue;
            }
            System.out.println();
        }*/

        BFS.main(Adjamatrix,0,Pts.size());

        
//to display graph:
         Program p = new Program();

//create adjacency matrix of distances
        double[][] temp = new double[Pts.size()][Pts.size()];
        /*for (int k = 0; k < Pts.size(); k++) {
            for (int j = 0; j < Pts.size(); j++) {
                temp[k][j] = 0;

            }

        }*/
        Arrpoints = temp;
        for (int k = 0; k < Pts.size(); k++) {
            for (int j = 0; j < numn; j++) {
                if (knnptstok[k][j] == null) {
                    continue;
                } else {
                    //adds edge
                    Arrpoints[k][Pts.indexOf(knnptstok[k][j])] = knnptsdist[k][j];

                    //add backedge for traversal

                    Arrpoints[Pts.indexOf(knnptstok[k][j])][k] = knnptsdist[k][j];

                }
            }

        }
            double[][] temparr = new double[Pts.size()][Pts.size()];
        for (int k = 0; k < Pts.size(); k++) {
            for (int j = 0; j < Pts.size(); j++) {
                if (Adjamatrix[k][j].equals("0")) {
                    continue;
                } else {
                    //adds edge
                    temparr[k][j] = Distcheckman(Pts.get(k),Pts.get(j));

                    //add backedge for traversal

                    temparr[j][k] = Distcheckman(Pts.get(k),Pts.get(j));

                }
            }

        }
        Arrpointsmanhattan = temparr;
        System.out.println();
        Dijkstra.main(Arrpointsmanhattan, Pts.size(),Pts.indexOf(endpt));
        Dijkstra.getmindistDij(Pts.indexOf(startpt));
        System.out.println();
        new ASTAR(Arrpoints , Arrpointsmanhattan , Pts, startpte , endpte , Adjamatrix);
        ASTAR.main();
    }


    public static void createObsticles(String toplft, String Botrght) {
        String[] arr = toplft.split(",", -2);
        Point tplft = new Point();
        tplft.x = Integer.parseInt(arr[0]);
        tplft.y = Integer.parseInt(arr[1]);

        //arr=null;

        arr = Botrght.split(",", -2);
        Point botrt = new Point();
        botrt.x = Integer.parseInt(arr[0]);
        botrt.y = Integer.parseInt(arr[1]);
        vobsticles.add(CreateVectors(tplft, botrt));
        // arr=null;


        for (int j = tplft.x; j <= botrt.x; j++) {//x axis
            for (int n = tplft.y; n <= botrt.y; n++) {//y axis
                Point temppt = new Point();
                temppt.x = j;
                temppt.y = n;
                ClosedList.add(temppt);
            }


        }


    }


    public static void KNN(int num) {
        int dist;
        Point[][] knntmp1 = new Point[Pts.size()][num];
        double[][] knntmp2 = new double[Pts.size()][num];
        for (int q = 0; q < Pts.size(); q++) {

            ArrayList<Vector> temp = new ArrayList<>();
            ArrayList<Double> disttemp = new ArrayList<Double>();
            for (int t = 0; t < vlist.size(); t++) {
                if (vlist.get(t).contains(Pts.get(q))) {
                    temp.add(vlist.get(t));

                }


            }
            Point tmp1;
            Point tmp2;
            for (int g = 0; g < temp.size(); g++) {
                tmp1 = new Point((Point) (temp.get(g).get(0)));
                tmp2 = new Point((Point) (temp.get(g).get(1)));
                disttemp.add(Distcheckeuc(tmp1, tmp2));

            }
            //sort array from smallest to larges
            double tmp;

            for (int y = 0; y < disttemp.size() - 1; y++) {
                Vector swapper = new Vector();


                if (disttemp.get(y) > disttemp.get(y + 1)) {
                    tmp = disttemp.get(y);
                    disttemp.set(y, disttemp.get(y + 1));
                    disttemp.set(y + 1, tmp);

                    swapper = temp.get(y);
                    temp.set(y, temp.get(y + 1));
                    temp.set(y + 1, swapper);
                    y = -1;
                }

                for (int n = 0; n < temp.size(); n++) {
                    Vector<Point> tmve = new Vector();
                    tmve.add((Point) temp.get(n).get(1));
                    tmve.add((Point) temp.get(n).get(0));
                    if (temp.contains(tmve)) {
                        disttemp.remove(temp.indexOf(tmve));
                        temp.remove(tmve);
                    }


                }


            }


            //Point dummypt = new Point(200, 200);
            for (int b = 0; b < num; b++) {
                if (temp.size() <= b) {
                    knntmp1[q][b] = null;
                    continue;
                }
                if (Pts.get(q).equals(temp.get(b).get(0))) {
                    knntmp1[q][b] = (Point) temp.get(b).get(1);
                    knntmp2[q][b] = disttemp.get(b);
                } else {
                    knntmp1[q][b] = (Point) temp.get(b).get(0);
                    knntmp2[q][b] = disttemp.get(b);
                }
                //System.out.println(disttemp.get(b));


            }


        }
        knnptstok = knntmp1;
        knnptsdist = knntmp2;


    }


    public static Vector CreateVectors(Point pt1, Point pt2) {

        Vector V = new Vector();

        V.add(pt1);
        V.add(pt2);


        return V;
    }

    public static void Vcheck2D() {

        //obs
        Line2D obs1x1 = new Line2D.Double();
        Line2D obs1x2 = new Line2D.Double();
        Line2D obs1y1 = new Line2D.Double();
        Line2D obs1y2 = new Line2D.Double();
        for (int p = 0; p < Pts.size(); p++) {
            for (int t = 0; t < numn; t++) {
                for (int k = 0; k < vobsticles.size(); k++) {
                    Point pt1 = new Point((Point) vobsticles.get(k).get(0));
                    Point pt2 = new Point((Point) vobsticles.get(k).get(1));
                    double pt5 = 0.5;


                    obs1x1 = new Line2D.Double(pt1.x, pt1.y, pt2.x, pt1.y);
                    obs1x2 = new Line2D.Double(pt1.x, pt2.y, pt2.x, pt2.y);
                    obs1y1 = new Line2D.Double(pt1.x, pt1.y, pt1.x, pt2.y);
                    obs1y2 = new Line2D.Double(pt1.x, pt1.y, pt2.x, pt2.y);

                    Point temp1 = new Point(Pts.get(p));
                    if (knnptstok[p][t] == null) {
                        continue;
                    }
                    Point temp2 = new Point(knnptstok[p][t]);

                    Line2D pnt = new Line2D.Float();
                    pnt.setLine(temp1, temp2);
                    boolean out = pnt.intersectsLine(obs1x1.getX1(), obs1x1.getY1(), obs1x1.getX2(), obs1x1.getY2());
                    boolean out2 = pnt.intersectsLine(obs1x2.getX1(), obs1x2.getY1(), obs1x2.getX2(), obs1x2.getY2());
                    boolean out3 = pnt.intersectsLine(obs1y1.getX1(), obs1y1.getY1(), obs1y1.getX2(), obs1y1.getY2());
                    boolean out4 = pnt.intersectsLine(obs1y2.getX1(), obs1y2.getY1(), obs1y2.getX2(), obs1y2.getY2());
                    // System.out.println(out);
                    Point dummypt = new Point();

                    if (out == true) {
                        knnptstok[p][t] = null;
                    } else if (out2 == true) {
                        knnptstok[p][t] = null;
                    } else if (out3 == true) {
                        knnptstok[p][t] = null;
                    } else if (out4 == true) {
                        knnptstok[p][t] = null;
                    }


                }

            }

        }


    }

    public static void AdjMatrix(int numneigh) {
        String[][] Adjamatrics = new String[Pts.size()][Pts.size()];

        for (int t = 0; t < Pts.size(); t++) {
            for (int f = 0; f < Pts.size(); f++) {
                Adjamatrics[t][f] = "0";
            }
        }
        // Point dummypt = new Point(200,200);
        for (int k = 0; k < Pts.size(); k++) {
            for (int j = 0; j < numneigh; j++) {
                if (knnptstok[k][j] == null) {
                    continue;
                }
                int holder = Pts.indexOf(knnptstok[k][j]);
                Adjamatrics[k][holder] = "1";

            }


        }
        Adjamatrix = Adjamatrics;

    }

    public static double Distcheckman(Point pt1, Point pt2) {
        int x1;
        int x2;
        int y1;
        int y2;
        double dist;
        if (pt1.equals(pt2)) {
            dist = 500;
        } else {

            x1 = pt1.x;
            y1 = pt1.y;
            x2 = pt2.x;
            y2 = pt2.y;

            dist = ((double)Math.abs(x1-x2) + (double)Math.abs(y1-y2));
        }
        return dist;
    }

    public static double Distcheckeuc(Point pt1, Point pt2) {
        int x1;
        int x2;
        int y1;
        int y2;
        double dist;
        if (pt1.equals(pt2)) {
            dist = 500;
        } else {

            x1 = pt1.x;
            y1 = pt1.y;
            x2 = pt2.x;
            y2 = pt2.y;

            dist =(double) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        }
        return dist;
    }

    public void paint(Graphics g) {
        //draw grid
        // g.drawLine();

        //Where ever you see rowW*2 or rowH*2 added to a point,line,or obsticle this shifts the graph down to fit the screen

        int i;
        int width = 625;
        int height = 625;

        //draw rows:
        int rowH = height / 25;

        for (i = 1; i < 26; i++) {
            g.drawLine(1, i * rowH, width, i * rowH);

        }
        // draw cols;
        int rowW = width / 25;
        for (i = 1; i < 26; i++) {
            g.drawLine(i * rowW, 1, i * rowW, height);

        }


        //draw points
        for (i = 0; i < Pts.size(); i++) {
            int x = Pts.get(i).x * rowH - (10 / 2)+rowH*2;
            int y = Pts.get(i).y * rowH - (10 / 2) ;

            //g.drawLine(Pts.get(i).x*rowH,Pts.get(i).y*rowW,Pts.get(i).x*rowH,Pts.get(i).y*rowW);
            g.fillOval(y, x, 10, 10);
        }
        // g.fillOval(25,25,10,10);

        //draw Obs

        for (i = 0; i < vobsticles.size(); i++) {

            Point tmp1 = new Point((Point) vobsticles.get(i).get(0));
            Point tmp2 = new Point((Point) vobsticles.get(i).get(1));

            int pnlW = Math.abs(tmp1.y - tmp2.y);
            int pnlH = Math.abs(tmp1.x - tmp2.x);
            g.drawRect(tmp1.y * rowH, tmp1.x * rowW + rowW*2, pnlW * rowW, pnlH * rowH);
            g.setColor(Color.red);
            g.fillRect(tmp1.y * rowH, tmp1.x * rowW +rowW*2, pnlW * rowW, pnlH * rowH);

        }

        //draw Lines
        int r = 100;
        int n = 0;
        int b = 0;
        for (i = 0; i < Pts.size(); i++) {

            Color color = new Color(r, n, b);
            g.setColor(color);
            n = n + 10;
            b = b + 15;
            r = r + 10;
            for (int j = 0; j < numn; j++) {
                if (knnptstok[i][j] == null) {
                    continue;
                }
                g.drawLine(Pts.get(i).y * rowH, Pts.get(i).x * rowW +rowW*2, knnptstok[i][j].y * rowH, knnptstok[i][j].x * rowW+rowW*2);


            }


        }

    }

}
