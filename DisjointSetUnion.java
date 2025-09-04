import java.util.*;

public class DSU{

    int components;
    int[] parent;

    DSU(int n){
        parent = new int[n];
        components = n;
        for(int i=0; i<n; i++) parent[i] = -1;
    }

    // find with path compression
    public int find(int x){

        if(parent[x] < 0) return x; // root
        
        parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    public boolean union(int a, int b){

        int ra = find(a);
        int rb = find(b);
        if(ra == rb) return false; // already in same set

        if(parent[ra] > parent[rb]){ // more negative is larger set
            int temp = ra;
            ra = rb;
            rb = temp;
        }

        //merging rb into ra
        parent[ra] += parent[rb]; //increasing size
        parent[rb] = ra;
        components--;
        return true;
    }

    public boolean connected(int a, int b){
        return find(a) == find(b);
    }

    public int sizeOf(int x){
        return -parent[find(x)];
    }

    public int count(){
        return components;
    }

    public static boolean hasCycle(int n, int edges[][]){

        DSU dsu = new DSU(n);
        for(int i=0; i<edges.length; i++){
            if(!dsu.union(edges[i][0], edges[i][1])) return true;
        }

        return false;
    }

    public static int countConnectedComponents(int n, int edges[][]){

        DSU dsu = new DSU(n);
        for(int i=0; i<edges.length; i++){
            dsu.union(edges[i][0], edges[i][1]);
        }

        return dsu.count();
    }

    public static class Edge{
        int u,v,w;
        Edge(int u, int v, int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static class KruskalResult{
        long totalWeight;
        ArrayList<Edge> edges = new ArrayList<Edge>();
        boolean isSpanning;
    }

    public static KruskalResult getMST(int n, ArrayList<Edge> edges){

        edges.sort((Edge a, Edge b) -> Integer.compare(a.w, b.w));

        DSU dsu = new DSU(n);

        KruskalResult result  = new KruskalResult();

        for(Edge e: edges){

            if(dsu.union(e.u, e.v)){
                result.edges.add(e);
                result.totalWeight += e.w;
                if(result.edges.size() == n-1) break;
            }
        }

        if(result.edges.size() == n-1) result.isSpanning = true;
        return result;
    }

    public static int[] dijkstraShortestPath(int n, int src, ArrayList<Edge> edges){

        class Pair{
            int node;
            int distance;
            Pair(int n, int d){
                this.node = n;
                this.distance = d;
            }
        }

        // construct adjList
        ArrayList<ArrayList<Edge>> adjList = new ArrayList<ArrayList<Edge>>();
        for(int i=0; i<n; i++) adjList.add(new ArrayList<Edge>());

        for(Edge e: edges){
            // assuming undirected graph
            adjList.get(e.u).add(e);
            adjList.get(e.v).add(new Edge(e.v, e.u, e.w)); // reverse edge for undirected
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE/2);
        dist[src] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>((Pair a, Pair b) -> Integer.compare(a.distance, b.distance));
        pq.offer(new Pair(src, 0));

        boolean[] visited = new boolean[n];

        while(!pq.isEmpty()){
            Pair currPair = pq.poll(); // Fixed: Pair not int
            int u = currPair.node;
            int currDist = currPair.distance;
            
            if(visited[u]) continue; // Skip if already processed
            visited[u] = true;

            // Relax all adjacent edges
            for(Edge e: adjList.get(u)){
                int v = e.v;
                int weight = e.w;
                
                if(currDist + weight < dist[v]){
                    dist[v] = currDist + weight;
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        return dist;
    }

    public class BellmondFordResult{
        long[] dist;
        int[] parent;
        boolean hasNegativeCycle;
        BellmondFordResult(long[] d, int[] p, boolean neg){
            dist = d; 
            parent = p; 
            hasNegativeCycle = neg;
        }
    }

    public static BellmondFordResult bellmondFord(int n, ArrayList<Edge> edges, int src){

        long[] dist = new long[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        long INF = Long.MAX_VALUE/4;
        Arrays.fill(dist, INF);
        dist[src] = 0;
        boolean hasNegativeCycle = false;

        // Relax edges up to n-1 times
        for(int i=0; i<n-1; i++){
            boolean updated = false;

            for(Edge e: edges){
                if(dist[e.u] == INF) continue;

                long currDist = dist[e.u] + e.w;
                if(currDist < dist[e.v]){
                    dist[e.v] = currDist;
                    parent[e.v] = e.u;
                    updated = true;
                }
            }
            
            // Early termination if no updates in this iteration
            if(!updated) break;
        }

        // Check for negative cycle
        for(Edge e: edges){
            if(dist[e.u] == INF) continue;
            long currDist = dist[e.u] + e.w;
            if(currDist < dist[e.v]){
                hasNegativeCycle = true;
                break; // Found negative cycle, no need to continue
            }
        }

        return new BellmondFordResult(dist, parent, hasNegativeCycle);

    }





}






