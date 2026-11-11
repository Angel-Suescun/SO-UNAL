// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.io.BufferedWriter;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.io.IOException;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.nio.file.Files;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.nio.file.Path;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.Locale;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.concurrent.ForkJoinPool;



// Ejecuta el paso correspondiente del algoritmo.
public class Benchmark {

    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    public static final int[] SIZES = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000};

    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private static final int WARMUP_SIZE = 100_000;

    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private static final int WARMUP_ITERATIONS = 2;

    // Ejecuta el paso correspondiente del algoritmo.
    public static void main(String[] args) throws IOException {

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int repetitions = args.length >= 1 ? Integer.parseInt(args[0]) : 5;

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int parallelism = args.length >= 2 ? Integer.parseInt(args[1]) : Runtime.getRuntime().availableProcessors();

        // Ejecuta el paso correspondiente del algoritmo.
        if (repetitions < 1 || parallelism < 1) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            System.err.println("El número de repeticiones y el paralelismo deben ser mayores a 0");
            // Finaliza esta operación y entrega su resultado al método que la llamó.
            return;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        ForkJoinPool pool = new ForkJoinPool(parallelism);

        // Ejecuta el paso correspondiente del algoritmo.
        try {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            Files.createDirectories(Path.of("results"));

            // Ejecuta el paso correspondiente del algoritmo.
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of("results", "benchmark.csv"))) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                writer.write("algorithm,size,threads,repetition,time_ms");
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                writer.newLine();

                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                System.out.println("Iniciando benchmark...");
                // Ejecuta corridas previas para calentar la JVM y reducir el efecto de la compilación JIT.
                warmUp(pool);

                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                System.out.println("Resultados del benchmark:");
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                System.out.println("Hilos: " + parallelism);
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                System.out.println("Repeticiones: " + repetitions);
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                System.out.println();

                // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
                for (int size : SIZES) {
                    // Mide ambos algoritmos para un tamaño específico y sus repeticiones.
                    benchmarkSize(size, repetitions, pool, writer);
                // Cierra el bloque de ejecución de recorrido actual.
                }
            // Cierra el bloque de ejecución de recorrido actual.
            }
        // Ejecuta el paso correspondiente del algoritmo.
        } finally {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            pool.shutdown();
        // Cierra el bloque de ejecución de recorrido actual.
        }

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Benchmark completado. Resultados guardados en 'results/benchmark.csv'");

    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Ejecuta corridas previas para calentar la JVM y reducir el efecto de la compilación JIT.
    private static void warmUp(ForkJoinPool pool) {

        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int repetition = 0; repetition < WARMUP_ITERATIONS; repetition++){

            // Genera el arreglo de entrada con valores reproducibles para comparar los algoritmos.
            Integer[] originalArray = ArrayGenerator.ramdomIntegers(WARMUP_SIZE, 2026L + repetition);

            // Crea una copia independiente para que cada algoritmo reciba los mismos datos iniciales.
            QuickSort.sort(originalArray.clone(), pool);
            // Crea una copia independiente para que cada algoritmo reciba los mismos datos iniciales.
            MergeSort.sort(originalArray.clone(), pool);
        // Cierra el bloque de ejecución de recorrido actual.
        }
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Mide ambos algoritmos para un tamaño específico y sus repeticiones.
    private static void benchmarkSize(int size, int repetitions, ForkJoinPool pool, BufferedWriter writer) throws IOException {


        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int repetition = 1; repetition <= repetitions; repetition++){

            // Genera el arreglo de entrada con valores reproducibles para comparar los algoritmos.
            Integer[] originalArray = ArrayGenerator.ramdomIntegers(size, 2026L + (long) size * 31 + repetition);

            // Crea una copia independiente para que cada algoritmo reciba los mismos datos iniciales.
            Integer[] quickSortArray = originalArray.clone();
            // Crea una copia independiente para que cada algoritmo reciba los mismos datos iniciales.
            Integer[] mergeSortArray = originalArray.clone();

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            long quickTime;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            long mergeTime;

            // Ejecuta el paso correspondiente del algoritmo.
            if (repetition % 2 == 1) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                quickTime = measureQuickSort(quickSortArray, pool);
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                mergeTime = measureMergeSort(mergeSortArray, pool);
            // Ejecuta el paso correspondiente del algoritmo.
            } else {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                mergeTime = measureMergeSort(mergeSortArray, pool);
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                quickTime = measureQuickSort(quickSortArray, pool);
            // Cierra el bloque de ejecución de condición actual.
            }

            // Comprueba que el resultado quedó ordenado antes de reportar la medición.
            if (!ArrayGenerator.isSorted(quickSortArray) || !ArrayGenerator.isSorted(mergeSortArray)) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                throw new IllegalStateException("El arreglo no está ordenado correctamente");
            // Cierra el bloque de ejecución de condición actual.
            }

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            double quickTimeMs = quickTime / 1_000_000.0;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            double mergeTimeMs = mergeTime / 1_000_000.0;

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            double quickSortTotalTime = 0;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            double mergeSortTotalTime = 0;

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            quickSortTotalTime += quickTimeMs;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            mergeSortTotalTime += mergeTimeMs;

            // Guarda la medición del algoritmo y tamaño actuales en el archivo CSV.
            writeResult(writer, "QuickSort", size, pool.getParallelism(), repetition, quickTimeMs);

            // Guarda la medición del algoritmo y tamaño actuales en el archivo CSV.
            writeResult(writer, "MergeSort", size, pool.getParallelism(), repetition, mergeTimeMs);

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            System.out.printf(Locale.ROOT, "Tamaño: %d, QuickSort: %10.3f ms, MergeSort: %10.3f ms%n", size, quickTimeMs, mergeTimeMs);
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private static long measureQuickSort(Integer[] array, ForkJoinPool pool) {

        // Toma una marca de tiempo para medir la duración del ordenamiento.
        long startTime = System.nanoTime();
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        QuickSort.sort(array, pool);
        // Toma una marca de tiempo para medir la duración del ordenamiento.
        return System.nanoTime() - startTime;
    // Cierra el bloque de ejecución de measureQuickSort.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private static long measureMergeSort(Integer[] array, ForkJoinPool pool) {

        // Toma una marca de tiempo para medir la duración del ordenamiento.
        long startTime = System.nanoTime();
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        MergeSort.sort(array, pool);
        // Toma una marca de tiempo para medir la duración del ordenamiento.
        return System.nanoTime() - startTime;
    // Cierra el bloque de ejecución de measureMergeSort.
    }

    // Guarda la medición del algoritmo y tamaño actuales en el archivo CSV.
    private static void writeResult(
            // Ejecuta el paso correspondiente del algoritmo.
            BufferedWriter writer,
            // Ejecuta el paso correspondiente del algoritmo.
            String algorithm,
            // Ejecuta el paso correspondiente del algoritmo.
            int size,
            // Ejecuta el paso correspondiente del algoritmo.
            int threads,
            // Ejecuta el paso correspondiente del algoritmo.
            int repetition,
            // Ejecuta el paso correspondiente del algoritmo.
            double timeMs) throws IOException {

        // Ejecuta el paso correspondiente del algoritmo.
        writer.write(String.format(Locale.ROOT, "%s,%d,%d,%d,%.6f",
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                algorithm, size, threads, repetition, timeMs));
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        writer.newLine();
    // Cierra el bloque de ejecución de writeResult.
    }
// Cierra el bloque de ejecución de writeResult.
}
