package util;



import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class GraphLoader {

    private GraphLoader() {}

    public static UndirectedGraph<Integer, Pair<Integer>> load(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        Integer numberOfNodes = Integer.valueOf(line.split(" ")[2]);
        UndirectedGraph<Integer, Pair<Integer>> graph = new UndirectedSparseGraph<>();

        while(true) {
            line = reader.readLine();
            if (line == null) break;
            if (!line.contains(" ")) continue;
            Integer from = Integer.valueOf(line.split(" ")[1]);
            Integer to = Integer.valueOf(line.split(" ")[2]);

            graph.addEdge(new Pair<>(from, to), from, to);
        }

        return graph;
    }

}
