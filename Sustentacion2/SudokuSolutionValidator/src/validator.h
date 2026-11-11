// Configura el compilador para disponer de las definiciones necesarias.
#ifndef VALIDATOR_H
// Configura el compilador para disponer de las definiciones necesarias.
#define VALIDATOR_H

// Configura el compilador para disponer de las definiciones necesarias.
#include "sudoku.h"

// Comprueba que una fila contenga exactamente los números del 1 al 9 sin repetir.
bool validateRow(const SudokuGrid& grid, int row);

// Comprueba que una columna contenga exactamente los números del 1 al 9 sin repetir.
bool validateColumn(const SudokuGrid& grid, int column);

// Comprueba que un subcuadro 3x3 contenga números válidos y no repetidos.
bool validateSubgrid(const SudokuGrid& grid, int startRow, int startColumn);

// Ejecuta secuencialmente las validaciones de filas, columnas y subcuadros.
bool validateSudoku(const SudokuGrid& grid);

// Configura el compilador para disponer de las definiciones necesarias.
#endif
