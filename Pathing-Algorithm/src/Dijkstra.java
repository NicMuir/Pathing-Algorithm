//import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.*;


public class Dijkstra {

    public static double adjMatrix[][];
    public static int verts;
    public static int Goal;

    public Dijkstra(){
    }


    public static void main(double[][] matrix,int vert,int goal){
        adjMatrix = matrix;
        verts=vert;
        Goal = goal;

    }

   public static int getminvert(boolean[] vst , double[] key){
        double minkey =Integer.MAX_VALUE;
        int vert = -1;
        for(int k =0;k<verts;k++){
            if(vst[k]==false && minkey > key[k]){
                minkey = key[k];
                vert = k;

            }

        }
      return vert;
    }

    public static void Printdij(int source , int[] key , double[] dist){
        ArrayList<Integer> arr = new ArrayList<Integer>();
        int temp =  key[Goal];
        //int sum=0;
       arr.add(Goal);
        while(temp != source){
            //sum=sum+(int)dist[temp];
            arr.add(temp);
            temp =  key[temp];

        }
        arr.add(source);
        Collections.reverse(arr);

        for(int k=0;k< arr.size();k++){
            System.out.print(arr.get(k)+ " ");

        }
        System.out.println();
        System.out.println((int) dist[Goal]);

    }



    public static void getmindistDij(int source){
        boolean[] vst = new boolean[verts];
        double[] dist = new double[verts];
        int[] Parent = new int[verts];
        int Infin = Integer.MAX_VALUE;



        for(int r=0;r<verts;r++){
            dist[r]=Infin;
            Parent[r]=-1;
        }

        dist[source] = 0;
        Parent[source] = 0;


        for(int i=0;i<verts;i++){

            int vertmin = getminvert(vst,dist);
            if(vertmin == 1){
                break;
            }
            vst[vertmin] = true;

            for(int q =0;q<verts ; q++){

                if(adjMatrix[vertmin][q]>0){
                    if(vst[q]==false && adjMatrix[vertmin][q]!=Infin){

                        double key = adjMatrix[vertmin][q] + dist[vertmin];
                        if(key < dist[q]){
                            dist[q] = key;
                            Parent[q]=vertmin;
                        }

                    }
                }

            }

        }

        Printdij(source,Parent ,dist);
        System.out.println();

    }

    public static void PrintGraph(int[] parent){

        for(int t=0; t<verts;t++){
            if(parent[t]==-1){
                continue;
            }

            System.out.print(parent[t]+"\t");
        }



    }


}
