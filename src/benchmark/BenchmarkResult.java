package benchmark;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BenchmarkResult {
    private final String algorithmName;
    private final int n;
    private final long executionTimeNanos;
    private final long comparisons;
    private final long assignments;
    private final int primeCount;
    private final int linesOfCode;
    // private final List<Integer> primes;

    public BenchmarkResult(
            String algorithmName,
            int n,
            long executionTimeNanos,
            long comparisons,
            long assignments,
            int linesOfCode,
            List<Integer> primes
    ) {
        this.algorithmName = algorithmName;
        this.n = n;
        this.executionTimeNanos = executionTimeNanos;
        this.comparisons = comparisons;
        this.assignments = assignments;
        this.primeCount = (primes != null) ? primes.size() : -1;
        this.linesOfCode = linesOfCode;
        // this.primes = primes;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getN() {
        return n;
    }

    public long getExecutionTime(TimeUnit unit) {
        return unit.convert(executionTimeNanos, TimeUnit.NANOSECONDS);
    }

    public long getExecutionTimeNanos() {
        return executionTimeNanos;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAssignments() {
        return assignments;
    }

    public int getPrimeCount() {
        return primeCount;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    @Override
    public String toString() {
        return String.format(
                "Algorithm: %-20s | N: %-8d | LOC: %-5d | Time(ms): %-10.3f | Comparisons: %-15d | Assignments: %-15d | Primes Found: %d",
                algorithmName,
                n,
                linesOfCode,
                getExecutionTime(TimeUnit.MILLISECONDS), // Convertir a ms con decimales
                comparisons,
                assignments,
                primeCount
        );
    }
}
