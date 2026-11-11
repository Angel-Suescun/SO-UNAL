#ifndef SUDOKU_H
# define SUDOKU_H

#include <array>
#include <string>

constexpr int GRID_SIZE = 9;

using SudokuGrid = std::array<std::array<int, GRID_SIZE>, GRID_SIZE>;

bool loadSudokuFromFile(const std::string& filepath, SudokuGrid& grid);

#endif 