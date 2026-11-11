// Configura el compilador para disponer de las definiciones necesarias.
#include "sudoku.h"

// Configura el compilador para disponer de las definiciones necesarias.
#include <fstream>
// Configura el compilador para disponer de las definiciones necesarias.
#include <iostream>
// Configura el compilador para disponer de las definiciones necesarias.
#include <string>

// Ejecuta el paso correspondiente del algoritmo.
bool loadSudokuFromFile(const std::string& filepath, SudokuGrid& grid) {
    // Ejecuta el paso correspondiente del proceso de validación.
    std::ifstream inputFile(filepath);

    // Ejecuta el paso correspondiente del algoritmo.
    if (!inputFile.is_open()) {

        // Ejecuta el paso correspondiente del proceso de validación.
        std::cerr << "Error> No se pudo abrir el archivo: " << filepath << '\n';

        // Informa al llamador que la operación o validación falló.
        return false;

    // Cierra el bloque de ejecución de condición actual.
    }

    // Recorre las celdas de la unidad que se está comprobando.
    for (int row = 0; row < GRID_SIZE; row++) {
        // Recorre las celdas de la unidad que se está comprobando.
        for (int column = 0; column < GRID_SIZE; column++) {
            // Comprueba que el archivo aporte un valor para cada celda del Sudoku.
            if (!(inputFile >> grid[row][column])) {
                // Ejecuta el paso correspondiente del proceso de validación.
                std::cerr << "Error: Sudoku incompleto o inválido en el archivo: " << filepath << '\n';
                // Informa al llamador que la operación o validación falló.
                return false;
            // Cierra el bloque de ejecución de condición actual.
            }
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del proceso de validación.
    std::string extraValue;

    // Comprueba que el archivo no contenga valores después de las 81 celdas esperadas.
    if (inputFile >> extraValue) {
        // Ejecuta el paso correspondiente del proceso de validación.
        std::cerr << "Error: Sudoku tiene valores adicionales en el archivo: " << filepath << '\n';
        // Informa al llamador que la operación o validación falló.
        return false;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Informa al llamador que la operación o validación fue exitosa.
    return true;
// Cierra el bloque de ejecución de condición actual.
}
