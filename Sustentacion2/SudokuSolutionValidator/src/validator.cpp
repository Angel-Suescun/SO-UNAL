// Configura el compilador para disponer de las definiciones necesarias.
#include "validator.h"

// Configura el compilador para disponer de las definiciones necesarias.
#include <array>

// Comprueba que una fila contenga exactamente los números del 1 al 9 sin repetir.
bool validateRow(const SudokuGrid& grid, int row) {


    // Crea una tabla para marcar los números del 1 al 9 que ya aparecieron en la unidad.
    std::array<bool, 10> seen = {};

    // Recorre las celdas de la unidad que se está comprobando.
    for (int column = 0; column < GRID_SIZE; column++) {

        // Ejecuta el paso correspondiente del proceso de validación.
        int value = grid[row][column];

        // Detecta un número fuera de rango o repetido en la fila, columna o subcuadro.
        if (value < 1 || value > 9 || seen[value]) {

            // Informa al llamador que la operación o validación falló.
            return false;

        // Cierra el bloque de ejecución de condición actual.
        }

        // Marca el número como encontrado para detectar duplicados posteriores.
        seen[value] = true;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Informa al llamador que la operación o validación fue exitosa.
    return true;

// Cierra el bloque de ejecución de condición actual.
}

// Comprueba que una columna contenga exactamente los números del 1 al 9 sin repetir.
bool validateColumn(const SudokuGrid& grid, int column) {


    // Crea una tabla para marcar los números del 1 al 9 que ya aparecieron en la unidad.
    std::array<bool, 10> seen = {};

    // Recorre las celdas de la unidad que se está comprobando.
    for (int row = 0; row < GRID_SIZE; row++) {

        // Ejecuta el paso correspondiente del proceso de validación.
        int value = grid[row][column];

        // Detecta un número fuera de rango o repetido en la fila, columna o subcuadro.
        if (value < 1 || value > 9 || seen[value]) {

            // Informa al llamador que la operación o validación falló.
            return false;

        // Cierra el bloque de ejecución de condición actual.
        }

        // Marca el número como encontrado para detectar duplicados posteriores.
        seen[value] = true;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Informa al llamador que la operación o validación fue exitosa.
    return true;

// Cierra el bloque de ejecución de condición actual.
}


// Comprueba que un subcuadro 3x3 contenga números válidos y no repetidos.
bool validateSubgrid(const SudokuGrid& grid, int startRow, int startColumn) {

    // Crea una tabla para marcar los números del 1 al 9 que ya aparecieron en la unidad.
    std::array<bool, 10> seen = {};

    // Recorre las celdas de la unidad que se está comprobando.
    for (int row = startRow; row < startRow + 3; row++) {

        // Recorre las celdas de la unidad que se está comprobando.
        for (int column = startColumn; column < startColumn + 3; column++) {

            // Ejecuta el paso correspondiente del proceso de validación.
            int value = grid[row][column];

            // Detecta un número fuera de rango o repetido en la fila, columna o subcuadro.
            if (value < 1 || value > 9 || seen[value]) {

                // Informa al llamador que la operación o validación falló.
                return false;

            // Cierra el bloque de ejecución de condición actual.
            }

            // Marca el número como encontrado para detectar duplicados posteriores.
            seen[value] = true;
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Informa al llamador que la operación o validación fue exitosa.
    return true;

// Cierra el bloque de ejecución de condición actual.
}

// Ejecuta secuencialmente las validaciones de filas, columnas y subcuadros.
bool validateSudoku(const SudokuGrid& grid) {

    // Parte de la hipótesis de que el Sudoku es válido y la irá descartando si encuentra errores.
    bool valid = true;

    // Recorre las celdas de la unidad que se está comprobando.
    for (int row = 0; row < GRID_SIZE; row++) {

        // Acumula la validez de la fila sin perder errores encontrados anteriormente.
        valid &= validateRow(grid, row);
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Recorre las celdas de la unidad que se está comprobando.
    for (int column = 0; column < GRID_SIZE; column++) {

        // Acumula la validación de la columna con el resultado global.
        valid &= validateColumn(grid, column);
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    for (int startRow = 0; startRow < GRID_SIZE; startRow += 3) {

        // Ejecuta el paso correspondiente del algoritmo.
        for (int startColumn = 0; startColumn < GRID_SIZE; startColumn += 3) {

            // Acumula la validación de cada subcuadro 3x3.
            valid &= validateSubgrid(grid, startRow, startColumn);

        // Cierra el bloque de ejecución de recorrido actual.
        }
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Devuelve el resultado de la comprobación al llamador.
    return valid;
// Cierra el bloque de ejecución de recorrido actual.
}
