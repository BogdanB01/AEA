import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import org.omg.CORBA.INTERNAL;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private UndirectedGraph<Integer, Pair<Integer>> graph;

    private Collection<Set<Integer>> cliques;

    public Test(UndirectedGraph<Integer, Pair<Integer>> graph) {
        this.graph = graph;
    }

    public Collection<Set<Integer>> getAllMaximalCliques() {
        cliques = new ArrayList<>();

        List<Integer> potential_clique = new ArrayList<>();
        List<Integer> candidates = new ArrayList<>();
        List<Integer> already_found = new ArrayList<>();

        candidates.addAll(graph.getVertices());
        findCliques(potential_clique, candidates, already_found);

        return cliques;
    }

    private void findCliques(List<Integer> potential_clique, List<Integer> candidates, List<Integer> already_found) {
        List<Integer> candidates_array = new ArrayList<>(candidates);
        if (!end(candidates, already_found)) {

            candidates_array.forEach((Integer candidate) -> {

                potential_clique.add(candidate);

                candidates.remove(candidate);
                List<Integer> new_candidates = candidates.stream()
                        .filter(new_candidate -> graph.isNeighbor(candidate, new_candidate))
                        .collect(Collectors.toList());

                List<Integer> new_already_found = already_found.stream()
                        .filter(new_found -> graph.isNeighbor(candidate, new_found))
                        .collect(Collectors.toList());

                if (new_candidates.isEmpty() && new_already_found.isEmpty()) {
                    cliques.add(new HashSet<>(potential_clique));
                } else {
                    findCliques(potential_clique, new_candidates, new_already_found);
                }

                potential_clique.remove(candidate);
                already_found.add(candidate);
            });
        }
    }

    private boolean end(List<Integer> candidates, List<Integer> already_found) {

        return already_found.stream()
                .anyMatch(found -> candidates.stream()
                        .filter(candidate -> graph.isNeighbor(found, candidate)).count() > candidates.size());
    }
}