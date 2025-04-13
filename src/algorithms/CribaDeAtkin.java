package algorithms;

import benchmark.PerformanceCounters;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la Criba de Atkin para generar números primos hasta un límite n.
 * Esta versión adapta la lógica encontrada en Sanfoundry.
 * Referencia: https://www.sanfoundry.com/java-program-sieve-of-atkin-algorithm/
 * Autor Original: Desconocido (Sanfoundry)
 */
public class CribaDeAtkin implements PrimeAlgorithm {

    @Override
    public String getName() {
        return "Criba de Atkin";
    }

    @Override
    public List<Integer> findPrimes(int n, PerformanceCounters counters) {

        counters.incrementComparisons();
        if (n < 2) return new ArrayList<>();

        boolean[] prime = new boolean[n + 1];
        counters.incrementAssignments();

        // Marcar 2 y 3 explícitamente
        prime[2] = true;
        counters.incrementAssignments();

        counters.incrementComparisons();
        if (n >= 3) {

            prime[3] = true;
            counters.incrementAssignments();
        }


        int root = (int) Math.ceil(Math.sqrt(n));
        counters.incrementAssignments();

        counters.incrementAssignments(); // x = 1
        for (int x = 1; x < root; x++) {
            counters.incrementComparisons(); // x < root
            counters.incrementAssignments(); // x++


            int xSquared = x * x;
            counters.incrementAssignments();

            counters.incrementAssignments(); // y = 1
            for (int y = 1; y < root; y++) {
                counters.incrementComparisons(); // y < root
                counters.incrementAssignments(); // y++


                int ySquared = y * y;
                counters.incrementAssignments();

                // Forma 1
                int num = 4 * xSquared + ySquared;
                counters.incrementAssignments();

                counters.incrementComparisons(3);
                if (num <= n && (num % 12 == 1 || num % 12 == 5)) {
                    prime[num] = !prime[num]; // XOR
                    counters.incrementAssignments();
                }


                // Forma 2
                num = 3 * xSquared + ySquared;
                counters.incrementAssignments();

                counters.incrementComparisons(2); // num <= n, num % 12 == 7
                if (num <= n && num % 12 == 7) {

                    prime[num] = !prime[num]; // XOR
                    counters.incrementAssignments();
                }


                // Forma 3
                counters.incrementComparisons(); // x > y
                if (x > y) {

                    num = 3 * xSquared - ySquared;
                    counters.incrementAssignments();

                    counters.incrementComparisons(3);
                    if (num > 0 && num <= n && (num % 12 == 11)) {

                        prime[num] = !prime[num]; // XOR
                        counters.incrementAssignments();
                    }
                }
            }
            counters.incrementComparisons();

        }
        counters.incrementComparisons();

        counters.incrementAssignments(); // i = 5
        for (int i = 5; i <= root; i++) {
            counters.incrementComparisons(); // i <= root
            counters.incrementAssignments(); // i++

            counters.incrementComparisons(); // if (prime[i])
            if (prime[i]) {

                int iSquared = i * i;
                counters.incrementAssignments();

                counters.incrementAssignments(); // j = iSquared
                for (int j = iSquared; j < n; j += iSquared) {
                    counters.incrementComparisons(); // j < n
                    counters.incrementAssignments(); // j += iSquared

                    prime[j] = false; // Asignación directa a false
                    counters.incrementAssignments();
                }
                counters.incrementComparisons();
            }
        }
        counters.incrementComparisons();



        // --- Recolectar resultados ---
        List<Integer> resultPrimes = new ArrayList<>();
        counters.incrementAssignments();

        counters.incrementAssignments();
        for (int i = 2; i <= n; i++) {
            counters.incrementComparisons();
            counters.incrementAssignments();

            counters.incrementComparisons();
            if (prime[i]) {

                resultPrimes.add(i);
                counters.incrementAssignments();
            }
        }
        counters.incrementComparisons();

        return resultPrimes;
    }

    @Override
    public int getCoreLinesOfCode() {
        // Contando líneas de lógica principal en findPrimes.
        // Excluyendo comentarios, llaves solas, declaraciones y líneas de medición.
        return 30;
    }
}
