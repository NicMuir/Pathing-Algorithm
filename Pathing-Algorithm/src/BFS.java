import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

   public Queue<Node> queue;
   public static ArrayList<Node> allnodes = new ArrayList<Node>();

    public static void main(String[][] adjmatrix , int nd,int ptssize){
        Node startnode = new Node(nd);
        allnodes.add(startnode);

        int[][] newadj = new int[adjmatrix.length][adjmatrix.length];
        for(int k = 0;k<adjmatrix.length;k++){
            for(int j =0;j<adjmatrix.length;j++){
               newadj[k][j]=Integer.parseInt(adjmatrix[k][j]);
            }
        }

            BFS bfsrun = new BFS(ptssize);
            bfsrun.bfs(newadj,startnode);

    }
    public BFS(int ptssize){

        queue = new LinkedList<Node>();

        for(int t=1;t<ptssize;t++){
            Node nde =new Node(t);
            allnodes.add(nde);
        }

    }

    static class Node{
        int data;
        boolean vis;

        Node(int data){
            this.data = data;

        }

    }


    public ArrayList<Node> neighbours(int adjmatrix[][],Node x) {
        int Index = -1;

        ArrayList<Node> neighbours = new ArrayList<Node>();
        for (int k = 0; k < allnodes.size(); k++) {
            if (allnodes.get(k).data == x.data) {
                Index = k;
                break;

            }

        }
        if (Index != -1) {

            for (int k = 0; k < adjmatrix[Index].length; k++) {
                if (adjmatrix[Index][k] == 1) {

                    neighbours.add(allnodes.get(k));

                }

            }


        }

        return neighbours;
    }

    public void bfs(int adjmatrix[][],Node node){
        queue.add(node);
        node.vis = true;
        while(!queue.isEmpty()){


            Node elm = queue.remove();
            System.out.print(elm.data+" ");
            ArrayList<Node> neigh = neighbours(adjmatrix,elm);
            for(int k =0;k<neigh.size();k++){
                Node n = neigh.get(k);
                if(n!=null && !n.vis){
                    queue.add(n);
                    n.vis = true;

                }
            }

        }

    }
}
