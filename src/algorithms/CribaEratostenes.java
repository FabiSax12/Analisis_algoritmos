package algorithms;

import benchmark.PerformanceCounters;
import java.util.ArrayList;
import java.util.List;

// Referencia https://reyapuntesblog.blogspot.com/2015/01/criba-de-erastotenes.html
// Adaptado para la estructura de este proyecto, se respeta la logica principal
public class CribaEratostenes implements PrimeAlgorithm {

    @Override
    public String getName() {
        return "Criba de Eratóstenes";
    }

    @Override
    public List<Integer> findPrimes(int n, PerformanceCounters counters) {
        // Se usa false para primo y true para compuesto
        boolean[] isComposite = new boolean[n + 1];
        counters.incrementAssignments();

        // Hasta la raíz cuadrada de n.
        int limit = (int) Math.sqrt(n);
        counters.incrementAssignments();

        // Bucle principal de la criba
        counters.incrementAssignments(); // Asignación inicial p = 2
        for (int p = 2; p <= limit; p++) {
            counters.incrementComparisons(); // Comparación del bucle (p <= limit)
            counters.incrementAssignments(); // Incremento del bucle (p++)

            counters.incrementComparisons(); // Comparación del if (!isComposite[p])
            if (!isComposite[p]) {

                counters.incrementAssignments(); // Asignación inicial i = p * p
                for (int i = p * p; i <= n; i += p) {
                    counters.incrementComparisons(); // Comparación del bucle (i <= n)

                    // Asignación del incremento (i += p)
                    counters.incrementAssignments();

                    isComposite[i] = true;
                    counters.incrementAssignments();
                }
                counters.incrementComparisons(); // Comparación final del bucle interno que falla
            }
        }
        counters.incrementComparisons(); // Comparación final del bucle

        // Recolectar los números primos (índices que quedaron en false)
        List<Integer> primes = new ArrayList<>();
        counters.incrementAssignments(); // Asignación/creación de la lista de resultados

        counters.incrementAssignments(); // Asignación inicial p = 2
        for (int p = 2; p <= n; p++) { // Iterar hasta n inclusive
            counters.incrementComparisons(); // Comparación del bucle (p <= n)
            counters.incrementAssignments(); // Incremento del bucle (p++)

            counters.incrementComparisons(); // Comparación del if (!isComposite[p])
            if (!isComposite[p]) {
                primes.add(p);
                counters.incrementAssignments();
            }
        }
        counters.incrementComparisons(); // Comparación final del bucle

        return primes;
    }

    @Override
    public int getCoreLinesOfCode() {
        // --- CONTEO MANUAL ---
        // boolean[] isComposite = new boolean[n + 1]; // 1 (Inicialización array)
        // int limit = (int) Math.sqrt(n);             // 2 (Cálculo límite)
        // for (int p = 2; p <= limit; p++) {          // 3 (Bucle externo)
        //     if (!isComposite[p]) {                 // 4 (Condición if)
        //         for (int i = p * p; i <= n; i += p) { // 5 (Bucle interno)
        //             isComposite[i] = true;         // 6 (Marcado)
        //         }
        //     }
        // }
        // List<Integer> primes = new ArrayList<>();    // 7 (Inicialización lista resultado)
        // for (int p = 2; p <= n; p++) {              // 8 (Bucle recolección)
        //     if (!isComposite[p]) {                 // 9 (Condición if)
        //         primes.add(p);                     // 10 (Añadir a lista)
        //     }
        // }
        // return primes;                             // 11 (Return)
        return 11;
    }
}
