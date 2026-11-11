#include "sudoku.h"

#include <fstream>
#include <iostream>
#include <string>

bool loadSudokuFromFile(const std::string& filepath, SudokuGrid& grid) {
    std::ifstream inputFile(filepath);

    if (!inputFile.is_open()) {
        
        std::cerr << "Error> No se pudo abrir el archivo: " << filepath << '\n';
        
        return false;

    }
    
    for (int row = 0; row < GRID_SIZE; row++) {
        
        for (int column = 0; column < GRID_SIZE; column++) {
            
            if (!(inputFile >> grid[row][column])) {
            
                std::cerr << "Error: Sudoku incompleto o inválido en el archivo: " << filepath << '\n';
            
                return false;
            
            }
        }
    }

    std::string extraValue;

    if (inputFile >> extraValue) {
        
        std::cerr << "Error: Sudoku tiene valores adicionales en el archivo: " << filepath << '\n';
        
        return false;
    }

    return true;
}