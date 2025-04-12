package benchmark;

// Objeto para pasar a los algoritmos y que estos actualicen las métricas.
public class PerformanceCounters {
    private long comparisons = 0;
    private long assignments = 0;

    public void reset() {
        this.comparisons = 0;
        this.assignments = 0;
    }

    public void incrementComparisons(long count) {
        this.comparisons += count;
    }

    public void incrementComparisons() {
        this.comparisons++;
    }

    public void incrementAssignments(long count) {
        this.assignments += count;
    }

    public void incrementAssignments() {
        this.assignments++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAssignments() {
        return assignments;
    }

    @Override
    public String toString() {
        return "Comparisons=" + comparisons + ", Assignments=" + assignments;
    }
}
