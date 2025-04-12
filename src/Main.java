import algorithms.*;
import benchmark.BenchmarkResult;
import benchmark.BenchmarkRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // Lista de algoritmos a probar
        List<PrimeAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(new OptimizedTrialDivision());
        algorithms.add(new CribaEratostenes());

        // Valores de N para probar
        int[] nValues = { 100, 1000, 10000, 20000, 100000, 1000000 };

        BenchmarkRunner runner = new BenchmarkRunner();
        List<BenchmarkResult> allResults = new ArrayList<>();

        System.out.println("Starting Prime Algorithm Benchmarks...");
        System.out.println("=".repeat(100));

        for (PrimeAlgorithm algorithm : algorithms) {
            System.out.printf("Benchmarking Algorithm: %s%n", algorithm.getName());
            for (int n : nValues) {
                System.out.printf("  Running for N = %d... ", n);
                try {
                    // Ejecutar el benchmark
                    BenchmarkResult result = runner.runBenchmark(algorithm, n);
                    allResults.add(result);
                    System.out.printf(
                            "Done (Time: %.3f ms)%n",
                            result.getExecutionTime(TimeUnit.MILLISECONDS) / 1.0
                    );
                } catch (Exception e) {
                    System.out.printf("Error running %s for N=%d: %s%n",
                            algorithm.getName(), n, e.getMessage());
                    // Podrías querer loggear el stack trace completo
                    // e.printStackTrace();
                }
            }
            System.out.println("-".repeat(100));
        }

        System.out.println("Benchmark Results Summary:");
        System.out.println("=".repeat(100));
        // Imprimir cabecera de la tabla
        System.out.printf(
                "%-40s | %-8s | %-5s | %-15s | %-15s | %-15s | %s%n",
                "Algorithm", "N", "LOC", "Time (ms)", "Comparisons", "Assignments", "Primes Found"
        );
        System.out.println("-".repeat(100));

        // Loggear los resultados
        for (BenchmarkResult result : allResults) {
            System.out.printf(
                    "%-40s | %-8d | %-5d | %-15.3f | %-15d | %-15d | %d%n",
                    result.getAlgorithmName(),
                    result.getN(),
                    result.getLinesOfCode(),
                    result.getExecutionTime(TimeUnit.MILLISECONDS) / 1.0,
                    result.getComparisons(),
                    result.getAssignments(),
                    result.getPrimeCount()
            );
        }
        System.out.println("=".repeat(100));

        // Aquí podrías añadir lógica para escribir los resultados a un archivo CSV,
        // una base de datos, o usar un framework de logging más avanzado.
        // logResultsToCsv(allResults, "benchmark_results.csv");
    }

    // Ejemplo de función para loggear a CSV (simplificado)
    /*
    private static void logResultsToCsv(List<BenchmarkResult> results, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("Algorithm,N,Time(ns),Comparisons,Assignments,PrimeCount");
            // Data
            for (BenchmarkResult result : results) {
                writer.printf("%s,%d,%d,%d,%d,%d%n",
                        result.getAlgorithmName(),
                        result.getN(),
                        result.getExecutionTimeNanos(),
                        result.getComparisons(),
                        result.getAssignments(),
                        result.getPrimeCount());
            }
            System.out.println("Results successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing results to CSV: " + e.getMessage());
        }
    }
    */
}
