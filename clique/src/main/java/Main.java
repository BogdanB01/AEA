
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import util.GraphLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String path = "C:\\Users\\BogdanBo\\Documents\\Facultate\\AN2\\AEA\\clique\\data.txt";
        UndirectedGraph<Integer, Pair<Integer>> graph = null;
        try {
            graph = GraphLoader.load(path);
            Test algorithm = new Test(graph);

            Runnable task = () -> {

                long start = System.nanoTime();

                Collection<Set<Integer>> allMaximalCliques = algorithm.getAllMaximalCliques();

                allMaximalCliques.stream()
                        .collect(Collectors.groupingBy(Set::size))
                        .entrySet()
                        .stream()
                        .sorted((o1, o2) -> Integer.compare(o2.getKey(), o1.getKey()))
                        .limit(1)
                        .forEach(integerListEntry -> {

                            int maxCliqueSize = integerListEntry.getValue().size();

                            System.out.println("Maximum cliques found: " + maxCliqueSize);

                            integerListEntry.getValue().forEach(vertexList -> {
                                System.out.println("The largest clique: " + vertexList.stream().map(String::valueOf)
                                        .collect(Collectors.joining(" ")));
                                System.out.println("The size of the largest clique: " + vertexList.size());
                            });
                        });

                System.out.println("Execution time in milliseconds: " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));
            };

            task.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
