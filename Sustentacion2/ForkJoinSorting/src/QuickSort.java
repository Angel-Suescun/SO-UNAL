// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.concurrent.ForkJoinPool;
// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.concurrent.RecursiveAction;

// Define la tarea Fork/Join que divide y ordena segmentos mediante QuickSort.
public class QuickSort<T extends Comparable<? super T>> extends  RecursiveAction{

    // Fija el tamaño a partir del cual conviene usar ordenamiento secuencial en vez de crear más tareas.
    private static final int THRESHOLD = 100;

    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final T[] array;
    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final int low;
    // Ejecuta el paso del algoritmo indicado por esta instrucción.
    private final int high;

    // Ejecuta el paso correspondiente del algoritmo.
    public QuickSort(T[] array, int low, int high) {
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.array = array;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.low = low;
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        this.high = high;
    // Cierra el bloque de ejecución de QuickSort.
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
            // Entrega el arreglo completo al ForkJoinPool para iniciar el ordenamiento paralelo.
            pool.invoke(new QuickSort<>(array, 0, array.length - 1));
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
        if (size <= THRESHOLD){
            // Ordena directamente el segmento pequeño, evitando crear tareas paralelas innecesarias.
            insertionSort();
            // Finaliza esta operación y entrega su resultado al método que la llamó.
            return;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Calcula la posición de separación usando el pivote elegido.
        int pivotPosition = partition();

        // Crea la tarea que ordenará recursivamente la mitad izquierda del segmento.
        QuickSort<T> leftTask = new QuickSort<>(array, low, pivotPosition );

        // Crea la tarea que ordenará recursivamente la mitad derecha del segmento.
        QuickSort<T> rightTask = new QuickSort<>(array, pivotPosition + 1, high);

        // Envía las dos mitades al framework Fork/Join para que puedan ejecutarse en paralelo y espera su finalización.
        invokeAll(leftTask, rightTask);
    // Cierra el bloque de ejecución de partition.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private int partition() {

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        int mid = low + (high - low) / 2;

        // Elige como pivote la mediana entre el primer, medio y último elemento para evitar particiones muy desequilibradas.
        T pivot = medianOfThree(array[low], array[mid], array[high]);

        // Coloca los índices de partición fuera del segmento para avanzar desde ambos extremos.
        int left = low - 1;
        // Inicializa el índice derecho antes del último elemento para comenzar el escaneo hacia la izquierda.
        int right = high + 1;

        // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
        while (true) {
            // Ejecuta el paso correspondiente del algoritmo.
            do {
                // Avanza hasta encontrar desde la izquierda un elemento que no pertenezca a ese lado.
                left++;
            // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
            } while (array[left].compareTo(pivot) < 0);

            // Ejecuta el paso correspondiente del algoritmo.
            do {
                // Retrocede hasta encontrar desde la derecha un elemento que no pertenezca a ese lado.
                right--;
            // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
            } while (array[right].compareTo(pivot) > 0);

            // Comprueba si los índices se cruzaron y la partición ya terminó.
            if (left >= right) {
                // Finaliza esta operación y entrega su resultado al método que la llamó.
                return right;
            // Cierra el bloque de ejecución de condición actual.
            }

            // Intercambia elementos que quedaron en el lado incorrecto respecto al pivote.
            swap(left, right);
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Obtiene un pivote representativo usando tres posiciones del segmento.
    private T medianOfThree(T first, T middle, T last) {

        // Ejecuta el paso correspondiente del algoritmo.
        if (first.compareTo(middle) > 0) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            T temporary = first;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            first = middle;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            middle = temporary;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del algoritmo.
        if (middle.compareTo(last) > 0) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            T temporary = middle;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            middle = last;
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            last = temporary;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del algoritmo.
        if (first.compareTo(middle) > 0) {

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            middle = first;

        // Cierra el bloque de ejecución de condición actual.
        }
        // Finaliza esta operación y entrega su resultado al método que la llamó.
        return middle;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private void insertionSort() {

        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int index = low + 1; index <= high; index++) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            T key = array[index];
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            int position = index - 1;

            // Repite el desplazamiento o comparación hasta ubicar correctamente el elemento.
            while (position >= low && array[position].compareTo(key) > 0) {
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                array[position + 1] = array[position];
                // Ejecuta el paso del algoritmo indicado por esta instrucción.
                position--;
            // Cierra el bloque de ejecución de recorrido actual.
            }


            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            array[position + 1] = key;
        // Cierra el bloque de ejecución de recorrido actual.
        }
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    private void swap(int first, int second) {
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        T temporary = array[first];
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        array[first] = array[second];
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        array[second] = temporary;
    // Cierra el bloque de ejecución de swap.
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
        long startTime = System.nanoTime();
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        sort(array, pool);
        // Toma una marca de tiempo para medir la duración del ordenamiento.
        long endTime = System.nanoTime();

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        pool.shutdown();

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Algoritmo QuickSort");
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Tamaño del arreglo: " + size);
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Hilos: " + parallelism);
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1_000_000 + " ms");
        // Comprueba que el resultado quedó ordenado antes de reportar la medición.
        System.out.println("Correctamente ordenado: " + ArrayGenerator.isSorted(array));
    // Cierra el bloque de ejecución de Integer.parseInt.
    }
// Cierra el bloque de ejecución de Integer.parseInt.
}


