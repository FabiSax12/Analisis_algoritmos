package benchmark;

import algorithms.PrimeAlgorithm;

import java.util.List;

public class BenchmarkRunner {

    public BenchmarkResult runBenchmark(PrimeAlgorithm algorithm, int n) {
        PerformanceCounters counters = new PerformanceCounters();
        List<Integer> primesResult = null;
        long startTime = 0;
        long endTime = 0;
        int loc = algorithm.getCoreLinesOfCode();

        // --- Inicio de la medición ---
        startTime = System.nanoTime(); // Tomar tiempo justo antes de llamar
        try {
            // Llamada al CORE del algoritmo. Pasa los contadores.
            primesResult = algorithm.findPrimes(n, counters);
        } finally {
            // Asegurarse de tomar el tiempo incluso si hay una excepción
            endTime = System.nanoTime();
        }
        // --- Fin de la medición ---

        long durationNanos = endTime - startTime;

        // Crear y devolver el objeto de resultado
        return new BenchmarkResult(
                algorithm.getName(),
                n,
                durationNanos,
                counters.getComparisons(),
                counters.getAssignments(),
                loc,
                primesResult
        );
    }
}
