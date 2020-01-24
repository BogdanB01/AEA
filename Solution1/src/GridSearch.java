import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GridSearch {
    public static final int[] ITERATIONS = { 5000, 6000, 7000, 8000 };
    public static final int[] TOLERANCES = { 500, 750, 1000, 1250, 1500 };

    public static final Map<String, Integer> fileBestResultMapping = new HashMap<>();

    static {
        fileBestResultMapping.put("data1.CLQ", 34);
        fileBestResultMapping.put("data2.CLQ", 44);
        fileBestResultMapping.put("data3.CLQ", 57);
        fileBestResultMapping.put("data4.CLQ", 68);
        fileBestResultMapping.put("data5.CLQ", 345);
    }

    public static void main(String[] args) {

        Map<String, Map<IterationTolerance, IntSummaryStatistics>> finalResults = new HashMap<>();
        for (Map.Entry<String, Integer> entry : fileBestResultMapping.entrySet()) {

            String file = entry.getKey();
            Map<IterationTolerance, IntSummaryStatistics> statisticsMap = new HashMap<>();

            for (int iteration : ITERATIONS) {
                for (int tolerance : TOLERANCES) {
                    List<Integer> results = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        try {
                            int result = MaxClique.run(iteration, tolerance, file);
                            results.add(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    IntSummaryStatistics statistics = results.stream().collect(Collectors.summarizingInt(Integer::intValue));
                    statisticsMap.put(new IterationTolerance(iteration, tolerance), statistics);
                }
            }

            finalResults.put(file, statisticsMap);
        }

        List<Map.Entry<IterationTolerance, Double>> minimum= new ArrayList<>();
        for (Map.Entry<String, Map<IterationTolerance, IntSummaryStatistics>> entry : finalResults.entrySet()) {

            Map<IterationTolerance, IntSummaryStatistics> value = entry.getValue();

            Integer bestKnown = fileBestResultMapping.get(entry.getKey());

            double minimumDifference = Double.MAX_VALUE;
            IterationTolerance bestModel = null;
            for (Map.Entry<IterationTolerance, IntSummaryStatistics> innerEntry : value.entrySet()) {
                System.out.println("File name :" + entry.getKey());
                System.out.println(String.format("[Iteration = %s, Toleration = %s]", innerEntry.getKey().iteration, innerEntry.getKey().tolerance));
                System.out.println(innerEntry.getValue().getAverage());

                double difference = bestKnown - innerEntry.getValue().getAverage();
                System.out.println("Difference " + difference);
                if (bestKnown - innerEntry.getValue().getAverage() < minimumDifference) {
                    minimumDifference = difference;
                    bestModel = innerEntry.getKey();
                }
            }

            Map.Entry<IterationTolerance, Double> newEntry =
                    new AbstractMap.SimpleEntry<>(bestModel, minimumDifference);
            minimum.add(newEntry);
        }

        IterationTolerance bestModel = null;
        double min = Double.MAX_VALUE;
        for (Map.Entry<IterationTolerance, Double> val : minimum) {
            if (val.getValue() < min) {
                bestModel = val.getKey();
            }
        }

        if (bestModel != null) {
            System.out.println(String.format("Best model is [tolerance = %s, iterations = %s]", bestModel.tolerance, bestModel.iteration));
        }

    }

    private static class IterationTolerance {

        private int iteration;
        private int tolerance;

        public IterationTolerance(int iteration, int tolerance) {
            this.iteration = iteration;
            this.tolerance = tolerance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IterationTolerance that = (IterationTolerance) o;
            return iteration == that.iteration &&
                    tolerance == that.tolerance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(iteration, tolerance);
        }
    }
}
