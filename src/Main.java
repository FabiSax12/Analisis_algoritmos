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
        algorithms.add(new CribaEratostenes());
        algorithms.add(new CribaDeAtkin());
        algorithms.add(new OptimizedTrialDivision());
        algorithms.add(new MillerRabinTest());

        // Valores de N para probar
        int[] nValues = { 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

        BenchmarkRunner runner = new BenchmarkRunner();
        List<BenchmarkResult> allResults = new ArrayList<>();

        System.out.println("Iniciando pruebas...");
        System.out.println("=".repeat(125));

        for (PrimeAlgorithm algorithm : algorithms) {
            System.out.printf("Midiendo el algoritmo: %s%n", algorithm.getName());

            for (int n : nValues) {
                System.out.printf("  Ejecutando para N = %d... ", n);
                try {
                    // Ejecutar las mediciones
                    BenchmarkResult result = runner.runBenchmark(algorithm, n);
                    allResults.add(result);
                    System.out.printf(
                            "Terminado (Tiempo: %.0f ms)%n",
                            result.getExecutionTime(TimeUnit.MILLISECONDS) / 1.0
                    );
                } catch (Exception e) {
                    System.out.printf(
                        "Error ejecutando %s para N=%d: %s%n",
                        algorithm.getName(), n, e.getMessage()
                    );
                }
            }
            System.out.println("-".repeat(125));
        }

        System.out.println("Resumen de los resultados...:");
        System.out.println("=".repeat(125));

        // Imprimir cabecera de la tabla
        System.out.printf(
                "%-33s | %-9s | %-5s | %-11s | %-15s | %-15s | %-20s | %s%n",
                "Algoritmo", "N", "Lineas", "Tiempo (ms)", "Comparaciones", "Asignaciones", "Lineas Ejecutadas", "Primos encontrados"
        );
        System.out.println("-".repeat(125));

        // Imprimir los resultados
        String lastPrintedAlgorithm = allResults.get(0).getAlgorithmName();

        for (BenchmarkResult result : allResults) {
            if (!result.getAlgorithmName().equals(lastPrintedAlgorithm)) {
                System.out.println("-".repeat(125));
                lastPrintedAlgorithm = result.getAlgorithmName();
            }

            System.out.printf(
                    "%-33s | %-9d | %-5d | %-12.0f | %-15d | %-15d | %-20d | %d%n",
                    result.getAlgorithmName(),
                    result.getN(),
                    result.getLinesOfCode(),
                    result.getExecutionTime(TimeUnit.MILLISECONDS) / 1.0,
                    result.getComparisons(),
                    result.getAssignments(),
                    result.getComparisons() + result.getAssignments(),
                    result.getPrimeCount()
            );
        }
        System.out.println("=".repeat(125));

        // --- Generar archivos CSV Para abrirlos en EXCEL---
        System.out.println("\nGenerando archivos CSV para cada algoritmo...");
        SaveResults.generateCsvFiles(allResults);
        System.out.println("Generación de archivos CSV completada.");
    }
}
