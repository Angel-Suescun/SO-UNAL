#include "validator.h"

#include <array>

bool validateRow(const SudokuGrid& grid, int row) {
    
    
    std::array<bool, 10> seen = {};

    for (int column = 0; column < GRID_SIZE; column++) {
        
        int value = grid[row][column];
        
        if (value < 1 || value > 9 || seen[value]) {
            
            return false;
        
        }
        
        seen[value] = true;
    }

    return true;

}

bool validateColumn(const SudokuGrid& grid, int column) {
    

    std::array<bool, 10> seen = {};

    for (int row = 0; row < GRID_SIZE; row++) {
        
        int value = grid[row][column];
        
        if (value < 1 || value > 9 || seen[value]) {
            
            return false;
        
        }
        
        seen[value] = true;
    }

    return true;

}


bool validateSubgrid(const SudokuGrid& grid, int startRow, int startColumn) {
    
    std::array<bool, 10> seen = {};

    for (int row = startRow; row < startRow + 3; row++) {
        
        for (int column = startColumn; column < startColumn + 3; column++) {
            
            int value = grid[row][column];
            
            if (value < 1 || value > 9 || seen[value]) {
                
                return false;
            
            }
            
            seen[value] = true;
        }
    }

    return true;

}

bool validateSudoku(const SudokuGrid& grid) {
    
    bool valid = true;

    for (int row = 0; row < GRID_SIZE; row++) {
        
        valid &= validateRow(grid, row);
    }

    for (int column = 0; column < GRID_SIZE; column++) {
        
        valid &= validateColumn(grid, column);
    }

    for (int startRow = 0; startRow < GRID_SIZE; startRow += 3) {
        
        for (int startColumn = 0; startColumn < GRID_SIZE; startColumn += 3) {
            
            valid &= validateSubgrid(grid, startRow, startColumn);
        
        }
    }

    return valid;
}