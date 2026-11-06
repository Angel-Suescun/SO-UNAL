#include "sudoku.h"

#include <iostream>

int main(int argc, char const *argv[])
{
    
    if (argc != 2) {
        std::cerr << "Uso: ./sudoku_validator <ruta_del_archivo_sudoku>\n";
        return 1; 
    }

    SudokuGrid grid;

    if (!loadSudokuFromFile(argv[1], grid)) {
        return 1; 
    }

    std::cout << "Sudoku cargado correctamente \n";

    for (const auto& row : grid) {
        for (int value : row) {
            std::cout << value << ' ';
        }
        std::cout << '\n';
    }

    return 0;
}
