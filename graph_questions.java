import java.util.*;
import java.lang.*;
import java.io.*;


class graph_questions.java{

    //Basic graph construction
    
    ArrayList<Edge>[] graph;

    public static class Edge{
        int v = 0;
        int w = 0;
        public Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }


    public static void addEdge(ArrayList<Edge>[] graph,int u,int v,int w){
            graph[u].add(new Edge(v,w));
            graph[v].add(new Edge(u,w));
    }
    

    public static void display(ArrayList<Edge>[] graph,int N){
        for(int i=0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e: graph[i]){
                System.out.print("(" + e.v + ", " + e.w + "), ");
            }

            System.out.println();
        }
    }




//1 BFS
// https://practice.geeksforgeeks.org/problems/bfs-traversal-of-graph/1

static ArrayList<Integer> bfs(ArrayList<ArrayList<Integer>> g, int N) {
       LinkedList<Integer> q = new LinkedList<>();
       boolean[] visited = new boolean[g.size()];
       
       ArrayList<Integer> ans = new ArrayList<Integer>();
       q.addLast(0);

       while(q.size()!=0){
           int num = q.removeFirst();
           if(visited[num]==false){
               visited[num] = true;
               for(int val: g.get(num)){
                   if(visited[val]==false){
                        q.addLast(val);
                   }
               }
           }
       }
       return ans;
    }


// 2 ==================================================================================================================

    //https://leetcode.com/problems/is-graph-bipartite
    public boolean isBipartite(int[][] graph) {
        int nodes = graph.length;
        int vis[] = new int[nodes];
        Arrays.fill(vis,-1);
        
        for(int i=0;i<nodes;i++){
            if(vis[i]==-1){
                if(!isBipartite_(graph,i, vis)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isBipartite_(int[][] graph,int src,int vis[]) {
        
        LinkedList<int[]> q = new LinkedList<int[]>();
        q.addLast(new int[]{src, 0}); // 0: red, 1: blue
        
        while(q.size()!=0){
            int size = q.size();
            
            while(size-- >0) {
                
                int[] pair = q.removeFirst();
                int vtx = pair[0];
                int color = pair[1];
            
                if(vis[vtx]!=-1){
                    if(vis[vtx] !=color){
                        return false;    
                    }
                     continue;
                }  
             
                vis[vtx] = color;
            
                for(int nbr : graph[vtx])
                {
                    if(vis[nbr]==-1){
                        q.addLast(new int[]{nbr, (color+1)%2});
                    }
                }
        }
            
        }
        return true;
    }


//3 ==================================================================================================================
//https://leetcode.com/problems/bus-routes/

 public int numBusesToDestination(int[][] routes, int S, int T) {
        
        int number_of_Buses = routes.length;
        
        HashMap<Integer, ArrayList<Integer>> busses_to_routed = new HashMap<>();
        
        for(int bus=0; bus< routes.length;bus++){
            for(int route_of_this_bus: routes[bus]){
                busses_to_routed.putIfAbsent(route_of_this_bus, new ArrayList<Integer>());
                //yaha us roye se jana wala bus arrayalist mein add kar rahe hai
                busses_to_routed.get(route_of_this_bus).add(bus);
            }
        }
        
        
        LinkedList<Integer> bus_stop_queue = new LinkedList<>();
        HashSet<Integer> is_bus_stop_visited = new HashSet<>();
        boolean[] is_bus_used_earlier = new boolean[number_of_Buses];
        
        
        bus_stop_queue.add(S);
        int level = 0;
        
        while(bus_stop_queue.size()!=0){
            
            int size = bus_stop_queue.size();
            while(size-- >0){
                
                int bus_stop = bus_stop_queue.removeFirst();
                
                if(bus_stop == T) return level;
                
                //kaun kaun si bus jati hai is bus stop se
                for(int bus_from_bus_stop : busses_to_routed.get(bus_stop)){
                    
                    //kya is bus mein pahle baith chuka hun
                    if(is_bus_used_earlier[bus_from_bus_stop]==true) continue;
                    
                    //is bus k saare route
                    for(int stops_of_this_bus: routes[bus_from_bus_stop]){
                        
                        //kya ye stop ko pahle nahi use kar chuka hun
                        if(!is_bus_stop_visited.contains(stops_of_this_bus)){
                            is_bus_stop_visited.add(stops_of_this_bus);
                            bus_stop_queue.addLast(stops_of_this_bus);
                        }
                        
                    }
                    
                    is_bus_used_earlier[bus_from_bus_stop] = true;
                    
                }
                
                
            }
            level++;
        }
        
        
        return -1;
    }

//4 ==================================================================================================================
//https://practice.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1


    static ArrayList<Integer> dfs(ArrayList<ArrayList<Integer>> g, int N)
    {
       int num_of_nodes = N;
       
       boolean is_vis[] = new boolean[num_of_nodes];
       ArrayList<Integer> ans= new ArrayList<>();
       
       for(int i=0;i<N;i++){
           if(!is_vis[i]){
               dfs_(g,i,is_vis,ans);
           }
       }
       return ans;
    }
    
    
    static void dfs_(ArrayList<ArrayList<Integer>> g,int sr,boolean is_vis[], ArrayList<Integer> ans){
        is_vis[sr] = true;
        ans.add(sr);
        for(int nbr : g.get(sr)){
            if(!is_vis[nbr]){
                dfs_(g, nbr,is_vis,ans);
            }
        }
        
    }



//5 =========================PRISM's=========================================================================================
//https://www.spoj.com/problems/MST/
// Minium Spanning tree:
// 1 N node hone -> n-1 edges hone chahye
//suppose 4 node hai, and edge 5 hai 2 max wala edge remove hoga tree k liye
//VlogE

public static class primsPair{
    int par = 0;
    int vtx = 0;
    int w = 0;

    primsPair(int vtx,int par,int w){
        this.par = par;
        this.vtx = vtx;
        this.w = w;
    }
}

//Prism : Here we have to construct a new graph from the main graph, hence we have to keep an extra variable par in primsPair, so that this can be used in attaching a vertes and make it its parent

public static void prismAlgo(ArrayList<Edge>[] original_graph, int N){

    //create a new graph which can be used as MST
    ArrayList<Edge>[] new_mst_graph = new ArrayList[N];
    for(int i=0;i<N;i++){
        new_mst_graph[i] = new ArrayList<>();
    }

    boolean vis[] = new boolean[N];

    //min pq
    PriorityQueue<primsPair> pq = new PriorityQueue<>((a,b)->{
        return a.w-b.w;
    });

    //first time a vtx(0) is alone and hence its parent is -1 and weight = 0
    pq.add(new primsPair(0,-1,0));

    int EdgesCount = 0;

    while(EdgesCount <= N-1){ //prism basic consditio

        primsPair pair = pq.remove();

        //already visited
        if(vis[pair.vtx]) continue;

        if(pair.par!=-1){

            //add edge fronm vtx to par
            addEdge(new_mst_graph, pair.par, pair.vtx, pair.weight );
            EdgesCount++
        }
        
        vis[pair.vtx] = true;

        for(Edge e: graph[pair.vtx]){
            if(!vis[e.v]){
                pq.add(new primsPair(e.v, pair.vtx,e.w));
            }
        }

    }
}


public static prismAlgo_optimize(ArrayList<Edge>[] original_graph, int N){

    //create a new graph which can be used as MST
    ArrayList<Edge>[] new_mst_graph = new ArrayList<Edge>[N];
    for(int i=0;i<N;i++){
        new_mst_graph[i] = new ArrayList<>();
    }

    ///////////////////////
    //ek storage goga distance ka vtx k accoridin
    int[] dis = new int[N];

    boolean vis[] = new boolean[N];

    //min pq
    PriorityQueue<primsPair> pq = new PriorityQueue<>((a,b)->{
        return a.w-b.w;
    });

    //first time a vtx(0) is alone and hence its parent is -1 and weight = 0
    pq.add(new primsPair(0,-1,0));
    /////////
    dis[0] = 0;
    int EdgesCount = 0;

    while(EdgesCount <= N-1){ //prism basic consditio

        primsPair pair = pq.remove();

        //already visited
        if(vis[pair.vtx]) continue;

        if(pair.par!=-1){

            //add edge fronm vtx to par
            addEdge(new_mst_graph, pair.par, pair.vtx, pair.weight );
            EdgesCount++
        }
        
        vis[pair.vtx] = true;

        for(Edge e: graph[pair.vtx]){
            //pahle se dis mein e.v ka value hai and agar wo large hai naye wale value se tabhi queu mein daalenge
            if(dis[e.v] > e.w  && !vis[e.v]){
                //distance ko small value se update karenge
                dis[e.v] = e.w;
                pq.add(new primsPair(e.v, pair.vtx,e.w));
            }
        }

    }
}

//conclusion:
//1 prism me graph banaana jaruri hai
// 2 undirected graph only

//6 ==============================================Dijkstra====================================================================

//Shortes path Algo
//1 dijkastra
//Ellog(V)

//2. bellman ford : Dynamic programming, both positive and negative edge
 //O(EV)

//3 floyd warshal : return 2D matrix, where each cell i,j return 1 se j tak shortet path
// RETURN ALL PATH, BOTH POSITIVE AND NEGATICE VERTEX
 //O(V3)


//4 johnson : DO SAME AS floyd warshal BUT complexity ls less
 //O(VlogV)

// # dijkastra : 1 src se har vtx tak ka shortes path store kar k array mein rakhta hai
// greedy, fast, postive edge weight

public static class dijPair{
    int vtx;
    int wsf;
    int par;
    public dijPair(int vtx,int wsf,int par){
      this.vtx=vtx;
      this.wsf=wsf;
      this.par=par;
    }

  }

public static void Dijkastra(int src,int N,ArrayList<Edge>[] graph){

    ArrayList<Edge>[] dijiGraph = new ArrayList<>[];
    for(int i=0;i<N;i++){
        dijiGraph[i] = new ArrayList<>();
    }

    boolean[] vis = new boolean[N];
    int[] costArray = new int[N];
    int[] dis = new int[N];

    Arrays.fill(costArray,(int)1e9);
    
    //Min Priority Queue
    PriorityQueue<dijPair> q = new PriorityQueue<dijPair>((a,b)->{
      return a.wsf-b.wsf;
    });

    q.add(new dijPair(s,0,-1));
    int EdgesCount = 1;
    costArray[s] = 0;
    while(EdgesCount<=n-1){
      dijPair dpai = q.remove();

      if(vis[dpai.vtx]) continue;

      if(dpai.par != -1){
        EdgesCount++;
      }

      vis[dpai.vtx] = true;
      par[dpai.vtx] = dpai.par;

      for(Edge e: graph[dpai.vtx]){
        if(!vis[e.v] && dpai.wsf  + e.w < costArray[e.v]){
          //dis[e.v] = dpai.wsf  + e.w;
          costArray[e.v] = dpai.wsf + e.w;
          q.add(new dijPair(e.v,dpai.wsf  + e.w, dpai.vtx ));
        }
      }

    }
}

//8 ==============================================codechef====================================================================

//https://www.codechef.com/submit/REVERSE

/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{   
  public static class Edges {

    int v = 0;
    int w = 0;

    public Edges(int v, int w) {
      this.v = v;
      this.w = w;
    }
  }

  public static void addEdge(int u, int v, int w) {
    graph[u].add(new Edges(v, w));
  }

  public static class dijPair {

    int vtx;
    int wsf;
    int par;

    public dijPair(int vtx, int wsf, int par) {
      this.vtx = vtx;
      this.wsf = wsf;
      this.par = par;
    }

  }

  static ArrayList<Edges>[] graph;

  public static void main(String[] args) throws java.lang.Exception {
    Scanner sc = new Scanner(System.in);
    int vertex = sc.nextInt();
    int edges = sc.nextInt();
    graph = new ArrayList[vertex+1];

    for (int i = 0; i <= vertex; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int i = 0; i < vertex; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      graph[u].add(new Edges(v, 0));//  = new ArrayList<>();
      graph[v].add(new Edges(u, 1));
    }

    boolean[] vis = new boolean[vertex + 1];
    int[] costArray = new int[vertex + 1];
    int[] par = new int[vertex + 1];
    int[] dis = new int[vertex + 1];

    Arrays.fill(costArray, (int) 1e9);
    Arrays.fill(par, -1);

    //Min Priority Queue
    PriorityQueue<dijPair> q = new PriorityQueue<dijPair>((a, b) -> {
      return a.wsf - b.wsf;
    });

    q.add(new dijPair(1, 0, -1));
    int EdgesCount = 1;
    costArray[1] = 0;
    
    while (EdgesCount <= vertex - 1) {
      dijPair dpai = q.remove();

      if (vis[dpai.vtx])
        continue;

      if (dpai.par != -1) {
        EdgesCount++;
      }

      vis[dpai.vtx] = true;
      par[dpai.vtx] = dpai.par;

      for (Edges e : graph[dpai.vtx]) {
        if (!vis[e.v] && dpai.wsf + e.w < costArray[e.v]) {
          //dis[e.v] = dpai.wsf  + e.w;
          costArray[e.v] = dpai.wsf + e.w;
          q.add(new dijPair(e.v, dpai.wsf + e.w, dpai.vtx));
        }
      }


    }
    System.out.println((costArray[vertex] == (int) 1e9) ? -1 : costArray[vertex]);
  }
}  


//9 ==============================================codechef====================================================================
//https://www.geeksforgeeks.org/minimum-cost-connect-cities/

import java.util.*;
import java.lang.*;

public class MinimumSpanningTree {

  public static class Edge {

    int v = 0;
    int w = 0;

    public Edge(int v, int w) {
      this.v = v;
      this.w = w;
    }
  }
  public static class primsPair{
    int par = 0;
    int vtx = 0;
    int w = 0;

    primsPair(int vtx,int par,int w){
      this.par = par;
      this.vtx = vtx;
      this.w = w;
    }
  }

  static ArrayList<Edge>[] new_mst_graph;
  static  ArrayList<Edge>[] graph;
  public static void addEdge(ArrayList<Edge>[] g,int u, int v, int w) {
    g[u].add(new Edge(v, w));
  }

  public static void prismAlgo(ArrayList<Edge>[] original_graph, int N){

    //create a new graph which can be used as MST
    new_mst_graph = new ArrayList[N];
    for(int i=0;i<N;i++){
      new_mst_graph[i] = new ArrayList<>();
    }

    boolean vis[] = new boolean[N];
    int[] dis = new int[N];
    Arrays.fill(dis,(int)1e8);
    //min pq
    PriorityQueue<primsPair> pq = new PriorityQueue<>((a,b)->{
      return a.w-b.w;
    });

    //first time a vtx(0) is alone and hence its parent is -1 and weight = 0
    pq.add(new primsPair(0,-1,0));

    dis[0] = 0;
    int EdgesCount = 1;

    while(EdgesCount <= N-1){ //prism basic consditio

      primsPair pair = pq.remove();

      //already visited
      if(vis[pair.vtx]) continue;

      if(pair.par!=-1){
        //add edge fronm vtx to par
        addEdge(new_mst_graph,pair.par, pair.vtx, pair.w );
        EdgesCount++;
      }

      vis[pair.vtx] = true;

      for(Edge e: graph[pair.vtx]){
        if(!vis[e.v] && e.w < dis[e.v]){
          dis[e.v] = e.w;
          pq.add(new primsPair(e.v, pair.vtx,e.w));
        }
      }


    }
    int sum = 0;
    for(int i=0;i<dis.length;i++){
      sum+= dis[i];
    }
    System.out.println(sum);
  }



  public static void main(String[] args) {
    int n1 = 6;
    int city1[][] = { { 0, 1, 2, 3, 4 },
        { 1, 0, 5, 0, 7 },
        { 2, 5, 0, 6, 0 },
        { 3, 0, 6, 0, 0 },
        { 4, 7, 0, 0, 0 } };
    int city2[][] = {{0, 1, 1, 100, 0, 0},
        {1, 0, 1, 0, 0, 0},
        {1, 1, 0, 0, 0, 0},
        {100, 0, 0, 0, 2, 2},
        {0, 0, 0, 2, 0, 2},
        {0, 0, 0, 2, 2, 0}};;

    graph = new ArrayList[n1];

    for(int i= 0; i< n1;i++) graph[i] = new ArrayList<>();

    for(int i=0;i<n1;i++){
      for(int j=0;j<n1;j++){
        if(city2[i][j]!=0){
          addEdge(graph,i,j,city2[i][j]);
        }
      }
    }

    prismAlgo(graph, n1);

  }
}


//10 ==============================================Leetcode 1168====================================================================

//LeetCode: Optimize Water Distribution in a Village
//https://code.dennyzhang.com/optimize-water-distribution-in-a-village



//isme ek virtual node banayenge 0 and us se sabhi house ko connect kardent 0 se housue[i] k edge weight waha pe will k cost k barabr hoga (ek tarah se 0 se house[i] tak virtual pipe man lo will house[i] k jagah pe)
// aur hosuse k bich k pipe k cost bhi graph mein add kar do
// ab virtul node k src maan k prism laga dena hai


import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

//There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
//
//    For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes, where each pipes[i] = [house1, house2, cost] represents the cost to connect house1 and house2 together using a pipe. Connections are bidirectional.
//
//    Find the minimum total cost to supply water to all houses.
//
//    Example 1:
//    Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
//    Output: 3
//    Explanation:
//    The image shows the costs of connecting houses using pipes.
//    The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
//
//    Constraints:
//    1 <= n <= 10000
//    wells.length == n
//    0 <= wells[i] <= 10^5
//    1 <= pipes.length <= 10000
//    1 <= pipes[i][0], pipes[i][1] <= n
//    0 <= pipes[i][2] <= 10^5
//    pipes[i][0] != pipes[i][1]

public class MinCostToSupplyWaterPrism {

  public static class Edge {

    int v = 0;
    int w = 0;

    public Edge(int v, int w) {
      this.v = v;
      this.w = w;
    }
  }
  public static class pair{
    int par = 0;
    int vtx = 0;
    int w = 0;

    pair(int vtx,int par,int w){
      this.par = par;
      this.vtx = vtx;
      this.w = w;
    }
  }

  static ArrayList<Edge>[] new_mst_graph;
  static  ArrayList<Edge>[] graph;
  public static void addEdge(ArrayList<Edge>[] g,int u, int v, int w) {
    g[u].add(new Edge(v, w));
  }


  public static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {

    // pahle graph banaya
    graph = new ArrayList[n];
    // mst k liye
    new_mst_graph = new ArrayList[n];

    for(int i=0;i<n;i++){
      graph[i] = new ArrayList<>();
      new_mst_graph[i] = new ArrayList<>();
    }

    // vitual house o se saare house k, house 1 se diya hai is liye i+1 hai
    for(int i=0;i<n;i++){
      addEdge(graph, 0,i+1,wells[i]);
    }

    // baki house k bich k pipe ka cost
    for(int[] e: pipes){
      addEdge(graph, e[0],e[1],e[2]);
    }

    boolean vis[] = new boolean[n+1];
     
    //min pq
    PriorityQueue<pair> pq = new PriorityQueue<>((a,b)->{
      return a.w-b.w;
    });

    pq.add(new pair(0,-1,0));

    int[] cost = new int[n+1];
    Arrays.fill(cost,(int)1e8);
    cost[0] = 0;
    
    int EdgesCount = 1;
    while(EdgesCount <= n-1){ //prism basic consdition

      pair pair = pq.remove();

      //already visited
      if(vis[pair.vtx]) continue;

      if(pair.par!=-1){
        //add edge fronm vtx to par
        addEdge(new_mst_graph,pair.par, pair.vtx, pair.w );
        EdgesCount++;
      }

      vis[pair.vtx] = true;

      for(Edge e: graph[pair.vtx]){
        if(!vis[e.v] && e.w < cost[e.v]){
          cost[e.v] = e.w;
          pq.add(new pair(e.v, pair.vtx,e.w));
        }
      }


    }

    int sum = 0;
    for(int i=0;i<cost.length;i++){
      sum+= cost[i];
    }

    return sum;


    //return -1;
  }
  public static void main(String[] args) {
    int n = 3;
    int[] wells = new int[]{1,2,2};
    int[][]pipes = { {1,2,1},{2,3,1}};

    System.out.println(minCostToSupplyWater(n,wells,pipes));
  }

}


//11 ==============================================Leetcode1168====================================================================
//https://leetcode.com/problems/evaluate-division/



class Solution {
    //[a / b = 2.0,] => {a -> {b - 2.0}} is the graph contains
    HashMap<String,HashMap<String,Double>> graph;
    
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        graph = new HashMap<>();
        for(int i=0;i<equations.size();i++){
                List<String> equation = equations.get(i); // ["a","b"], 
                if(!graph.containsKey(equation.get(0))){ // equation.get(0) == a
                    graph.put(equation.get(0), new HashMap<>()); // [a - [,]]
                }

                if(!graph.containsKey(equation.get(1))){ // equation.get(1) == b
                    graph.put(equation.get(1), new HashMap<>()); // [b - [,]]
                }

                graph.get(equation.get(0)).put(equation.get(1), values[i]); // [a - [b, 2.0]]
                graph.get(equation.get(1)).put(equation.get(0), 1.0/values[i]); // [b - [a, 1.0/2.0]]
        }
        
        
        // EQUATION MAP CREATED
        ArrayList<Double> ans = new ArrayList<>();
        for(int i=0;i<queries.size();i++){
             List<String> query = queries.get(i); //["a","c"] a/c

            if(!graph.containsKey(query.get(0))){ // [a - [b, 2.0]] yes it contains a as key
                ans.add(-1.0);
                continue;
            }
            
            HashSet<String> visited = new HashSet<>();
            double val = dfs(query.get(0), query.get(1), visited); // a/c => (a,c, visited)
            if(val>0){
                ans.add(val);

            }else{
                ans.add(-1.0);
            }
        }
        
        
        double[] db = new double[ans.size()];
        int k=0;
        for(double d: ans){
            db[k++] = d;
        }
        
        return db;
        
        
        
    }
    
    
    
    
    public  double dfs(String src, String dest, HashSet<String> visited) {//src =a and dest = c
        if(!graph.containsKey(src)) return 0;  // graph not contains a as key null/c

        if(graph.get(src).containsKey(dest)){ // [src - [c,3.4] ] 
            return graph.get(src).get(dest); // return 3.4
        }

        visited.add(src); /// marked

        for(String c: graph.get(src).keySet()){ // 
            if(!visited.contains(c)){
                double ans =  dfs(c, dest, visited);
                if(ans >0 ){
                    ans*= graph.get(src).get(c);
                    return ans;
                }
            }
        }
    return 0;
}
}

//12 ==============================================Leetcode1168====================================================================
// https://leetcode.com/problems/01-matrix/submissions/

class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        
        if(matrix.length==0  || matrix[0].length ==0  ) return matrix;
        
        int n = matrix.length;
        int m = matrix[0].length;
        
        int dir[][] = {
            {0,1},
            {1,0},
            {-1,0},
            {0,-1}
        };
        
        // 2  amtrix banega
        int vis[][] = new int[n][m];
        for(int[] arr: vis){
            Arrays.fill(arr,-1);
        }
        
        //ye Linked se faster hita hai, kynki ds array use hota hai
        ArrayDeque<int[]> q =new ArrayDeque<>();
        
        int countOnes = n*m;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrix[i][j] == 0){
                    // agar 0 hai to que mein adde karna hai and vis ko bhi 0 karna hai
                    q.addLast(new int[]{i,j});
                    vis[i][j] = matrix[i][j];
                    countOnes--;
                }
            }
        }
        
        
        while(q.size()!=0){
            int rvtx[] = q.removeFirst();
            int xVtx = rvtx[0];
            int yVtx = rvtx[1];
            
            for(int i=0;i<dir.length;i++){ // rvtx (0) se 4 direction mein jana hai
                int row = xVtx + dir[i][0];
                int col = yVtx + dir[i][1];
                                                            //wo pahle se visited ni hina chahiye or matrix mein pos 1 hone chahiye 0 wale saare already vis mein daal chuke hai
                if(row >=0 && row < n && col>=0 && col <m && vis[row][col] == -1 && matrix[row][col] == 1){
                    q.addLast(new int[]{row,col});
                    vis[row][col] = 1 + vis[xVtx][yVtx];
                    countOnes--;
                    
                }
                
            }
            
            if (countOnes == 0) break;
        }
        
        
        
        return vis;
    }
}

//13 ==============================================Topological sort====================================================================
//https://practice.geeksforgeeks.org/problems/topological-sort/1
lass TopologicalSort {
     
    public static void dfs_topo(int src, boolean[] vis, ArrayList<Integer> st,
        ArrayList<ArrayList<Integer>> adj) {
        vis[src] = true;
        ArrayList<Integer> e = adj.get(src);
        for (int ev : e) {
    
          if (!vis[ev])
            dfs_topo(ev, vis, st, adj);
        }

        st.add(src);
  }
  
  static int[] topoSort(ArrayList<ArrayList<Integer>> adj, int N) {
    int[] ans = new int[N];
    boolean isVisted[] = new boolean[N];
    ArrayList<Integer> st = new ArrayList<>();

    for (int i = 0; i < adj.size(); i++) {
      for (int j = 0; j < adj.get(i).size(); j++) {
        if (!isVisted[i]) {
          dfs_topo(i, isVisted, st, adj);
        }
      }

    }
    Collections.reverse(st);
    
    for (int i = 0; i < st.size(); i++) {
        //System.out.print(st.get(i) + " ");
      ans[i] = st.get(i);
    }
    
    
    return ans;
  }
  
  
}

//14 ==============================================KahnsAlgo====================================================================

 public static void KahnsAlgo(){

    ArrayDeque<Integer> q = new ArrayDeque<>();
    int [] indegree = new int[N];
    for(int i=0;i<N;i++){
        for(Edge e: graph[i]){
            indegree[e.v]++;
        }

    }

     for(int i=0;i<N;i++){
         if(indegree[i]==0){
             q.addLast(i);
         }
     }

     ArrayList<Integer> ans=new ArrayList<>();
     while(q.size()!=0){
         int rvtx=que.removeFirst();
         ans.add(rvtx);
         for(Edge e: graph[rvtx]){
               if(--indegree[e.v]==0) q.addLast(e.v);
         }
     }
      if(ans.size()!=N) ans.clear();
      for(int ele: ans){
            System.out.print(ele);
        }
    
 }


//15 ==============================================Leetcode 210. Course Schedule II====================================================================
//https://leetcode.com/problems/course-schedule-ii/

//  There are a total of n courses you have to take labelled from 0 to n - 1.
// Some courses may have prerequisites, for example, if prerequisites[i] = [ai, bi] this means you must take the course bi before the course ai.

// b - a


public int[] findOrder(int numCourses, int[][] prerequisites) {
    int N = numCourses;

    ArrayList<Integer>[] graph = new ArrayList[N];

    for(int i=0;i<N;i++) graph[i] = new ArrayList();

    int indegree[] = new int[N];
    for(int[] preq : prerequisites){
        int u = preq[1]; //yaha u and v opposite hai kyninki v is mandtory for u
        int v = preq[0];
        graph[u].add(v);

        //v jaruri hai so v se jata hai graph
        indegree[v]++;

    }

    ArrayDeque<Integer> q = new ArrayDeque<>();

    //jinki indegree 0 hai usko q mein daala
    for(int i=0;i<N;i++){
        if(indegree[i]==0){
            q.addLast(i);
        }
    }
    ArrayList<Integer> ans = new ArrayList();

    while(q.size()!=0){
        int rvtx = q.removeFirst();
        ans.add(rvtx);
        for(int e: graph[rvtx]){
            if(--indegree[e] == 0) q.addLast(e);
        }

    }

    if(ans.size()!=N) ans.clear(); // Important here

     int size = ans.size();
     int ANS[] = new int[size];

     for(int i=0;i<ans.size();i++){
         ANS[i] = ans.get(i);
     }

     return ANS;
}


//16 ==============================================GFG====================================================================
//https://practice.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1
// kosarajus : total connectec componet batane k kaam aata hai

//Based on strongly connected
// 2 DFS required
// 1 ArrayList mein path naam : add karenege saare path using dfs 1

//Now ek naya graph banayange jaha edges ko reverse kar diya jayega
// fir is graph pe path mein se ek ek node ko nikal k trave karene and ans++ karte jaynege
// last mein ans batayega ki kitne connected componenets hai


    public int kosaraju(ArrayList<ArrayList<Integer>> adj, int N)
    {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[N];
        for(int i=0;i<N;i++){
            if(visited[i]==false){
                 DFS_SSC(i,adj,path,visited);
            }
             
        }
        
        // Reverse graph.
        
         ArrayList<ArrayList<Integer>> ngraph = new ArrayList<>();
         for(int i=0;i<N;i++){
             ngraph.add(new ArrayList<>());
         }
         
         for(int i=0;i<adj.size();i++)
	       {
	           for(int x:adj.get(i))
	           {
	               ngraph.get(x).add(i);
	           }
	       }
         
          int ans=0;
	      visited=new boolean[N];
          
           for(int i=path.size()-1;i>=0;i--){
            if(!visited[path.get(i)]){
                ans++;
              //  DFS_SSC2(path.get(i),ngraph,vis);
               DFS2(path.get(i),ngraph,visited);
            }
        }
        return ans;
        
    }
    
    public static void DFS_SSC(int src,ArrayList<ArrayList<Integer>> list,ArrayList<Integer> path, boolean[]visited){
        visited[src]=true;
	    for(int e: list.get(src)){
	        if(visited[e] == false){
	            DFS_SSC(e,list,path,visited);
	        }
	    }
	    path.add(src);
    }
    
     private void DFS2(int vertex,ArrayList<ArrayList<Integer>> list,boolean[]visited)
	    {
	        visited[vertex]=true;
	        
	        for(int x:list.get(vertex))
	        {
	            if(visited[x]==false)
	            DFS2(x,list,visited);
	        }
      }
      
//17 ==============================================GFG====================================================================

//https://practice.geeksforgeeks.org/problems/mother-vertex/1 

//A Mother Vertex is a vertex through which we can reach all the other vertices of the Graph.

//isme moter vertext hai wo statck ke upar aayge chahe kahin se bhi dfs se start kare
// first dfs ka purpose hai mother vertex ko statck k top pe lana
// second dfs ka purpose hai ki us vertex se saare path ko travel kar paa rahe hai ya ni

class MotherVertex
{
    static int counter = 0;
    static int findMother(ArrayList<ArrayList<Integer>> g, int n)
    {
        LinkedList<Integer> st = new LinkedList<>();
        boolean[] visited=new boolean[n];
        //int counter = 0;
        for(int i=0;i<g.size();i++){
            
            if(g.get(i).size()>0 && visited[i]==false){
                DFS(i,g,visited,st);
            }
            
        }
        
        counter = 0;
        
        int top = st.getFirst();
        visited=new boolean[n];
        DFS(top,g,visited,st);
        
        if(n == counter){
            return top;
        }
        return -1;
        
    }
    
    static void DFS(int src,ArrayList<ArrayList<Integer>> g, boolean[]visited, LinkedList<Integer> st){
        visited[src] = true;
        counter++;
        for(int e: g.get(src)){
            if(!visited[e]){
                DFS(e,g,visited,st);
            }
        }
        st.addFirst(src);
    }
}


//18 ==============================================LC====================================================================
//https://leetcode.com/problems/number-of-enclaves/
//1020. Number of Enclaves

// 0 water 1 land
// ye batana hai ki kitne saare land hai jaha se bahar nikal ni sakte
// array k boundry pe jitne bhi 1 hai agar waha se dfs laga  k usko 0 kar de to bache hue anadar k 1 hi hi titoal land bachenge jaha se nikal ni sakte

 public int numEnclaves(int[][] A) {
    int n = A.length;
    int m = A[0].length;

    if (n == 0 || m == 0) return 0;

    //first col
    for (int i = 0; i < n; i++) {
      if (A[i][0] == 1) dfsNumEnclaves(i, 0, A);
    }

    //first row
    for (int i = 0; i < m; i++) {
      if (A[0][i] == 1) dfsNumEnclaves(0, i, A);
    }
  

      //Last col
      for (int i = 0; i < n; i++) {
        if (A[i][m - 1] == 1) dfsNumEnclaves(i, m-1, A);
      }


    //last row
    for (int i = 0; i < m; i++) {
      if (A[n - 1][i] == 1) dfsNumEnclaves(n-1, i, A);
    }


    int ans = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (A[i][j] == 1) ans++;
      }

    }

    return ans;

}

public void dfsNumEnclaves(int i, int j, int[][] A) {
  int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};

  A[i][j] = 0;

  for (int d = 0; d < dir.length; d++) {
    int x = i + dir[d][0];
    int y = j + dir[d][1];

    if (x >= 0 && x < A.length && y >= 0 && y < A[0].length && A[x][y] == 1) {
      dfsNumEnclaves(x, y, A);
    }
  }
}


//19 ==============================================LC====================================================================
//https://leetcode.com/problems/number-of-islands/

//isme total island batane hai ye pura array 4 taraf se water se surrounded hai
// so kahin pe bhi 1 mile and waha se dfs laga ke mark kar de 0
// so 1 dfs mein 1 land cover ho jayega

// har dfs pe 1 se count ko badha dena hai

public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        if(n ==0 || m==0) return 0;
        
        int count = 0;
        for(int i=0;i< n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == '1'){
                    count++;
                    dfs_mark_one_with_zero(grid, i,j);
                }
            }
        }
        
            
            return count;
    }
    
    public void dfs_mark_one_with_zero(char[][] grid, int i,int j){
        
        if(grid[i][j] == '0') return;
        
        //mark 
        grid[i][j] = '0';
        
        if(i-1>=0){
             dfs_mark_one_with_zero(grid, i-1,j);
        }
        if(i+1 < grid.length){
             dfs_mark_one_with_zero(grid, i+1,j);
        }
        if(j-1>=0){
             dfs_mark_one_with_zero(grid, i,j-1);
        }
        if(j+1< grid[0].length){
             dfs_mark_one_with_zero(grid, i,j+1);
        }
        
        
    }



//20 ==============================================LC====================================================================

//https://leetcode.com/problems/rotting-oranges/

//ye batana hai ki kitni der mein saare orange rotten ho jayega
// so que mein hum rotten tomatao ka location daal denge
//fir q mein se har rotten ko nikalenge and unke neighbou pe fresh hai to usk rotten kar denge

    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = {{0,1},{1,0},{0,-1}, {-1,0}};
        
        int freshOrange = 0;
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        for(int i = 0; i< n;i++){
            for(int j=0;j< m ;j++){
                if(grid[i][j] == 1) freshOrange++;
                else  if(grid[i][j] == 2) q.addLast(new int[]{i,j});    
            }
        }
        
        if(freshOrange == 0) return 0;
        
        int time = 0;
        
        while(q.size()!=0){
            int size = q.size();
                while(size-->0){
                    int[] rvtx = q.removeFirst();
                    int rx = rvtx[0];
                    int ry = rvtx[1];

                    for(int i=0;i<dir.length;i++){
                        int x = rx + dir[i][0];
                        int y = ry + dir[i][1];

                        if(x>=0 && x<n && y >=0 && y<m && grid[x][y]==1){
                            grid[x][y]=2; // make it rotten
                            freshOrange--;
                            q.addLast(new int[]{x,y}); 
                            if(freshOrange == 0 ) return time + 1;
                        }
                    }
                    
                } 
             time++;
            }
       return -1;
    }

//21 ==================================================================================================================

//Bellman–Ford Algorithm
//it is dynamic programming
//Ye negative edge weight ki solve karta hai
//only edge is require [1,2,3] 1 se 2 k edge hai jiska weigh 3 hai



import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
    Scanner s = new Scanner(System.in);
    int num_of_ip = s.nextInt();
    boolean isAnyUpdate = false;
    while(num_of_ip-- >0){
      boolean is_neg = false;
      int vertices =  s.nextInt();
      int egs =  s.nextInt();
      ArrayList<int[]> edges = new ArrayList<>();
      for(int i=0;i < egs;i++){
        edges.add(new int[]{s.nextInt(), s.nextInt(), s.nextInt()});
      }


      int[] prevdis = new int[vertices];
      int[] currDis = new int[vertices];
      Arrays.fill(prevdis,(int)1e9);
      prevdis[0] = 0;

      int vtx = 1;
      while(vtx <= vertices){
        for(int i=0;i<vertices;i++) currDis[i] = prevdis[i];
        isAnyUpdate = false;
        for(int[] e: edges){
          int u = e[0], v = e[1], w = e[2];
          if(prevdis[u] + w  < currDis[v]){
            currDis[v] = prevdis[u] + w;
            isAnyUpdate = true;
          }
        }

        if(!isAnyUpdate) break;
        if(isAnyUpdate && vtx == vertices){
          is_neg = true;
          //System.out.println("Negative Cycle");
        }

        prevdis = currDis;
        vtx++;
      }

      if(is_neg){
        System.out.println("1");
      }else{
        System.out.println("0");
      }
   }
}
}



//22 ==================================================LC================================================================
//https://leetcode.com/problems/word-ladder/
//127. Word Ladder

//pair class level and word from wordList hold karega

//isme grah kuchh is tarha baneg wordList = ["hot","dot","dog","lot","log","cog"]

//h*t -> hot, 
//d*t -> dot
//d*g -> dog, log, cog
//l*t -> lot 
//l*g --> log, 
//cog -> cog

// visited mein  ["hot","dot","dog","lot","log","cog"] ye value aaynge

class pair{
        int level;
        String current;
        public pair(int l, String c){
            level =l;
            current = c;
        }
    }
    
    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
        int len = beginWord.length();
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        // graph would be d*g -> dog,dag,dig.....
        

        HashSet<String> visited = new HashSet<>();
        // ye visited ka substitute hai isme ye doc cat word mile hai ya ni ye batayega


        for(String X: wordList){
            for(int i=0;i<len; i++){
                            //for dog -->( d    + "*" +   g   )
                String temp = X.substring(0,i) + "*" + X.substring(i+1); 
                
                if(graph.containsKey(temp)){
                    graph.get(temp).add(X);
                    
                }else{
                    ArrayList<String> al = new ArrayList<>();
                    al.add(X);
                    graph.put(temp, al);
                }
            }
        }

        
        
        //String map created
        
        ArrayDeque<pair> q = new ArrayDeque<>();
        q.add(new pair(1,beginWord)); // 1 dog
        
        while(q.size()!=0){
            
            pair p = q.removeFirst(); //dog
            
            for(int i=0;i<len;i++){
                String temp = p.current.substring(0,i) + "*"+ p.current.substring(i+1); // d*g
                
                if(graph.containsKey(temp)){ // graph.containsKey(d*g))
                    for(String str: graph.get(temp)){   //d*g -> dog, log, cog
                        if(str.equals(endWord)) { // if dog
                            return p.level + 1;
                        }else if(!visited.contains(str)){ // visted.coonatins(dog)
                            visited.add(str);
                            q.addLast(new pair(p.level + 1, str));
                        }
                    }
                }
                
                
            }
            
            
        }
        return 0;
    }


//23 ==================================================LC================================================================


https://leetcode.com/problems/sliding-puzzle/
/*

             0 1 2  <- array idx
            [1,2,3]  ==> 1 which is at 0th pos can goto 1 pos and 3 pos
            [4,5,0]  ==> 5 which is at 4th pos can goto 1 pos and 3 pos and 5th pos
             3 4 5   <- array idx
        
        int[][] dirs = {
                 {1,3}, //0-> 1 and 3 idx
                 {0,2,4},  // 1 -> 0,2,4 idx 
                 { 1, 5},
                 { 0, 4},
                 { 1, 3, 5},
                 { 2, 4}
             }
        
      

*/

//Yaha 0th position ko swap karna hai har jagah to make 12345(0) 0 ka matlab vacan position hai


public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = ""; //123405
        for(int i=0;i< board.length;i++){
            for(int j=0;j< board[0].length;j++){
                start+=board[i][j];
            }
        }
        
        
        HashSet<String> visited = new HashSet<>();
         /*  0 1 2  <- array idx
            [1,2,3]  ==> 1 which is at 0th pos can goto 1 pos and 3 pos
            [4,5,0]  ==> 5 which is at 4th pos can goto 1 pos and 3 pos and 5th pos
             3 4 5   <- array idx
        
        int[][] dirs = {
                 {1,3}, //0-> 1 and 3 idx
                 {0,2,4},  // 1 -> 0,2,4 idx 
                 { 1, 5},
                 { 0, 4},
                 { 1, 3, 5},
                 { 2, 4}
             }
        
      */  
        
        int[][] dirs = {
                 { 1, 3},
                 { 0, 2, 4},
                 { 1, 5},
                 { 0, 4},
                 { 1, 3, 5},
                 { 2, 4}
             };
        
        
        ArrayDeque<String> q= new ArrayDeque<>();
        q.addLast(start);
        visited.add(start);
        
        int res = 0;
        
        while(q.size()!=0){
            int size = q.size();
            
            while(size-- >0){
                String current = q.removeFirst();
                if(target.equals(current)){
                    return res;
                }
                
                int zero = current.indexOf('0');
                for(int dir : dirs[zero]){
                    String next = swap(current, zero,dir);
                    if(!visited.contains(next)){
                        visited.add(next);
                        q.addLast(next);
                    }
                }
            }
            
            res++;
            
        }
        
        return -1;
        
        
    }


    //String swap
    
     private String swap(String str, int i, int j) {
         StringBuilder sb = new StringBuilder(str);
         sb.setCharAt(i, str.charAt(j));
         sb.setCharAt(j, str.charAt(i));
          return sb.toString();
     }




//24 ==================================================LC================================================================

/*
Number of Distinct Islands

Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical). You may assume all four edges of the grid are surrounded by water.

Count the number of distinct islands. An island is considered to be the same as another if and only if one island has the same shape as another island (and not rotated or reflected).

Notice that:

11
1
and

 1
11
are considered different island, because we do not consider reflection / rotation.

Example
Example 1:

Input: 
  [
    [1,1,0,0,1],
    [1,0,0,0,0],
    [1,1,0,0,1],
    [0,1,0,1,1]
  ]
Output: 3
Explanation:
  11   1    1
  1        11   
  11
   1
Example 2:

Input:
  [
    [1,1,0,0,0],
    [1,1,0,0,0],
    [0,0,0,1,1],
    [0,0,0,1,1]
  ]


*/


//https://www.lintcode.com/problem/number-of-distinct-islands/my-submissions


/*
  o->R->R  after thsi we have to backtrac and hece we add a string 'b in slast'  o->R->R->b->D-D
    1 1 1
      1
      1

   o->R->R->D->D  Here no backtarck is required   
    1 1 1
        1
        1     

   If back track was not there than both will give same answer o-R-R-D-D      
  */





public int numberofDistinctIslands(int[][] grid) {
       
       int n = grid.length;
       int m = grid[0].length;
       if(n==0 || m==0 ) return 0;
       
       HashSet<String> visited = new HashSet<>();
       for(int i=0;i<n;i++){
           
           for(int j=0;j<m;j++){
               if(grid[i][j]!=0){
                   StringBuilder sb = new StringBuilder();
                   dfs(grid, i , j, visited, sb, "o");
                    grid[i][j] = 0;
                    visited.add(sb.toString());
                    System.out.println(sb.toString());
               }
           }
       }
        return visited.size();
    }
    
    public void dfs(int[][] grid,int i ,int j,HashSet<String> visited, StringBuilder sb, String dir){
        
         if(i < 0 || i == grid.length || j < 0 || j == grid[i].length 
	       || grid[i][j] == 0) return;
	       
	    sb.append(dir);    
        grid[i][j] = 0;
        dfs(grid, i-1 , j, visited, sb,"u");
        dfs(grid, i+1 , j, visited, sb,"d");
        dfs(grid, i , j-1, visited, sb,"l");
        dfs(grid, i , j+1, visited, sb,"r");
        sb.append('b');
        
    }

/*
  o->R->R  after thsi we have to backtrac and hece we add a string 'b in slast'  o->R->R->b->D-D
    1 1 1
      1
      1

   o->R->R->D->D  Here no backtarck is required   
    1 1 1
        1
        1     

   If back track was not there than both will give same answer o-R-R-D-D      
  */      



//25 ==================================================DSU================================================================

//Disjoint set union

//find(x) : ye leader of x ko return kar dega 
//union (x,y) : ye do leader ko merge karta hai usinf find

int find(int x){
  if(parent[x] == x) return x;

  int temp = find(parent[x]);
  return temp;
}


void union(int x, int x){
  int parx = find(x);
  int pary = find(y);

  if(parx!=pary){
    parent[parx] = pary;
  }
            7
          6
        5
      4
    3
  2
 // O(N) yaha 7 leader 2 3 4 5 6

//Path compression

            7

      2  3  4  5  6

      //Olog(N)
int find(int x){
  if(parent[x] == x) return x;

  int temp = find(parent[x]);
  parent[x] = temp; //yaha x ka prent 7 bana diya
  return temp;
}

//Union by rank

//depth ka basis pe add karte hai
//smaller depth walo ko larger depth mein add karna hai

par []  = {1,2,3,4,5};
rank []  = {1,1,1,1,1}


smaller rank will point bigger rank

void union(int x, int x){
  int parx = find(x);
  int pary = find(y);

  if(parx!=pary){
   if(rank[parx] > rank[pary]){
          parent[pary] = parx;  
   }else if (rank[parx] < rank[pary]){
     parent[parx] = pary;  

   }else {
      parent[parx] = pary;
      rank[pary]++;  

   }
  }

/*
  agar x ka parent y bana
  par[x] = y
  rank[y] = rank[y]+ 1
  */

}



//26 ==================================================LC================================================================

//https://www.lintcode.com/problem/number-of-islands-ii/description

/*
How to conver 2D array to 1D
2D
n = 2
m = 3
[0 1 2]
[3 4 5]

int 1D = new int[n*m];
[0,1,2,3,4,5]
from 2 d matrix let's picl 2
i=1
j=0

i,j = i*n+j = 1*2+0 = 3 

So in 1D 3rd locaktion will be the value i,j from 2d
[0,1,2,(3),4,5]

/*

/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */

public class Solution {
   
     
    public int find(int i,int[]parent)
	    {
	        if (parent[i] != i && parent[i]!=-1) 
	            parent[i] = find(parent[i],parent);
	      return parent[i];
	    }
	    
	    public void Union(int x, int y,int[]rank,int[]parent) { // union with rank
	      int rootx = find(x,parent);
	      int rooty = find(y,parent);
	        if(rootx==-1)
	        {
	            parent[x]=rooty;
	        }
	        else if(rooty==-1)
	        {
	            parent[y]=rootx;
	        }
	     else if (rootx != rooty) {
	        if (rank[rootx] > rank[rooty]) {
	          parent[rooty] = rootx;
	        } else if (rank[rootx] < rank[rooty]) {
	          parent[rootx] = rooty;
	        } else {
	          parent[rooty] = rootx; rank[rootx] += 1;
	        }
	      } 
	    }
	    
	    
	    
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        if(n==1 ||m ==1) return new ArrayList();
        
        
        int grid[][] = new int[n][m];
        List<Integer> ans =new ArrayList<>();
        int number=0;
        
      int[]parent=new int[m*n];
	    int[]rank=new int[m*n];
	    Arrays.fill(parent,-1);
	    
	    for(Point p : operators){
	        int x = p.x;
	        int y = p.y;
	        
	        if(grid[x][y]==0){
	            grid[x][y]=1;
	        }
	        
	        else{
	             ans.add(number);
	             continue;
	        }
	        
	       int num=x*(m)+y; 
        //Check whether my neighours are 1's or not
        //If yes, then union both of them, if they have different parents, decrease the 
        //number of islands by 1
        
        int[][]dirs={
            {1,0},
            {0,1},
            {-1,0},
            {0,-1}
        };
        
      int count=0;
	    for(int pdir[]:dirs){
	        int newx=x+pdir[0];
	        int newy=y+pdir[1];
	        if(newx>=0 && newx<grid.length && newy>=0 && newy<grid[0].length
	                  && grid[newx][newy]==1){
	                      int check=newx*(m)+newy;
	                      if(find(check,parent)!=find(num,parent)){
	                          Union(check,num,rank,parent);
	                          number--;
	                          count++;
	                      }
	                      
	                  }
	        
	    }
       if(count==0){
           parent[num]=num;
           rank[num]++;
           
       } 
        number++;
        ans.add(number);    
    }
	    
	    
	    return ans;
	    
	   }
}


==EASY Solution

public class Solution {
    /**
     * @param n: An integer
     * @param m: An integer
     * @param operators: an array of point
     * @return: an integer array
     */
     
    public int find(int i,int[]parent)
	    {
	         if(parent[i] == -1) {
	             return i;
	             
	         };
        return parent[i] = find(parent[i], parent);
	    }
	    
	    
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        if(n==1 ||m ==1) return new ArrayList();
        
        
        int grid[][] = new int[n][m];
        List<Integer>ans=new ArrayList<>();
        int islandCount = 0;
        int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};

        
        int[]parent=new int[m*n];
	    //int[]rank=new int[m*n];
	    Arrays.fill(parent,-1);
	    
	    for(Point p: operators){
	        int i = p.x;
	        int j = p.y;
	        
	        if(grid[i][j]==0){
	            int p1 = find(i*m+j, parent);
	            grid[i][j] = 1;
	            islandCount ++;
	            
	            for(int d = 0;d<4;d++){
	                int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    if(r >= 0 && c >= 0 && r < n && c < m && grid[r][c]==1){
                        int p2 = find(r*m+c, parent);
                        if(p1 != p2){
                            islandCount--;
                            parent[p2] = p1;
                        }
                    }
	            }
	            
	            
	        }
	        ans.add(islandCount);
	         
	        
	    }
	    return ans;
	    
	   }
}

//27 ==================================================LC================================================================

//https://www.lintcode.com/problem/sentence-similarity/description


//isme list of list diya gaya hai us se uniion bana hai


words1 = ["great","acting","skills"]
words2 = ["fine","drama","talent"]
pairs = [["great","fine"],["drama","acting"],["skills","talent"]]
HashMap<String, String> map = new HashMap<String, String>(); 

//Hash map mein <CHILD: PARENT store hoga>
//yaha pairs se union karenge

[great, great]              [great, fine]
                      ==> 
[fine, fine]                [fine, fine]

public String find(String str){
        map.putIfAbsent(str,str); // great ,great and fine fine
        //iska matlab str khud ko point ni kar raha hai iska matalab iska koi parent hai
        while(str!=map.get(str)){  // great ==great hoga so [great, great]  return hoga
            str = map.get(str);// parent ko dhundega
        }
        return map.get(str);
}


public void union(String s1, String s2){
        String firstParent = find(s1);  //[great, great] --> parent "great" hi hai 
        String secondParent = find(s2); //  and [fine, fine] --> parent "fine" hi hai
        if(!firstParent.equals(secondParent)){ // dono ka parent same ni hai 
            map.put(firstParent,secondParent); // kisi ek ka paren dusra ban jayega like "fine" ka parent "fine" and "great" ka parent bhi "fine"
        }
    }
   
  
[great, great]              [great, fine]
                      ==> 
[fine, fine]                [fine, fine]

==
HashMap<String, String> map = new HashMap<String, String>(); 
    public boolean isSentenceSimilarity(String[] words1, String[] words2, List<List<String>> pairs) {
        if(words1.length!= words2.length) return  false;
        
        for(List<String> pair: pairs){
            String s1 = pair.get(0);
            String s2 = pair.get(1);
            union(s1,s2);
        }
        
        for(int i=0;i<words1.length;i++){
            
            String onePar = find(words1[i]);
            String secondPar = find(words2[i]);
            if(!onePar.equals(secondPar)){
                return false;
            }
        }
        
        
        return true;
        
    }
    
    
    public void union(String s1, String s2){
        String firstParent = find(s1);
        String secondParent = find(s2);
        if(!firstParent.equals(secondParent)){
            map.put(firstParent,secondParent);
        }
    }
    
    
    public String find(String str){
        map.putIfAbsent(str,str);
        
        //iska matlab str khud ko point ni kar raha hai iska matalab iska koi parent hai
        while(str!=map.get(str)){
            str = map.get(str);// parent ko dhundega
        }
        
        
        return map.get(str);
    }


//28 ==================================================LC================================================================

//https://leetcode.com/problems/regions-cut-by-slashes/submissions/


     int par[]; 
    public int find(int p){
        if(par[p] == p) return p;
        return par[p] = find(par[p]);
    }
    
   
    
    
    public int regionsBySlashes(String[] grid) {
        if(grid.length==0) return 0;
        
        int len = grid.length;
        int m = len+1;
        par = new int[m*m];
        
        //Yaha i and j 1 se chalenege kynki boundry humara parent hoga
        // and par[] usko 0 value assign karenge rest ko i + j * m;
        for(int i = 1;i<len;i++){
            for(int j = 1;j<len;j++){
                int p = i + j * m;
                par[p] = p;
            }
        }
        
        
        // intput will be[_ _]  2 d array
       //                [_ _]
        
       // intput will be [\ /]  2 d array
       //                [_ _] 
       
       // intput will be [_ /]  2 d array
       //                [/ _]  
       
        //intput will be [\ _]  2 d array
       //                [_ \]  
       
        //Here the array is 2D but when we have to create the array for m+1
        // "\"" this is one in grid array but there are two cordinate for it par array is stroting the cordinate in 1 d
        
        int count = 1; // there is by defaul 1 region
        
        for(int i=0;i<len;i++){
            
            String s = grid[i]; //. "/" " " "\\"
            for(int j=0;j<s.length();j++){
                char ch = s.charAt(j);
                if(ch=='/'){
                    int p1 = find((i+1) + (j*m)); // lower index of [/]
                    int p2 = find(i + ((j+1)*m)); //upper index of [/]
                        
                    if(p1!=p2){
                        //leader hamesa boundry banega, p1 p2 mein se boundry jo hoga uska value par[] == 0 assign kiya gaya tha
                        par[p1] = Math.min(p1,p2);
                        par[p2] = Math.min(p1,p2);
                    }else{
                        count++;
                    }    
                    
                }
                else if(ch=='\\'){
                    int p1 = find(i + (j*m)); // lower index of [\]
                    int p2 = find((i+1) + ((j+1)*m)); //upper index of [\]
                        
                    if(p1!=p2){
                        par[p1] = Math.min(p1,p2);
                        par[p2] = Math.min(p1,p2);
                    }else{
                        count++;
                    }  
                }
            }           
            
        }
        
        return count;
        
    }

//29 ==================================================LC================================================================
//https://leetcode.com/problems/satisfiability-of-equality-equations/submissions/


     int[] par;
    public int find(int u){
       if(u == par[u]) return u;
        return par[u] = find(par[u]);
    }
    
    public boolean equationsPossible(String[] equations) {
        
        par = new int[26];
        for(int i=0;i<26;i++){
            par[i] = i;
        }
        
        //pahle equal walo ko ek group mein daalo
        for(String s: equations){ // a = = b
            if(s.charAt(1) == '='){
                par[find(s.charAt(0)-'a')] = find(s.charAt(3)-'a');
             }
        }
        

        //ab wapis se check karenege and agar ! sign wala mein dodo string ka parent same aata hai to false retturn karna hai
        for(String s: equations){
            if(s.charAt(1) == '!'){
                if(find(s.charAt(0)-'a')  == find(s.charAt(3)-'a')) {
                    return false;
                }
            }
        }
        
        return true;
        
    }

//30 ==================================================================================================================
//Kruskal
//isko use tab karenege jab edge diya gaya ho
// edge like [ [u,v,w], [u1,v1,w1]] is tarah diya gaya ho
// so is se hume ek MST banana hai ek dursa graph


int[] par;
public int find(int p){
  if(par[p] == p) return p;
  return par[p] = find(par[p]);
}


public void krushkal(int[][] edges, int N){
  ArrayList<Integer>[] mst= new ArrayList[N];
  for(int i=0;i<N;i++){
      mst[i] = new ArrayList();
  }

  Arrays.sort(edges, (a,b)->{
    return a[2]-b[2];  //based on weigth sore the arr in ascendiing order
  });

  par = new int[N];
  size=new int[N];
        
  for(int i=0;i<N;i++){
    par[i]=i;
    size[i]=1;    
  }


  for(int[] e: edges){
    int u = e[0];
    int v = e[1];
    int w = e[2]

    int p1 = find(u);
    int p2 = find(v);

    if(p1!=p2){
      par[p1] = p2; //merge
      //add eddge in mst
      addEdge(mst, u,v,w);
    }
  }



}


//31 ==================================================================================================================
//https://practice.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1
//Job Sequencing Problem 


    int[] par;
    public int find(int p){
      if(par[p] == p) return p;
      return par[p] = find(par[p]);
    }

    int[] JobScheduling(Job jobs[], int n){
      int max_deadline=Integer.MIN_VALUE; // ye sabse jyada dead line walue store karega
        for(int i=0; i<jobs.length; i++) {
            if(jobs[i].deadline>max_deadline) {
                max_deadline = jobs[i].deadline;
            }
        }
        
        par = new int[max_deadline + 1]; // 1 jyada kyni deadline 1 se start hoga 0 se ni

        for(int i=0; i<=max_deadline; i++) {
			    par[i]=i;
		    }
        
        //Sort based on profit
        Arrays.sort(jobs, (Job a, Job b)->{
          return b.profit - a.profit;  
        });

        int count = 0; 
        int num_of_jobs = 0;

        for(int i=0; i<jobs.length; i++) {
            int available_slots = find(jobs[i].deadline); // koi job karne ka deadline, aur ye decreasing order mein sorted hai so sabse pahle jyada time wala aayega
            
            if(available_slots > 0 ){ // iska matlbal hai finall sabka parent 0 banega, jab tak 0 ni banta process karte raho
              //Union  
              par[available_slots] = available_slots-1; // chhote wale deadline ko bade wale ka parent banaynge
                count +=jobs[i].profit;
                num_of_jobs++;
            }
            
        } 
        
        return new int[]{num_of_jobs, count};





    }

//31 ==================================================================================================================
//Eulerian Path : kis 1 vertex pe pen rakho and waha se saare edge travel  ho jaye without pen uthaye

//Eulerian Circuit : kis 1 vertex pe pen rakho and waha se saare edge travel  karte hue usi vertex pe aa jaye

//constarint 1 dfs


//Eulerian Path  : har node ki degree even ho || n-2 ki even and 2 odd ho

// yaha even ka matlab 1 aaya 1 gaya, and 2 odd ka matlab1 se and  sure se gaya


//Eulerian Circuit : har node ki degree even ho 


public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		int num_of_ip = s.nextInt();
		int graph[][];
		while(num_of_ip-- >0){
		    int matrix_size = s.nextInt();
		    graph = new int[matrix_size][matrix_size];
		    for(int i=0;i< matrix_size;i++){
		        for(int j=0;j< matrix_size;j++){
		            graph[i][j] = s.nextInt();
		        }
		        
		    }
		    	int ans = isEulerPath(graph)?1:0;
		
		        System.out.println(ans);
		}
		
	}
	
	private static boolean isEulerPath(int[][] arr) {
	    
	    if(arr.length == 1) return true;
	    
	    //check if any comapnent of graph is dissconect
	    
	    if(!notConnected(arr)) return false;
	    if(!isconnected(arr)) return false;
	    
	    
	    int odd = 0;
	     for(int i=0;i< arr.length;i++){
	            int count = 0;
		        for(int j=0;j< arr.length;j++){
		           if(arr[i][j]  == 1){
		               count ++;
		           }
		        }
		        
		        if(count%2!=0) odd++;
		    }
	    
	    
	    
	    if(odd==0 || odd == 2) return true;
	    
	    return false;
	    
	    
	    
	}
	
	public static boolean notConnected(int[][] graph) {
	    for(int i=0;i< graph.length;i++){
	        boolean flag = false;
		        for(int j=0;j< graph.length;j++){
		            if(graph[i][j] == 1){
		                flag= true;
		                break;
		            }
		        }
		        if (flag == false) {
		            return false;
		            
		        }
		    }
		    
		    return true;
	    
	}
	public static boolean isconnected(int[][] arr) {
	    HashSet<Integer> visited = new HashSet<>();
	    dfs(arr,visited,0);
	    if(visited.size() == arr.length){
	        return true;
	    }
	    return false;
	}
	public static void dfs(int[][] graph, HashSet<Integer> visited, int s) {
	    visited.add(s);
	    for(int i=0;i< graph.length;i++){
	        
	            if((graph[s][i] == 1) && (visited.contains(i) == false))
	                dfs(graph,visited, i);
	        
	        
	    }
	}

//32  ==================================================================================================================

  //Euler circuit
// vertex sare even hone chahiye

public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		int num_of_ip = s.nextInt();
		int graph[][];
		while(num_of_ip-- >0){
		    int matrix_size = s.nextInt();
		    graph = new int[matrix_size][matrix_size];
		    for(int i=0;i< matrix_size;i++){
		        for(int j=0;j< matrix_size;j++){
		            graph[i][j] = s.nextInt();
		        }
		        
		    }
		    	int ans = isEulerPath(graph)?1:0;
		
		        System.out.println(ans);
		}
		
	}
	
	private static boolean isEulerPath(int[][] arr) {
	    
	    if(arr.length == 1) return true;
	    
	    //check if any comapnent of graph is dissconect
	    
	    if(!notConnected(arr)) return false;
	    if(!isconnected(arr)) return false;
	    
	    
	    int odd = 0;
	     for(int i=0;i< arr.length;i++){
	            int count = 0;
		        for(int j=0;j< arr.length;j++){
		           if(arr[i][j]  == 1){
		               count ++;
		           }
		        }
		        
		        if(count%2!=0) odd++;
		    }
	    
	    
	    
	    if(odd==0) return true;  // Only ye saaare even hone chahiye
	    
	    return false;
	    
	    
	    
	}
	
	public static boolean notConnected(int[][] graph) {
	    for(int i=0;i< graph.length;i++){
	        boolean flag = false;
		        for(int j=0;j< graph.length;j++){
		            if(graph[i][j] == 1){
		                flag= true;
		                break;
		            }
		        }
		        if (flag == false) {
		            return false;
		            
		        }
		    }
		    
		    return true;
	    
	}
	public static boolean isconnected(int[][] arr) {
	    HashSet<Integer> visited = new HashSet<>();
	    dfs(arr,visited,0);
	    if(visited.size() == arr.length){
	        return true;
	    }
	    return false;
	}
	public static void dfs(int[][] graph, HashSet<Integer> visited, int s) {
	    visited.add(s);
	    for(int i=0;i< graph.length;i++){
	        
	            if((graph[s][i] == 1) && (visited.contains(i) == false))
	                dfs(graph,visited, i);
	        
	        
	    }
  }
  




//33  ======================================================LC============================================================
// https://leetcode.com/problems/redundant-connection/  

// Cycle detection with union find
//union find se cycele detetcion karne mein 1 fayda hai ki ye order bata dega kis edge k karna cyccel ban raha hai

    int par[];
    public int findPar(int p){
        if(par[p] == p) return p; //agar uska parent wo khud hai to usi ko returnkarna hoga
        
        return par[p] = findPar(par[p]);
        
    }
    public int[] findRedundantConnection(int[][] edges) {
        
        int N = edges.length;
        par = new int[N+1];   // 0 se start hota hai array and edge k index 1 se start hai
        
        for(int i=0;i<=N;i++){
            par[i] = i;
        }
        
        for(int[] edge: edges){
            int u = findPar(edge[0]);
            int v = findPar(edge[1]);
            if(u!=v){
                par[u] =v;
            }else{
                return edge;
            }
        }
        
        return new int[0];
        
    }

//34  ======================================================LC============================================================

//Tough  ======================================================LC============================================================

//https://leetcode.com/problems/redundant-connection-ii/

//Graph mein se 1 aise edge ko hatana hai ji se tree ban jaaye

//sime jo tree hai na uska ya to 2 parents, ya cycle ban raha hai, ya dono hai.......so all three cases we have to look after


class Solution {
    int[] par;
    public int findPar(int u){
       if(u == par[u]) return u;
        return par[u] = findPar(par[u]);
    }
    
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] ans1 = null;
        int[] ans2 = null;
        
        
        int[] actualParent = new int[n+1]; // vertex 1 ,2,3,4 se start ho rahe hai
        
        Arrays.fill(actualParent,-1);
        
        
        par=new int[n+1];
        
        for(int i=0;i<=n;i++) par[i] = i;
        
        //first part focus on 2 parebt 
        
//        M->A->B
//           ^  | 
//        E<-D<-C
            
        for(int i=0;i<n;i++){
            //[1,2] -> 1->2. [u,v] -> u->v  v ka parent u hai
            int u = edges[i][0];
            int v = edges[i][1];
            if(actualParent[v] > 0){  // current node has two parents, we need to delete one of them.
                //purani wali edge ans1
                ans1 = new int[]{actualParent[v], v}; //actualParent[A] = M {M,A}
                //new wali edge ans2
                ans2 = new int[]{u, v};
                
                 //delete the last edge which make 2 parentes   
                 edges[i][0] = -1;
                 edges[i][1] = -1;
                
            }
            actualParent[v] = u;
        }
        
        
        //yaha tak 1 parent hai har node ka
        //ab cycle ki baari
 //     M->A->B
//            | 
//      E<-D<-C

    // https://github.com/Seanforfun/Algorithm-and-Leetcode/blob/master/leetcode/685.%20Redundant%20Connection%20II.md  
    //isko smajhne k liye upar wala diagram samjhna hoga

        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            if(u < 0 || v < 0) continue; // we deleted and made it -1
            int p = findPar(u);
            int q = findPar(v);
            if(p == q){ // u & v create a cycle.
                return ans1 == null ? edge: ans1;
            }
            
            par[p] = q;
            
        }
        return ans2;
    }
}


//35  ==================================================================================================================

//Topological Sort

//indgree mein hum grph[i] k vertex se v vertext pe janne wale ki indgree rahega
//u->v
//indegree[v]++

public static void topologicalOrder(){
    int[] indegree= new int[N];

    for(int u=0;u<N;y++){
        for(int v:graph[u]) indegree[v]++;
    }

    ArrayDeque<Integer> que = new ArrayDeque<>();
    ArrayList<Integer> ans = new ArrayList<>();

    //yaha q mein unko add kareneg jinka indgree 0 hai, meins jo kisi pe dependent ni hai

   for(int i=0;i<N;i++) if(indegree[i]==0) que.addLast(i);

   while(q.size()!=0){
       int vtx = que.removeFirst();
       ans.add(vtx);
   }

   //agar indegree mein edge minus karne pe 0 hota hai to hi add karenge
   for(int e : graph[vtx]){
       if(--indegree[e]==0){
           que.add(e); 
       }
   }

   if(ans.size()!=N) System.out.println("Cycle: ");
   else System.out.println(ans);


}


//36  ==================================================================================================================
//854. K-Similar Strings


public int kSimilarity(String A, String B) {
        if(A.equals(B)) return 0;
        HashSet<String> used = new HashSet<>();
        ArrayDeque<String> q = new ArrayDeque<>();
        q.addLast(A); // which have to make A -> B
        
        int ans = 0;
        int start = 0;
        
        //ABDEG  ABMNO --> till AB
        while(A.charAt(start)==B.charAt(start)){
            start++;
        }
        while (!q.isEmpty()) {
            int n = q.size();
            for(int i=0;i<n;i++){
                String str = q.removeFirst();
                int j = start; // j jaha se same string tha usk aage set start hoga
                System.out.println(str);
                while (str.charAt(j) == B.charAt(j)) {
					j++;
				}
                
                //bcd
                //abd 
                //Abhbi tak : B : bcd and A: abd
                // B(0) !=A(0) tabhi niche k loop mein ja raha hun
                //so ab A k (0+1) se start karenge kynki 0 tak mila ni kuchh bhi
                //yaha 0 -j and 0+1 k samjh lo
                   
                for(int k = j+1; k< B.length();k++){
                    
                    if(str.charAt(k)==B.charAt(j)){
                        String newString = swap(str, k,j);
                        if(!used.contains(newString)){
                            if(newString.equals(B)) return ans +1;
                            q.addLast(newString);
                            used.add(newString);
                        }
                    }
                }
            }
            
            //yaha tak 1 step badh gaya hoga matching mein
            ans++;
            // 1 char match kar gaya hoga ab 1 char aage badhayenge 
            start++;
        }
        return ans;
        
    }
    
    
    public String swap(String st, int i, int j){
        StringBuilder sb = new StringBuilder(st);
        sb.setCharAt(i, st.charAt(j));
        sb.setCharAt(j, st.charAt(i));
        return sb.toString();
    }

//37  ==================================================================================================================
//https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/
















//38  ==================================================================================================================
//Articulation Points
//https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/

//aisa point jisk hatne se graph diifretn group mein tut jata hai

[discovey time, low]

//1->2->3->4
//discover time : agar t time pe milla to t+1 , t+2 pe milega dfs laga to

1->2->3->4
         |
4<-6<-8<-5
//agar 4-5 ko gata de denge to 2 ban jayega graph

low : apne se jyada powerful bande ka index akhega

        4
        |
        5
       / \ 
      6 - 7  

//yaha 7 tak pahuche n ka rata 4 5 6 7 and 4 5 7
//agar journey 4 5 6.....7 aisi hogi
// to 7 ka next 5 hoga jabki 5 already visted tha 4 5 6...7 5 mein
// iska matlab 7 k liye powerful 7 hai and hence 7 ka discovery time 5 ka low time hoga

// fir 6 ki pass aya back track karte hue [6,6] -> iska low jyada 5 [5,3] (6>3) so 6 update ho jayega (6,3) se

//backtrack me [d1,l1] [d2,l2] mein d1 and l2 compare hoga


//0->1
//parent[1]=0 // ye dfs mein update karenge

//discover[i] = kis number pe wo vertex dfs k time discover hua

//low post order mein update hoga
//low[]


static int[] low = new int[N];
static int[] discovery = new int[N];
static boolean[] AP = new boolean[N];
static boolean[] vis = new boolean[N];
static int time = 0;
static int rootCalls = 0;

 public static void APB(){
    int componentsCount = 0;
     for(int i=0;i<N;i++){
         if(!vis[i]){
              dfs_APB(i,-1);
               componentsCount++;
                if(rootCalls == 1) AP[i] = false;
                 rootCalls = 0;
         }
     }
 }



public static void dfs_APB(int src,int par){
    low[src] = discover[src] = time++;
    vis[src] = true;
    for(Edge e: graph[src]){
         if(!vis[e.v]){ //not visited
            f(par == -1) rootCalls++;
            dfs_APB(e.v,src)
            if(discover[low] <= low[e.v]) AP[src] = true;
            //bridge
            if(disc[src] < low[e.v]) System.out.println("AE : " + src + " - " + e.v);
            low[src] = Math.min(low[src], low[e.v]]);
         }
         else if(e.v != par){//already visited

            low[src] = Math.min(low[src], discover[e.v]);

         }
    }


}


//39  ==================================================================================================================

https://practice.geeksforgeeks.org/problems/doctor-strange/0#



/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
    
  static int[] low;
  static int[] discovery;
  static boolean[] AP;
  static boolean[] vis;
  static int time = 0;
  static int rootCalls = 0;
  static int ans = 0;
  static ArrayList<ArrayList<Integer>> graph;
  public static void main (String[] args) {
    Scanner scn = new Scanner(System.in);
    int t = scn.nextInt();
    while (t-- > 0) {
      int n = scn.nextInt();
      int m = scn.nextInt();
      low = new int[n+1];
      discovery = new int[n+1];
      AP = new boolean[n+1];
      vis = new boolean[n+1];

      graph = new ArrayList<>();
      for (int i = 0; i <=n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int i = 0; i <n; i++) {
        int v1 = scn.nextInt();
        int v2 = scn.nextInt();
        graph.get(v1).add(v2);
        graph.get(v2).add(v1);
      }



      for(int i=1;i<=n;i++){
        if(!vis[i]){
          dfs_APB(i,-1);
        }
      }
      System.out.println(ans);
    }

  }
  public static void dfs_APB(int src,int par){
    low[src] = discovery[src] = time++;
    vis[src] = true;

    for(Integer e: graph.get(src)){
      if(!vis[e]){ //not visited
        if(par == -1) rootCalls++;
        dfs_APB(e,src);
        if(discovery[e] <= low[e]) ans++;
        //bridge
        //if(disc[src] < low[e.v]) System.out.println("AE : " + src + " - " + e.v);
        low[src] = Math.min(low[src], low[e]);
      }
      else if(e != par){//already visited

        low[src] = Math.min(low[src], discovery[e]);

      }
    }
  }

}

//40  ==================================================================================================================
// https://leetcode.com/problems/critical-connections-in-a-network/submissions/

class Solution {

 
  private int time = 0; // current time of discovery
  public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
      ArrayList<Integer>[] graph = new ArrayList[n];
      for(int i=0;i<n;i++){
          graph[i] = new ArrayList<>();
      }
      for (List<Integer> con: connections) {
          int v1 = con.get(0);
          int v2 = con.get(1);
          graph[v1].add(v2);
          graph[v2].add(v1);
          
          }
      
      //AP
      
      int[] disc = new int[n]; // discovery time of each node
      int[] low = new int[n]; // earliest discovered node reachable from this node in DFS
      boolean[] visited = new boolean[n]; // whether this node has been visited in DFS
      List<List<Integer>> out = new ArrayList<>();
      
      
      
      DFS_AP(0, -1, disc, low, visited, graph, out); // arbitrarily pick a node to start DFS
   

      return out;
  }
    
  public void DFS_AP(int src, int parent, int[] disc, int[] low, boolean[] visited, List<Integer>[] graph, List<List<Integer>> out)  {
      
      visited[src] = true;
      disc[src]=low[src] = time++;
      for (Integer nei : graph[src]) {
          if (!visited[nei]) { //not visited
              
              DFS_AP(nei, src, disc, low, visited, graph, out);
              if(disc[src] < low[nei]){
                  out.add(Arrays.asList(src, nei));
              }
              low[src] = Math.min(low[src],low[nei]);
              
          } else if(nei!=parent){
              low[src] = Math.min(low[src],disc[nei]);
          }
              
      }
      
      
  }
  
}

//41  ==================================================================================================================
//https://leetcode.com/problems/similar-string-groups/submissions/

//isme jis string mein 0 ya 2 character ka place chane hai wo 1 group mein aaynge


     int par[];
    public int find(int p){
        if(par[p] == p) return p;
        return par[p] = find(par[p]);
    }
    
    public int numSimilarGroups(String[] strs) {
        int len = strs.length;
        par=new int[len];
        for(int i = 0;i< len;i++) par[i] = i;
        
        for(int i=0;i<len;i++){
            for(int j=0;j<len;j++){
                if(isSimilar(strs[i],strs[j],i,j)){
                    //ye union hai
                    int n = find(i);
                    int m = find(j);
                    par[n] = m;
                }
            }
            
        }
        
        int ans = 0;
        //sabka parent badal gaya hoga
        // only leade ka parent same hoga
        //jitna leader utne grrroup honge
        for(int i = 0;i< len;i++){
            if(par[i] == i){
                ans++;
            }
            
        } 
        return ans;
    }
    
    private boolean isSimilar(String a, String b, int i, int j){
        int count = 0;
        for(int k=0;k<a.length();k++){
            if(a.charAt(k)!=b.charAt(k)) count++;
        }
        
        if(count == 0 || count ==2) return true;
        return false;
        
        
    }


//42  ==================================================================================================================
// https://leetcode.com/problems/as-far-from-land-as-possible/submissions/

//saare 1 ko q mein daalna hai then
//aur har q se bfs lagana hai, jab tak zero mile


     public int maxDistance(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        if(n==0 || m ==0) return 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        ArrayDeque<int[]> q = new ArrayDeque();
        //boolean [] visited = new boolean[n];
        
        int zeros = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1){
                    q.addLast(new int[]{i,j});
                } else{
                    zeros++;
                }
            }
        }
        
        //edge case
        if(q.size()==0 || q.size()==n*m) return -1;
        
        
        int ans = 0;
        
        
        while(!q.isEmpty() && zeros >0){
            int size = q.size();
            for(int i=0;i< size;++i){
                int[] rEdge = q.removeFirst();
                int r = rEdge[0];
                int c = rEdge[1];
                for(int d[] :dirs){
                    int nr = r+d[0];
                    int nc = c+d[1];
                     if (0 <= nr && nr < n && 0 <= nc && nc < m && grid[nr][nc] == 0){
                        q.addLast(new int[]{nr,nc});
                        grid[nr][nc]=1;
                        
                        zeros--;
                    }
                }
                
                
            }
            ans++;
        }
        return ans;
    }


//43  ==================================================================================================================

//https://leetcode.com/problems/shortest-bridge/







///44  ==================================================================================================================


https://leetcode.com/problems/reconstruct-itinerary/

//isme JFK se jaane wali flight ka detail dena hai
//jiski degree odd hogi wo destination flight ho sakte hai
//lexical order me ans chahiye and hece PQ
//df lagna hai JFK se

//JFK->SFO->LAX->NYC is tarahse dfs chalega

//but dfs mein ye opposite aayega
//dfs k post areaa mein aga hum addfirst kare to 

//pahle NYC aayge uska addFirst LAX is tarah se JFK first mein aa jayega



public List<String> findItinerary(List<List<String>> tickets) {
        LinkedList<String> result = new LinkedList<>();
        
        //priority quey for lexical order
        
        HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
        for(List<String> edge :  tickets){
            if (!graph.containsKey(edge.get(0))){
                graph.put(edge.get(0), new PriorityQueue<>());
                
            graph.get(edge.get(0)).add(edge.get(1));    
            }else{
                 graph.get(edge.get(0)).add(edge.get(1)); 
            }
        }
        
        DFS("JFK", graph, result);
        
        return result;
        
    }
    
    
    private void DFS(String node, HashMap<String, PriorityQueue<String>> graph, LinkedList<String> result) {
        PriorityQueue<String> nodes = graph.get(node);
        while (nodes != null && !nodes.isEmpty()) {
            DFS(nodes.remove(), graph, result);
        }
        result.addFirst(node); // this is the key, instead of reversing add to the head of LinkedList.
    }

}