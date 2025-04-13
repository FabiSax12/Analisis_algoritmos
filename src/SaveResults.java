import benchmark.BenchmarkResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SaveResults {

    /**
     * Genera un archivo CSV separado para cada algoritmo con los resultados del benchmark.
     * El formato sigue la estructura de la tabla solicitada por la profesora.
     *
     * @param results Lista completa de resultados del benchmark.
     */
    public static void generateCsvFiles(List<BenchmarkResult> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No hay resultados para generar archivos CSV.");
            return;
        }

        // 1. Obtener los nombres únicos de los algoritmos
        Set<String> algorithmNames = new HashSet<>();
        for (BenchmarkResult result : results) {
            algorithmNames.add(result.getAlgorithmName());
        }

        // 2. Iterar sobre cada nombre de algoritmo
        int algorithmCounter = 1;
        for (String name : algorithmNames) {

            // Filtrar los resultados solo para este algoritmo
            List<BenchmarkResult> algoResults = filterAlgorithmResults(results, name);

            if (algoResults.isEmpty()) continue;

            // Crear un nombre de archivo seguro
            String filename = createFilename(name, algorithmCounter);

            System.out.printf("  Escribiendo archivo: %s%n", filename);

            // 3. Escribir el archivo CSV para este algoritmo
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

                // Escribir la cabecera de la tabla CSV
                writer.println(
                        "\"Tamaño\",\"Asignaciones\",\"Comparaciones\",\"Cantidad de líneas ejecutadas (Suma asig + comp)\",\"Tiempo de ejecución (ms)\",\"Cantidad de líneas del código\""
                );

                // Escribir los datos de cada ejecución para este algoritmo
                for (BenchmarkResult result : algoResults) {
                    long assignments = result.getAssignments();
                    long comparisons = result.getComparisons();
                    long executedLines = assignments + comparisons; // Calcular suma
                    long timeMs = result.getExecutionTime(TimeUnit.MILLISECONDS); // Tiempo en ms
                    int loc = result.getLinesOfCode();
                    int nValue = result.getN();

                    writer.printf(
                            "%d,%d,%d,%d,%d,%d%n",
                            nValue,
                            assignments,
                            comparisons,
                            executedLines,
                            timeMs,
                            loc
                    );
                }

            } catch (IOException e) {
                System.err.printf(
                        "Error al escribir el archivo CSV '%s': %s%n",
                        filename,
                        e.getMessage()
                );
            }
            algorithmCounter++;
        }
    }

    private static List<BenchmarkResult> filterAlgorithmResults(List<BenchmarkResult> allResults, String algorithmName) {
        return allResults.stream()
            .filter(r -> r.getAlgorithmName().equals(algorithmName))
            .sorted((r1, r2) -> Integer.compare(r1.getN(), r2.getN())) // Ordenar por N
            .toList();
    }

    private static String createFilename(String algorithmName, int algorithmCounter) {
        String safeAlgoName = algorithmName.replaceAll("[^a-zA-Z0-9_\\-]", "_"); // Reemplaza caracteres no seguros

        return String.format("Resultados_%d_%s.csv", algorithmCounter, safeAlgoName);
    }
}
