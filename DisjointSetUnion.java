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

        edges.sort((Edge a, Edge b) -> a.w-b.w);

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

}






