import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Heap<AnyType extends Comparable<AnyType>>
{
    private int size;
    private AnyType[] heap;

    public Heap(int cap)
    {
        size = 0;
        heap = (AnyType[]) new Comparable[cap];
    }

    private void heapify(int k)
    {
        AnyType tmp = heap[k];
        int child;

        for(; 2*k <= size; k = child)
        {
            child = 2*k;

            if(child != size &&
                    heap[child].compareTo(heap[child + 1]) > 0) child++;

            if(tmp.compareTo(heap[child]) > 0)  heap[k] = heap[child];
            else
                break;
        }
        heap[k] = tmp;
    }

    public AnyType deleteMin() throws RuntimeException
    {
        if (size == 0) throw new RuntimeException();
        AnyType min = heap[1];
        heap[1] = heap[size--];
        heapify(1);
        return min;
    }

    public void insert(AnyType x)
    {
        if(size == heap.length - 1) doubleSize();

        int pos = ++size;

        for(; pos > 1 && x.compareTo(heap[pos/2]) < 0; pos = pos/2 )
            heap[pos] = heap[pos/2];

        heap[pos] = x;
    }
    private void doubleSize()
    {
        AnyType [] old = heap;
        heap = (AnyType []) new Comparable[heap.length * 2];
        System.arraycopy(old, 1, heap, 1, size);
    }

    public String toString()
    {
        String out = "";
        for(int k = 1; k <= size; k++) out += heap[k]+" ";
        return out;
    }
}

class Point {
    public final int x;
    public final int y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Forest {
    private int[] parent;
    private int[] size;

    public Forest(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {
        if (p == parent[p]) return p;
        parent[p] = find (parent[p]);
        return parent[p];
    }

    public void union(int p, int q) {
        p = find(p);
        q = find(q);
        if (p == q) return;

        if (size[p] < size[q]) {
            parent[p] = q;
        } else {
            parent[q] = p;
            if (size[p] == size[q]) size[p] ++;
        }
    }
}

class Edge implements Comparable<Edge> {
    int src, dest;
    double weight;
    public Edge (int src, int dst, double weight){
        this.src = src;
        this.dest = dst;
        this.weight = weight;
    }

    public int compareTo(Edge compareEdge) {
        return Double.compare(this.weight, compareEdge.weight);
    }

    @Override public String toString (){
        return "{src:"+src+", dest: "+dest+", weight: "+weight+"}";
    }
}

public class Kruskal {
    private int V;
    private Forest forest;
    private Heap<Edge> heapp;

    public Kruskal(int v)
    {
        V = v;
        heapp = new Heap<>(V * V);
        forest = new Forest(V);
    }

    private double Kruskal_MST()
    {
        int e = 0;

        //Collections.sort(edge);

        double road = 0.0;

        while (e < V - 1)
        {
            Edge elem = heapp.deleteMin();
            int x = forest.find(elem.src);
            int y = forest.find(elem.dest);

            if (x != y)
            {
                road += elem.weight;
                e++;
                forest.union(x, y);
            }
        }
        return road;
    }


    private double get_length(int x1, int y1, int x2, int y2){
        return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 -y1)*(y2 -y1));
    }

    public static void main (String[] args) throws IOException {
        //long startTime = System.currentTimeMillis();

        List<Point> coords = new ArrayList<>();

        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        int v = Integer.valueOf(bi.readLine());

        for (int i = 0; i< v; i ++){
            String line;
            if ((line =  bi.readLine()) != null){
                String s [] = line.split(" ");
                coords.add (new Point (Integer.parseInt(s[0]), Integer.parseInt(s[1])));
            }else{
                break;
            }
        }

        bi.close();

        Kruskal graph = new Kruskal(v);

        for(int i = 0; i < v; i ++ )
        {
            for(int j = i; j < v; j++){
                graph.heapp.insert(new Edge(i, j, graph.get_length(coords.get(i).x,coords.get(i).y, coords.get(j).x, coords.get(j).y)));
            }
        }
        System.out.printf("%.2f", graph.Kruskal_MST());
        //System.out.println("\ntime:"+ (System.currentTimeMillis() - startTime));
    }
}