#ifndef THREAD_VALIDATOR_H
#define THREAD_VALIDATOR_H

#include "sudoku.h"


bool validateSudokuConcurrent(const SudokuGrid& grid, int threadCount);

#endif