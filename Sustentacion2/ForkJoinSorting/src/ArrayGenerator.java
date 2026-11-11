// Importa la dependencia necesaria para ejecutar esta parte del algoritmo.
import java.util.SplittableRandom;

// Ejecuta el paso correspondiente del algoritmo.
public final class ArrayGenerator {

    // Ejecuta el paso correspondiente del algoritmo.
    private ArrayGenerator() {
    // Cierra el bloque de ejecución de ArrayGenerator.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    public static Integer[] ramdomIntegers(int size, long seed) {

        // Ejecuta el paso correspondiente del algoritmo.
        if (size < 1) {
            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            throw new IllegalArgumentException("El tamaño del arreglo debe ser mayor a 0");
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        Integer[] array = new Integer[size];
        // Ejecuta el paso del algoritmo indicado por esta instrucción.
        SplittableRandom random = new SplittableRandom(seed);

        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int index = 0; index < size; index++){

            // Ejecuta el paso del algoritmo indicado por esta instrucción.
            array[index] = random.nextInt(1_000_001);

        // Cierra el bloque de ejecución de recorrido actual.
        }
        // Finaliza esta operación y entrega su resultado al método que la llamó.
        return array;
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Comprueba que el resultado quedó ordenado antes de reportar la medición.
    public static boolean isSorted(Integer[] array) {
        // Recorre los índices del segmento para aplicar el paso correspondiente del ordenamiento.
        for (int index = 1; index < array.length; index++) {
            // Ejecuta el paso correspondiente del algoritmo.
            if (array[index - 1] > array[index]) {
                // Informa al llamador que la operación o validación falló.
                return false;
            // Cierra el bloque de ejecución de condición actual.
            }
        // Cierra el bloque de ejecución de condición actual.
        }

        // Informa al llamador que la operación o validación fue exitosa.
        return true;
    // Cierra el bloque de ejecución de condición actual.
    }
// Cierra el bloque de ejecución de condición actual.
}
