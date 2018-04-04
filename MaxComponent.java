import java.util.*;

class Graph {
    private int V;

    public List<List<Integer>> adj;

    public Graph(int V) {

        this.V = V;

        adj = new ArrayList<>();
        for(int i = 0; i < V; i++)
            adj.add(new ArrayList<>());
    }

    public int V() {
        return V;
    }

    public void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
    }

    public int edges(int v)
    {
        return adj.get(v).size();
    }

}

public class MaxComponent {
    private boolean[] visited;
    private int[] number;
    private int[] size;
    private int count;

    public MaxComponent(Graph G) {
        visited = new boolean[G.V()];
        number = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        visited[v] = true;
        number[v] = count;
        size[count]++;
        for (int w : G.adj.get(v)) {
            if (!visited[w]) dfs(G, w);
        }
    }

    public int number(int v) {
        return number[v];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        int V = scan.nextInt();

        int E = scan.nextInt();

        Graph G = new Graph(V);

        for(int i = 0, x, y; i < E; i++)
        {
            x = scan.nextInt();
            y = scan.nextInt();
            G.addEdge(x, y);
            arr.add(x);
            arr.add(y);
        }
        scan.close();

        MaxComponent cc = new MaxComponent(G);

        int m = cc.count();

        List<LinkedList<Integer>> components = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            components.add(new LinkedList<>());
        }

        for (int v = 0; v < G.V(); v++) {
            components.get(cc.number(v)).add(v);
        }

        int index_mc = 0;

        int count1 = 0;
        int count2 = 0;

        for (int i = 0, size = 0; i < m; i++) {
            if (size < components.get(i).size())
            {
                size = components.get(i).size();
                index_mc = i;
            }
            else if (size == components.get(i).size())
            {
                //System.out.println(components.get(i) + " " + components.get(index_mc));
                for (int j = 0; j < components.get(i).size(); j++)
                {
                    count1 += G.edges(components.get(i).get(j));
                    //System.out.println("cписок = " + G.adj.get(components.get(i).get(j)));
                }
                for(int j = 0; j < components.get(index_mc).size(); j++)
                    count2 +=G.edges(components.get(index_mc).get(j));

                //ystem.out.println(count1 + " " + count2);
                if(count1 > count2)
                    index_mc = i;
            }
            //System.out.println(components.get(i));
        }

        //System.out.println(components.get(max_cc));

        StringBuilder str = new StringBuilder(300000);
        str.append("graph {\n");

        for(int i = 0, j = 0; i < V; i++)
        {
            str.append(i);
            if(j < components.get(index_mc).size() && i == components.get(index_mc).get(j))
            {
                str.append(" [color = red]");
                j++;
            }
            str.append("\n");
        }

        for (int i = 0; i < E * 2; i += 2)
        {
            str.append(arr.get(i));
            str.append(" -- ");
            str.append(arr.get(i + 1));
            if(components.get(index_mc).contains(arr.get(i + 1)))
                str.append(" [color = red]");
            str.append("\n");
        }

        str.append("}");
        System.out.println(str);

    }
}