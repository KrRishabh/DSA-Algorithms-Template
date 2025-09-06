import java.util.*;

public class TarjansBridgeAndArticulationPoints{

    private int time = 0;
    private List<List<Integer>> graph;
    private int[] disc, low, parent;
    private boolean[] visited;
    private boolean[] articulation;
    private List<int[]> bridges;

    public TarjansBridgeAndArticulationPoints(int n) {
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        disc = new int[n];
        low = new int[n];
        parent = new int[n];
        Arrays.fill(parent, -1);
        visited = new boolean[n];
        articulation = new boolean[n];
        bridges = new ArrayList<>();
    }

    public void addEdge(int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }


    public void run(){
        
        for(int i=0; i<graph.size(); i++){
            if(!visited[i]){
                dfs(i);
            }
        }
        
    }

    public void dfs(int u){

        int children = 0;
        visited[u] = true;
        disc[u] = ++time; //(discovery time)
        low[u] = time;

        for(int v: graph.get(u)){
            if(!visited[v]){
                children++;
                parent[v] = u;
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
                
                if(low[v] > disc[u]){
                    bridges.add(new int[]{u,v});
                }

                if(parent[u] !=-1 && low[v] >= disc[u]){
                    articulation[u] = true;
                }
            }else if(v != parent[u]){
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if(parent[u] == -1 && children>1)
            articulation[u] = true;


    }

      // Helpers to expose results
    public List<int[]> getBridges() { return bridges; }
    public List<Integer> getArticulationPoints() {
        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < articulation.length; i++) {
            if (articulation[i]) points.add(i);
        }
        return points;
    }

    // Demo
    public static void main(String[] args) {
        TarjansBridgeAndArticulationPoints tarjan = new TarjansBridgeAndArticulationPoints(5);
        tarjan.addEdge(0, 1);
        tarjan.addEdge(0, 2);
        tarjan.addEdge(1, 2);
        tarjan.addEdge(0, 3);
        tarjan.addEdge(3, 4);

        tarjan.run();
        System.out.println("Bridges:");
        for (int[] b : tarjan.getBridges()) {
            System.out.println(b[0] + " -- " + b[1]);
        }
        System.out.println("Articulation Points: " + tarjan.getArticulationPoints());
    }
}