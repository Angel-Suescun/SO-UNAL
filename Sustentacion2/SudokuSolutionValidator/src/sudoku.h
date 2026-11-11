// Configura el compilador para disponer de las definiciones necesarias.
#ifndef SUDOKU_H
// Ejecuta el paso correspondiente del algoritmo.
# define SUDOKU_H

// Configura el compilador para disponer de las definiciones necesarias.
#include <array>
// Configura el compilador para disponer de las definiciones necesarias.
#include <string>

// Ejecuta el paso correspondiente del proceso de validación.
constexpr int GRID_SIZE = 9;

// Ejecuta el paso correspondiente del proceso de validación.
using SudokuGrid = std::array<std::array<int, GRID_SIZE>, GRID_SIZE>;

// Ejecuta el paso correspondiente del proceso de validación.
bool loadSudokuFromFile(const std::string& filepath, SudokuGrid& grid);

// Configura el compilador para disponer de las definiciones necesarias.
#endif
