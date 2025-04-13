package algorithms;

import benchmark.PerformanceCounters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementación del test de primalidad de Miller-Rabin adaptada para las mediciones.
 * Nota: Miller-Rabin es un test para números individuales. Usarlo para
 * generar una lista completa de primos hasta N (llamándolo para cada número)
 * es muy ineficiente comparado con las cribas. Se incluye para fines
 * comparativos según lo solicitado en el proyecto.
 *
 * Código base original obtenido de:
 * https://github.com/wgimson/MillerRabin/blob/master/myMillerRabinImplementation.java
 * Autor Original: William Gimson
 */
public class MillerRabinTest implements PrimeAlgorithm {
    private static final Random rnd = new Random();
    // Número de iteraciones para la precisión (del código original)
    private static final int MILLER_RABIN_ITERATIONS = 20;

    @Override
    public String getName() {
        return "Miller-Rabin";
    }

    @Override
    public List<Integer> findPrimes(int n, PerformanceCounters counters) {
        List<Integer> primes = new ArrayList<>();
        counters.incrementAssignments();

        BigInteger limitBig = BigInteger.valueOf(n);
        counters.incrementAssignments();

        counters.incrementAssignments(); // i = TWO
        for (BigInteger i = BigInteger.TWO;
             i.compareTo(limitBig) <= 0; // Comparación del bucle
             i = i.add(BigInteger.ONE)) { // Incremento del bucle

            counters.incrementComparisons(); // i <= limitBig
            counters.incrementAssignments(); // i = i.add(ONE)

            counters.incrementComparisons(); // if (millerRabin...)
            if (millerRabin(i, counters)) {

                try {
                    primes.add(i.intValueExact());
                    counters.incrementAssignments();

                } catch (ArithmeticException e) {
                    System.err.printf("Advertencia: El número primo %s es demasiado grande para caber en un int.%n", i);
                    break;
                }
            }
        }
        counters.incrementComparisons();

        return primes;
    }

    /**
     * Adaptación de millerRabin del código original para usar contadores.
     * Realiza múltiples pasadas de millerRabinPass para aumentar la certeza.
     *
     * @param n        El número a probar.
     * @param counters Objeto para contar operaciones.
     * @return true si n es probablemente primo, false si es compuesto.
     */
    private boolean millerRabin(BigInteger n, PerformanceCounters counters) {
        counters.incrementComparisons(); // n < 2
        if (n.compareTo(BigInteger.TWO) < 0) return false;
        counters.incrementComparisons(); // n == 2
        if (n.equals(BigInteger.TWO)) return true;
        counters.incrementComparisons(); // n % 2 == 0
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        counters.incrementAssignments(); // iter = 0
        for (int iter = 0; iter < MILLER_RABIN_ITERATIONS; iter++) {
            counters.incrementComparisons(); // iter < ITERATIONS
            counters.incrementAssignments(); // iter++

            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
                counters.incrementAssignments(); // (Contador)

                counters.incrementComparisons(2); // a == 0 || a >= n
            } while (a.equals(BigInteger.ZERO) || a.compareTo(n) >= 0);
            counters.incrementComparisons();

            counters.incrementComparisons();
            if (!millerRabinPass(a, n, counters)) {
                return false;
            }
        }
        counters.incrementComparisons();

        return true;
    }

    /**
     * Adaptación de millerRabinPass del código original para usar contadores.
     * Realiza la prueba de Miller-Rabin para un testigo 'a' específico.
     *
     * @param a        El testigo aleatorio.
     * @param n        El número a probar.
     * @param counters Objeto para contar operaciones.
     * @return true si 'a' no prueba que 'n' es compuesto, false si lo prueba.
     */
    private boolean millerRabinPass(BigInteger a, BigInteger n, PerformanceCounters counters) {
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        counters.incrementAssignments();

        BigInteger d = nMinusOne;
        counters.incrementAssignments();

        int s = d.getLowestSetBit();
        counters.incrementAssignments();

        d = d.shiftRight(s);
        counters.incrementAssignments();

        BigInteger aToPower = a.modPow(d, n);
        counters.incrementAssignments();

        counters.incrementComparisons(); // aToPower == 1
        if (aToPower.equals(BigInteger.ONE)) return true;

        counters.incrementAssignments(); // i = 0
        for (int i = 0; i < s; i++) {
            counters.incrementComparisons(); // i < s
            counters.incrementAssignments(); // i++

            counters.incrementComparisons(); // aToPower == nMinusOne
            if (aToPower.equals(nMinusOne)) return true;

            counters.incrementComparisons(); // i < s - 1
            if (i < s - 1) {
                aToPower = aToPower.modPow(BigInteger.TWO, n);
                counters.incrementAssignments();
            }
        }
        counters.incrementComparisons();

        return false;
    }

    @Override
    public int getCoreLinesOfCode() {
        // Contando líneas de lógica esencial en findPrimes (10), millerRabin (16), y millerRabinPass (16).
        // Excluye comentarios, llaves solas, declaraciones y líneas de llamadas a 'counters'.
        return 42;
    }
}

