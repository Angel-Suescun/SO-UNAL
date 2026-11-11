import java.util.SplittableRandom;

public final class ArrayGenerator {
    
    private ArrayGenerator() {}

    public static Integer[] ramdomIntegers(int size, long seed) {

        if (size < 1) {
   
            throw new IllegalArgumentException("El tamaño del arreglo debe ser mayor a 0");
   
        }

        Integer[] array = new Integer[size];
        SplittableRandom random = new SplittableRandom(seed);

        for (int index = 0; index < size; index++){

            array[index] = random.nextInt(1_000_001);
        
        }
   
        return array;
   
    }

    public static boolean isSorted(Integer[] array) {
   
        for (int index = 1; index < array.length; index++) {
   
            if (array[index - 1] > array[index]) {
   
                return false;
   
            }
        } 
        
 
        return true;
 
    }
}