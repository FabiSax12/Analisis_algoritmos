package algorithms;

import benchmark.PerformanceCounters;
import java.util.List;

// Interfaz común para todos los algoritmos de búsqueda de primos.
public interface PrimeAlgorithm {
    /**
     * Encuentra números primos hasta n.
     * IMPORTANTE: Este método debe actualizar los contadores
     * SOLO por operaciones realizadas DENTRO de su lógica principal.
     *
     * @param n Límite superior (exclusivo o inclusivo según defina la implementación)
     * @param counters Objeto para registrar comparaciones y asignaciones.
     * @return Una lista de números primos encontrados.
     */
    List<Integer> findPrimes(int n, PerformanceCounters counters);

    /**
     * @return Nombre descriptivo del algoritmo.
     */
    String getName();

    /**
     * Devuelve el número de líneas de código (LOC) contadas manualmente
     * que corresponden al NÚCLEO del algoritmo de búsqueda de primos.
     * Excluye código boilerplate, comentarios, líneas en blanco,
     * declaraciones de paquete/imports, y código de medición/contadores si es posible.
     * ¡La consistencia en el conteo es clave!
     *
     * @return El número de líneas de código del núcleo del algoritmo.
     */
    int getCoreLinesOfCode(); // <--- NUEVO MÉTODO
}
