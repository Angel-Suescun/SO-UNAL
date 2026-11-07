#include "sudoku.h"
#include "thread_validator.h"

#include <iostream>

int main(int argc, char const *argv[])
{
    
    if (argc != 2) {
        std::cerr << "Uso: ./sudoku_validator <ruta_del_archivo_sudoku>\n";
        return 1; 
    }

    SudokuGrid grid;

    if (!loadSudokuFromFile(argv[1], grid)) {

        std::cerr << "Error al cargar el archivo del sudoku\n";
        return 1;
         
    }

    std::cout << "Sudoku cargado correctamente \n";

    if (validateSudokuConcurrently(grid)) {
        std::cout << "El sudoku es válido\n";
    } else {
        std::cout << "El sudoku no es válido\n";
    }

    return 0;
}
