#ifndef VALIDATOR_H
#define VALIDATOR_H

#include "sudoku.h"

bool validateRow(const SudokuGrid& grid, int row);

bool validateColumn(const SudokuGrid& grid, int column);

bool validateSubgrid(const SudokuGrid& grid, int startRow, int startColumn);

bool validateSudoku(const SudokuGrid& grid);

#endif