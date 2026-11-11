// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.concurrent.ForkJoinPool;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.concurrent.RecursiveAction;

// Define la tarea Fork/Join que divide, ordena y fusiona segmentos mediante MergeSort.
public class MergeSort <T extends Comparable<? super T>> extends RecursiveAction{

    // Fija el tamaño a partir del cual conviene usar ordenamiento secuencial en vez de crear más tareas.
    private static final int THRESHOLD = 100;

    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final T[] array;
    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final T[] auxiliary;
    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final int low;
    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final int high;

    // Ejecuta el paso correspondiente del algoritmo.
    public MergeSort(T[] array, T[] auxiliary, int low, int high) {
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.array = array;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.auxiliary = auxiliary;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.low = low;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.high = high;
    // Cierra el bloque de ejecución de MergeSort.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    public static <T extends Comparable<? super T>> void sort(
        // Ejecuta el paso correspondiente del algoritmo.
        T[] array, ForkJoinPool pool
    // Ejecuta el paso correspondiente del algoritmo.
    ) {

        // Ejecuta el paso correspondiente del algoritmo.
        if (array == null || pool == null) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            throw new IllegalArgumentException("El arreglo y el pool no pueden ser nulos");
        // Cierra el bloque de ejecución de condición actual.
        }

        // Evita iniciar trabajo cuando el arreglo ya está vacío o contiene un solo elemento.
        if (array.length > 1) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            T[] auxiliary = array.clone();
            // Inicia el MergeSort paralelo sobre todo el arreglo usando el arreglo auxiliar compartido.
            pool.invoke(new MergeSort<>(array, auxiliary, 0, array.length - 1));
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    @Override
    // Ejecuta el paso correspondiente del algoritmo.
    protected void compute() {

        // Calcula cuántos elementos contiene el segmento que esta tarea debe ordenar.
        int size = high - low + 1;

        // Detiene la recursión porque un segmento de hasta un elemento ya está ordenado.
        if (size <= 1) {
            // Finaliza esta operación y entrega su resultado al método que la llamó.
            return;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Fija el tamaño a partir del cual conviene usar ordenamiento secuencial en vez de crear más tareas.
        if (size <= THRESHOLD) {
            // Ordena directamente el segmento pequeño, evitando crear tareas paralelas innecesarias.
            insertionSort();
            // Finaliza esta operación y entrega su resultado al método que la llamó.
            return;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Calcula el punto medio del segmento para dividirlo en dos partes.
        int middle = low + (high - low) / 2;

        // Crea la tarea de MergeSort para ordenar la mitad izquierda.
        MergeSort<T> leftTask = new MergeSort<>(array, auxiliary, low, middle);

        // Crea la tarea de MergeSort para ordenar la mitad derecha.
        MergeSort<T> rightTask = new MergeSort<>(array, auxiliary, middle + 1, high);

        // Envía las dos mitades al framework Fork/Join para que puedan ejecutarse en paralelo y espera su finalización.
        invokeAll(leftTask, rightTask);

        // Combina las dos mitades ya ordenadas en un único segmento ordenado.
        merge(middle);
    // Cierra el bloque de ejecución de +.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private void merge(int middle) {

        // Copia el segmento original al auxiliar para leer ambas mitades sin sobrescribir datos pendientes.
        System.arraycopy(array, low, auxiliary, low, high - low + 1);

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int leftIndex = low;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int rightIndex = middle + 1;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int destinationIndex = low;

        // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
        while (leftIndex <= middle && rightIndex <= high) {
            // Ejecuta el paso correspondiente del algoritmo.
            if (auxiliary[leftIndex].compareTo(auxiliary[rightIndex]) <= 0) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                array[destinationIndex++] = auxiliary[leftIndex++];
            // Ejecuta el paso correspondiente del algoritmo.
            } else {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                array[destinationIndex++] = auxiliary[rightIndex++];
            // Cierra el bloque de ejecución de condición actual.
            }
        // Cierra el bloque de ejecución de condición actual.
        }

        // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
        while (leftIndex <= middle) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            array[destinationIndex++] = auxiliary[leftIndex++];
        // Cierra el bloque de ejecución de condición actual.
        }

        // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
        while (rightIndex <= high) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            array[destinationIndex++] = auxiliary[rightIndex++];
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private void insertionSort() {

        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int index = low + 1; index <= high; index++) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            T currentElement = array[index];
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            int position = index - 1;

            // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
            while (position >= low && array[position].compareTo(currentElement) > 0) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                array[position + 1] = array[position];
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                position--;
            // Cierra el bloque de ejecución de recorrido actual.
            }

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            array[position + 1] = currentElement;
        // Cierra el bloque de ejecución de recorrido actual.
        }
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    public static void main(String[] args) {

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int size = args.length >= 1 ? Integer.parseInt(args[0]) : 100_000;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int parallelism = args.length >= 2 ? Integer.parseInt(args[1]) : Runtime.getRuntime().availableProcessors();

        // Genera el arreglo de entrada con valores reproducibles para comparar los algoritmos.
        Integer[] array = ArrayGenerator.ramdomIntegers(size, 42L);

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        ForkJoinPool pool = new ForkJoinPool(parallelism);

        // Toma una marca de tiempo para medir la duración del ordenamiento.
        long startTime = System.currentTimeMillis();
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        sort(array, pool);
        // Toma una marca de tiempo para medir la duración del ordenamiento.
        long endTime = System.currentTimeMillis();

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        pool.shutdown();

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Algoritmo MergeSort con ForkJoinPool");
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Elementos: " + size);
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Hilos: " + parallelism);
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
        // Comprueba que el resultado quedó ordenado antes de reportar la medición.
        System.out.println("Arreglo ordenado: " + ArrayGenerator.isSorted(array));

    // Cierra el bloque de ejecución de Integer.parseInt.
    }
// Cierra el bloque de ejecución de Integer.parseInt.
}
