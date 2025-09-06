import java.util.*;

class Kosaraju{

    private int V;
    private List<List<Integer>> adjList;

    Kosaraju(int V) {
        this.V = V;
        adjList = new ArrayList<>();
        for (int i = 0; i < V; i++) adjList.add(new ArrayList<>());
    }

    void addEdge(int u, int v) {
        adjList.get(u).add(v);
    }

    void dfs1(int v, boolean[] visited, List<List<Integer>> adjList, Stack<Integer> stack){

        visited[v] = true;
        for(int nei: adjList.get(v)){
            if(!visited[nei]){
                dfs1(nei, visited, adjList, stack);
            }
        }
        stack.push(v); // highest finishing time, means this one gets executed in the last.
    }

    void dfs2(int v, boolean[] visited, List<List<Integer>> reverseAdjList, List<Integer> comp ){
        visited[v] = true;
        comp.add(v);
        for(int nei: reverseAdjList.get(v)){
            if(!visited[nei]){
                dfs2(nei, visited, reverseAdjList, comp);
            }
        }
    }

    List<List<Integer>> reverse(List<List<Integer>> adjList){
        List<List<Integer>> rev = new ArrayList<>();
        int size = adjList.size();

        for(int u=0; u<size; u++) rev.add(new ArrayList<>());

        for(int u=0; u<size; u++){
            for(int v: adjList.get(u)){
                rev.get(v).add(u);
            }
        }

        return rev;
    }

    List<List<Integer>> getSCCs() {

        boolean[] visited = new boolean[V];

        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<V; i++){

            if(!visited[i]) dfs1(i, visited, adjList, stack);
        }

        Arrays.fill(visited, false);
        
        List<List<Integer>> rev = reverse(adjList);
        List<List<Integer>> scc = new ArrayList<>();

        while(!stack.isEmpty()){
            int curr = stack.pop();
            if(!visited[curr]){

                List<Integer> currScc = new ArrayList<>();
                dfs2(curr, visited, rev, currScc);
                scc.add(currScc);
            }
        }

        return scc;

    }


}