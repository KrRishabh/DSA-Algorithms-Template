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
        
        parent[x] = find(x); // path compression
        return parent[x];
    }

    public boolean union(int a, int b){

        int ra = find(a);
        int rb = find(b);
        if(ra == rb) return false; // already in same set

        if(parent[ra] > parent[rb]){ // more negative is larger set
            int temp = ra;
            int ra = rb;
            int rb = temp;
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
}