import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;



public class Benchmark {
    
    public static final int[] SIZES = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000};

    private static final int WARMUP_SIZE = 100_000;

    private static final int WARMUP_ITERATIONS = 2;

    public static void main(String[] args) throws IOException {
        
        int repetitions = args.length >= 1 ? Integer.parseInt(args[0]) : 5;

        int parallelism = args.length >= 2 ? Integer.parseInt(args[1]) : Runtime.getRuntime().availableProcessors();

        if (repetitions < 1 || parallelism < 1) {

            System.err.println("El número de repeticiones y el paralelismo deben ser mayores a 0");
            
            return;
        
        }

        ForkJoinPool pool = new ForkJoinPool(parallelism);

        try {
            Files.createDirectories(Path.of("results"));

            try (BufferedWriter writer = Files.newBufferedWriter(Path.of("results", "benchmark.csv"))) {
        
                writer.write("algorithm,size,threads,repetition,time_ms");
        
                writer.newLine();

        
                System.out.println("Iniciando benchmark...");
                warmUp(pool);

                System.out.println("Resultados del benchmark:");
                System.out.println("Hilos: " + parallelism);
                System.out.println("Repeticiones: " + repetitions);
                System.out.println();

                for (int size : SIZES) {
        
                    benchmarkSize(size, repetitions, pool, writer);
        
                }
        
            }
        } finally {
        
            pool.shutdown();
        
        }

        System.out.println("Benchmark completado. Resultados guardados en 'results/benchmark.csv'");

    }

    private static void warmUp(ForkJoinPool pool) {
        
        for (int repetition = 0; repetition < WARMUP_ITERATIONS; repetition++){

            Integer[] originalArray = ArrayGenerator.ramdomIntegers(WARMUP_SIZE, 2026L + repetition);

            QuickSort.sort(originalArray.clone(), pool);
            MergeSort.sort(originalArray.clone(), pool);
        }
    }

    private static void benchmarkSize(int size, int repetitions, ForkJoinPool pool, BufferedWriter writer) throws IOException {
        
        for (int repetition = 1; repetition <= repetitions; repetition++){

            Integer[] originalArray = ArrayGenerator.ramdomIntegers(size, 2026L + (long) size * 31 + repetition);

            Integer[] quickSortArray = originalArray.clone();
            Integer[] mergeSortArray = originalArray.clone();

            long quickTime;
            long mergeTime;

            if (repetition % 2 == 1) {
        
                quickTime = measureQuickSort(quickSortArray, pool);
                mergeTime = measureMergeSort(mergeSortArray, pool);
        
            } else {
        
                mergeTime = measureMergeSort(mergeSortArray, pool);
                quickTime = measureQuickSort(quickSortArray, pool);
        
            }

            if (!ArrayGenerator.isSorted(quickSortArray) || !ArrayGenerator.isSorted(mergeSortArray)) {
        
                throw new IllegalStateException("El arreglo no está ordenado correctamente");
        
            }

            double quickTimeMs = quickTime / 1_000_000.0;
            double mergeTimeMs = mergeTime / 1_000_000.0;

            double quickSortTotalTime = 0;
            double mergeSortTotalTime = 0;

            quickSortTotalTime += quickTimeMs;
            mergeSortTotalTime += mergeTimeMs;

            writeResult(writer, "QuickSort", size, pool.getParallelism(), repetition, quickTimeMs);

            writeResult(writer, "MergeSort", size, pool.getParallelism(), repetition, mergeTimeMs);

            System.out.printf(Locale.ROOT, "Tamaño: %d, QuickSort: %10.3f ms, MergeSort: %10.3f ms%n", size, quickTimeMs, mergeTimeMs);
        }
    }

    private static long measureQuickSort(Integer[] array, ForkJoinPool pool) {

        long startTime = System.nanoTime();
    
        QuickSort.sort(array, pool);
    
        return System.nanoTime() - startTime;
    
    }

    private static long measureMergeSort(Integer[] array, ForkJoinPool pool) {

        long startTime = System.nanoTime();
    
        MergeSort.sort(array, pool);
    
        return System.nanoTime() - startTime;
    
    }

    private static void writeResult(
            BufferedWriter writer,
            String algorithm,
            int size,
            int threads,
            int repetition,
            double timeMs) throws IOException {

        writer.write(String.format(Locale.ROOT, "%s,%d,%d,%d,%.6f",
                algorithm, size, threads, repetition, timeMs));
    
        writer.newLine();
    }
}
