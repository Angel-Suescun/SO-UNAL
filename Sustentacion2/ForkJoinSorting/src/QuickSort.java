import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort<T extends Comparable<? super T>> extends  RecursiveAction{
    
    private static final int THRESHOLD = 100;
    
    private final T[] array;
    private final int low;
    private final int high;

    public QuickSort(T[] array, int low, int high) {
        this.array = array;
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
      
            pool.invoke(new QuickSort<>(array, 0, array.length - 1));
      
        }
    }

    @Override 
    protected void compute() {

        int size = high - low + 1;

        if (size <= 1) {
            return;
        }

        if (size <= THRESHOLD){
            insertionSort();
            return;
        }

        int pivotPosition = partition();

        QuickSort<T> leftTask = new QuickSort<>(array, low, pivotPosition );

        QuickSort<T> rightTask = new QuickSort<>(array, pivotPosition + 1, high);

        invokeAll(leftTask, rightTask);
    }

    private int partition() {

        int mid = low + (high - low) / 2;

        T pivot = medianOfThree(array[low], array[mid], array[high]);
        
        int left = low - 1;
        int right = high + 1;

        while (true) {
            do {
         
                left++;
         
            } while (array[left].compareTo(pivot) < 0);

            do {
         
                right--;
         
            } while (array[right].compareTo(pivot) > 0);

            if (left >= right) {
         
                return right;
         
            }

            swap(left, right);
        }         
    }

    private T medianOfThree(T first, T middle, T last) {
        
        if (first.compareTo(middle) > 0) {
       
            T temporary = first;
            first = middle;
            middle = temporary;
       
        }

        if (middle.compareTo(last) > 0) {
       
            T temporary = middle;
            middle = last;
            last = temporary;
       
        }

        if (first.compareTo(middle) > 0) {
            
            middle = first; 

        }
       
        return middle;
    }

    private void insertionSort() {
        
        for (int index = low + 1; index <= high; index++) {
       
            T key = array[index];
            int position = index - 1;

            while (position >= low && array[position].compareTo(key) > 0) {
       
                array[position + 1] = array[position];
       
                position--;
       
            }

    
            array[position + 1] = key;
        }
    }

    private void swap(int first, int second) {
        T temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }

    public static void main(String[] args) {
        
        int size = args.length >= 1 ? Integer.parseInt(args[0]) : 100_000;
        int parallelism = args.length >= 2 ? Integer.parseInt(args[1]) : Runtime.getRuntime().availableProcessors();

        Integer[] array = ArrayGenerator.ramdomIntegers(size, 42L);

        ForkJoinPool pool = new ForkJoinPool(parallelism);

        long startTime = System.nanoTime();
        sort(array, pool);
        long endTime = System.nanoTime();

        pool.shutdown();

        System.out.println("Algoritmo QuickSort");
        System.out.println("Tamaño del arreglo: " + size);
        System.out.println("Hilos: " + parallelism);
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Correctamente ordenado: " + ArrayGenerator.isSorted(array));
    }
}

    