// Configura el compilador para disponer de las definiciones necesarias.
#ifndef THREAD_VALIDATOR_H
// Configura el compilador para disponer de las definiciones necesarias.
#define THREAD_VALIDATOR_H

// Configura el compilador para disponer de las definiciones necesarias.
#include "sudoku.h"


// Distribuye las 27 comprobaciones del Sudoku entre varios hilos y reúne sus resultados.
bool validateSudokuConcurrent(const SudokuGrid& grid, int threadCount);

// Configura el compilador para disponer de las definiciones necesarias.
#endif
