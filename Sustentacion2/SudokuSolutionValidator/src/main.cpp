#include "sudoku.h"
#include "thread_validator.h"


#include <chrono>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>


using namespace std;

bool runBenchmark(const SudokuGrid& grid, int repetitions) {

    const vector<int> threadCounts = {1, 2, 4, 8, 16, 27};

    ofstream outputFile("results/benchmark_results.csv");

    if (!outputFile.is_open()) {
        cerr << "Error: No se pudo abrir el archivo de salida para escribir los resultados del benchmark.\n";
        return false;
    }

    outputFile << "Threads,repetitions,total_time_ms,average_time_us\n";

    cout << "\nBenchmark results:\n";

    for (int threadCount : threadCounts) {
        
        auto start = chrono::steady_clock::now();

        for (int repetition = 0; repetition < repetitions;  repetition++) {
            
            if (!validateSudokuConcurrent(grid, threadCount)) {
                cerr << "Error: Sudoku inválido durante el benchmark con " << threadCount << " hilos.\n";
                return false;
            }
        }

        auto end = chrono::steady_clock::now();

        double totalMs = chrono::duration<double, milli>(end - start).count();

        int miliToMicro = 1000;
        double averageUs = (totalMs * miliToMicro ) / repetitions;

        outputFile << threadCount << "," << repetitions << ","
                   << totalMs << ","
                   << averageUs << "\n";
    
        cout << threadCount << " threads: " << averageUs << " us per validation\n"; 
    
    }                   
        
    cout << "Results saved in results/benchmark_results.csv\n";
        
        
    return true;
    
}


int main(int argc, char *argv[]) {
    
    bool benchmarkMode = (argc >= 2 && string(argv[1]) == "--benchmark");

    string filePath;
    int threadCount = 11; 
    SudokuGrid grid;
    
    if(benchmarkMode) {

        if (argc < 3 || argc > 4) {
            cerr << "Uso: ./sudoku_validator --benchmark <ruta_del_archivo_sudoku> [repeticiones]\n";
            return 1; 
        }

        filePath = argv[2];

        int repetitions = 1000;

        if (argc == 4) {
            try {
                repetitions = stoi(argv[3]);
            } catch (...) {
                cerr << "Error: El número de repeticiones debe ser un entero válido.\n";
                return 1;
            }
        }

        if (repetitions < 1) {
            cerr << "Error: El número de repeticiones debe ser mayor que 0.\n";
            return 1;
        }

        if (!loadSudokuFromFile(filePath, grid)) {
            cerr << "Error al cargar el archivo del sudoku\n";
            return 1;
        }

        return runBenchmark(grid, repetitions) ? 0 : 1;    
    }

    

    if (argc < 2 || argc > 3) {
        std::cerr << "Uso: ./sudoku_validator <ruta_del_archivo_sudoku> [número_de_hilos]\n";
        return 1; 
    }



    if (argc == 3) {
        try {
            
            threadCount = std::stoi(argv[2]);
        } catch(...){
            std::cerr << "Error: Número de hilos invalidos.\n";
            return 1;
        }
    }

    if (threadCount < 1 || threadCount > 27) {
        std::cerr << "Error: El número de hilos debe estar entre 1 y 27.\n";
        return 1;
    }

    if (!loadSudokuFromFile(argv[1], grid)) {

        std::cerr << "Error al cargar el archivo del sudoku\n";

        return 1;
         
    }

    std::cout << "Sudoku cargado correctamente \n";
    std::cout << "Hilos utilizados: " << threadCount << "\n";

    if (validateSudokuConcurrent(grid, threadCount)) {
        std::cout << "El sudoku es válido\n";
    } else {
        std::cout << "El sudoku no es válido\n";
    }

    return 0;
}
