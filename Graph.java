public class Graph{

    int nodes;
    ArrayList<ArrayList<Integer>> adjList;

    Graph(int numberOfNodes){
        this.nodes = numberOfNodes;
        for(int i=0; i<numberOfNodes; i++)
            adjList.add(new ArrayList<Integer>);
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


        while(!stack.isEmpty){

        if(!visited[start]){
            visited[start] = true;

            for(int neighbour: adjList[])
        }
        }
    }
}