import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSort <T extends Comparable<? super T>> extends RecursiveAction{

    private static final int THRESHOLD = 100;

    private final T[] array;
    private final T[] auxiliary;
    private final int low;
    private final int high;

    public MergeSort(T[] array, T[] auxiliary, int low, int high) {
        this.array = array;
        this.auxiliary = auxiliary;
        this.low = low;
        this.high = high;
    }

    public static <T extends Comparable<? super T>> void sort(
        T[] array, ForkJoinPool pool
    ) {

        if (array == null || pool == null) {
            throw new IllegalArgumentException("El arreglo y el pool no pueden ser nulos");
        }

        if (array.length > 1) {
            T[] auxiliary = array.clone();
            pool.invoke(new MergeSort<>(array, auxiliary, 0, array.length - 1));
        }
    }

    @Override
    protected void compute() {

        int size = high - low + 1;

        if (size <= 1) {
            return;
        }

        if (size <= THRESHOLD) {
            insertionSort();
            return;
        }

        int middle = low + (high - low) / 2;

        MergeSort<T> leftTask = new MergeSort<>(array, auxiliary, low, middle);

        MergeSort<T> rightTask = new MergeSort<>(array, auxiliary, middle + 1, high);

        invokeAll(leftTask, rightTask);

        merge(middle);
    }

    private void merge(int middle) {

        System.arraycopy(array, low, auxiliary, low, high - low + 1);

        int leftIndex = low;
        int rightIndex = middle + 1;
        int destinationIndex = low;

        while (leftIndex <= middle && rightIndex <= high) {
            if (auxiliary[leftIndex].compareTo(auxiliary[rightIndex]) <= 0) {
                array[destinationIndex++] = auxiliary[leftIndex++];
            } else {
                array[destinationIndex++] = auxiliary[rightIndex++];
            }
        }

        while (leftIndex <= middle) {
            array[destinationIndex++] = auxiliary[leftIndex++];
        }

        while (rightIndex <= high) {
            array[destinationIndex++] = auxiliary[rightIndex++];
        }
    }

    private void insertionSort() {

        for (int index = low + 1; index <= high; index++) {
            T currentElement = array[index];
            int position = index - 1;

            while (position >= low && array[position].compareTo(currentElement) > 0) {
                array[position + 1] = array[position];
                position--;
            }

            array[position + 1] = currentElement;
        }
    }

    public static void main(String[] args) {
        
        int size = args.length >= 1 ? Integer.parseInt(args[0]) : 100_000;
        int parallelism = args.length >= 2 ? Integer.parseInt(args[1]) : Runtime.getRuntime().availableProcessors();

        Integer[] array = ArrayGenerator.ramdomIntegers(size, 42L);

        ForkJoinPool pool = new ForkJoinPool(parallelism);

        long startTime = System.currentTimeMillis();
        sort(array, pool);
        long endTime = System.currentTimeMillis();

        pool.shutdown();
    
        System.out.println("Algoritmo MergeSort con ForkJoinPool");
        System.out.println("Elementos: " + size);
        System.out.println("Hilos: " + parallelism);
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
        System.out.println("Arreglo ordenado: " + ArrayGenerator.isSorted(array));
    
    }
}
