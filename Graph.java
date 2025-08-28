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

    
}