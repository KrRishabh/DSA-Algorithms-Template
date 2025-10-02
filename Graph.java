import java.util.*;

public class Graph{

    int nodes;
    ArrayList<ArrayList<Integer>> adjList;

    Graph(int numberOfNodes){
        this.nodes = numberOfNodes;
        this.adjList = new ArrayList<>();
        for(int i=0; i<numberOfNodes; i++)
            adjList.add(new ArrayList<Integer>());
    }

    public void addEdge(int u, int v, boolean isDirected){
        this.adjList.get(u).add(v);
        if(!isDirected){
            this.adjList.get(v).add(u);
        }
    }

    public void dfs(int node, boolean[] visited){
        visited[node] = true;
        System.out.println("visiting - "+node);

        for(int neighbour: adjList.get(node)){
            if(!visited[neighbour])
                dfs(neighbour, visited);
        }
    }

    public void dfsIterative(int start, boolean[] visited){

        Stack<Integer> stack = new Stack<Integer>();
        stack.push(start);


        while(!stack.isEmpty()){
            int curr = stack.pop();
            System.out.println("currently visiting - ", curr);
            
            if(!visited[curr]){
                visited[curr] = true;

                for(int neighbour: adjList[curr]{
                    if(!visited[neighbour]){
                        stack.push(neighbour);
                    }
                })
            }
        }
    }

    public void bfs(int start, boolean[] visited){

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(start);
        visited[start] = true;
        while(!queue.isEmpty()){

            int curr = queue.poll();
            System.out.println("visited - "+curr);
            for(int neighbour : adjList.get(curr)){
                
                if(!visited[neighbour]){
                    queue.add(neighbour);
                    visited[neighbour] = true;
                }
            }
        }
    }

    public void bfsPrintLevels(int start, boolean[] visited){

        Queue<Integer> queue = new LinkedList<Integer>();
        int level = 0;
        queue.add(start);
        visited[start] = true;

        while(!queue.isEmpty()){

            int nextSize = queue.size();

            // we already know that for the nextSize items they are from the previous level
            for(int i=0; i<nextSize; i++){

                int curr = queue.poll();
                System.out.println("visiting "+curr+" at level = "+level);
                for(int next: adjList.get(curr)){
                    if(!visited[next]){ // Check if not visited before adding
                        queue.add(next);
                        visited[next] = true;
                    }
                }
            }
            
            level++;
        }
        
    }

    public static int countConnectedComponents(){

        int count=0;
        boolean visited[] = new boolean[nodes];
        for(int i=0; i<nodes; i++){
            if(!visited[i]){
                dfs(i, visited);
                count++;
            }
        }

        return count;
    }

    public boolean bipartiteCheck(){

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        int color[] = new int[nodes]; // 0 = uncolored, 1 = red, -1 = blue
        color[0] = 1; //lets start with red
        while(!queue.isEmpty()){

            int curr = queue.poll();
            for(int nei: adjList.get(curr)){
                if(color[nei] == 0){
                    color[nei] = -color[curr];
                    queue.add(nei);
                }
                else if(color[nei] == color[curr])
                    return false;
            }
        }

        return true;

    }


    public ArrayList<Integer> topologicalSort(){
        // kahn's algorithm

        ArrayList<Integer> res = new ArrayList<Integer>();
        int[] indegree = new int[nodes];
        for(int i=0; i<nodes; i++){

            for(int nei: adjList.get(i)){

                indegree[nei] = indegree[nei]+1;
            }
        }

        Queue<Integer> q = new LinkedList<Integer>();
        for(int i=0; i<nodes; i++){
            if(indegree[i] == 0)
                q.offer(i);
        }

        while(!q.isEmpty()){

            int curr = q.poll();
            res.add(curr);
            for(int nei: adjList.get(curr)){
                indegree[nei] = indegree[nei]-1;
                if(indegree[nei] == 0) q.offer(nei);
            }
        }

        if(res.size() == nodes) return res;
        
        // there's cycle present in the graph so topological sort is not possible hence we retun empty arraylist.
        return new ArrayList<Integer>();

    }

    public static void floydWarshall(int[][] graph){

        int INF = Integer.MAX_VALUE;
        int V = graph.length;
        int[][] dist = new int[V][V];

        for(int i=0; i<V; i++){
            for(int j=0; j<V; j++){
                dist[i][j] = graph[i][j];
            }
        }

        for(int k=0; k<V; k++){
            for(int i=0; i<V; i++){
                for(int j=0; j<V; j++){
                    if(dist[i][k] != INF && dist[k][j] != INF){
                        int newDist = dist[i][k]+dist[k][j];
                        if(newDist< dist[i][j]){
                            dist[i][j] = newDist;
                        }
                    }
                }
            }
        }

        //detect negative cycle
        for(int i=0; i<V; i++){
            if(dist[i][i] < 0){
                System.out.println("Negative cycle found");
            }
        }

        return dist;


    }


    
}