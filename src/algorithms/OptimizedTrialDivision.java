package algorithms;

import benchmark.PerformanceCounters;

import java.util.ArrayList;
import java.util.List;

// Referencia: https://www.geeksforgeeks.org/how-to-find-prime-numbers
public class OptimizedTrialDivision implements PrimeAlgorithm {

    @Override
    public String getName() {
        return "División por tentativa optimizado";
    }

    @Override
    public int getCoreLinesOfCode() {
        return 15;
    }

    @Override
    public List<Integer> findPrimes(int n, PerformanceCounters counters) {
        List<Integer> primeList = new ArrayList<>(); // 1

        counters.incrementAssignments();
        for (int i = 2; i <= n; i++) { // 2
            counters.incrementComparisons();
            counters.incrementAssignments();

            counters.incrementComparisons();
            if (isPrime(i, counters)) { // 3
                primeList.add(i); // 4
                counters.incrementAssignments();
            }
        }
        counters.incrementComparisons();

        return primeList; // 5
    }


    public boolean isPrime(int number, PerformanceCounters counters) {

        counters.incrementComparisons();
        if (number <= 1) { // 6
            return false; // 7
        }

        counters.incrementComparisons();
        if (number <= 3) { // 8
            return true; // 9
        }

        counters.incrementComparisons(2);
        if (number % 2 == 0 || number % 3 == 0) { // 10
            return false; // 11
        }

        // Finalizar en el numero elevado al cuadrado
        counters.incrementAssignments();
        for (int i = 5; i * i <= number; i = i + 6) { // 12
            counters.incrementComparisons();

            counters.incrementComparisons(2);
            if (number % i == 0 || number % (i + 2) == 0) { // 13
                return false; // 14
            }

            counters.incrementAssignments();
        }
        counters.incrementComparisons();

        // Si no encontró divisores, es primo

        return true; // 15
    }
}
