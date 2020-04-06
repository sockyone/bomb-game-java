package sample.Tools;

import java.util.Stack;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public BreadthFirstPaths(Graph G,int s) {
        this.s = s;
        marked = new boolean[G.getV()];
        System.out.println(G.getV());
        for (int i=0;i<G.getV();i++) {
            marked[i] = false;
        }
        edgeTo = new int[G.getV()];
        for (int i=0;i<G.getV();i++) {
            edgeTo[i] = -1;
        }
        SpecifiedQueue q = new SpecifiedQueue(200);   // this just a specified number which I found it suitable for my home work.
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w:G.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x=v;x!=s;x=edgeTo[x]) {
            path.push(x);
        }
        return path;
    }

    public boolean[] getMarked() {
        return marked;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }
}
