// Configura el compilador para disponer de las definiciones necesarias.
#include "sudoku.h"
// Configura el compilador para disponer de las definiciones necesarias.
#include "thread_validator.h"


// Configura el compilador para disponer de las definiciones necesarias.
#include <chrono>
// Configura el compilador para disponer de las definiciones necesarias.
#include <fstream>
// Configura el compilador para disponer de las definiciones necesarias.
#include <iostream>
// Configura el compilador para disponer de las definiciones necesarias.
#include <string>
// Configura el compilador para disponer de las definiciones necesarias.
#include <vector>


// Ejecuta el paso correspondiente del proceso de validación.
using namespace std;

// Ejecuta el paso correspondiente del algoritmo.
bool runBenchmark(const SudokuGrid& grid, int repetitions) {

    // Ejecuta el paso correspondiente del proceso de validación.
    const vector<int> threadCounts = {1, 2, 4, 8, 16, 27};

    // Ejecuta el paso correspondiente del proceso de validación.
    ofstream outputFile("results/benchmark_results.csv");

    // Ejecuta el paso correspondiente del algoritmo.
    if (!outputFile.is_open()) {
        // Ejecuta el paso correspondiente del proceso de validación.
        cerr << "Error: No se pudo abrir el archivo de salida para escribir los resultados del benchmark.\n";
        // Informa al llamador que la operación o validación falló.
        return false;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del proceso de validación.
    outputFile << "Threads,repetitions,total_time_ms,average_time_us\n";

    // Ejecuta el paso correspondiente del proceso de validación.
    cout << "\nBenchmark results:\n";

    // Ejecuta el paso correspondiente del algoritmo.
    for (int threadCount : threadCounts) {

        // Ejecuta el paso correspondiente del proceso de validación.
        auto start = chrono::steady_clock::now();

        // Ejecuta el paso correspondiente del algoritmo.
        for (int repetition = 0; repetition < repetitions;  repetition++) {

            // Ejecuta el paso correspondiente del algoritmo.
            if (!validateSudokuConcurrent(grid, threadCount)) {
                // Ejecuta el paso correspondiente del proceso de validación.
                cerr << "Error: Sudoku inválido durante el benchmark con " << threadCount << " hilos.\n";
                // Informa al llamador que la operación o validación falló.
                return false;
            // Cierra el bloque de ejecución de condición actual.
            }
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del proceso de validación.
        auto end = chrono::steady_clock::now();

        // Ejecuta el paso correspondiente del proceso de validación.
        double totalMs = chrono::duration<double, milli>(end - start).count();

        // Ejecuta el paso correspondiente del proceso de validación.
        int miliToMicro = 1000;
        // Ejecuta el paso correspondiente del proceso de validación.
        double averageUs = (totalMs * miliToMicro ) / repetitions;

        // Ejecuta el paso correspondiente del algoritmo.
        outputFile << threadCount << "," << repetitions << ","
                   // Ejecuta el paso correspondiente del algoritmo.
                   << totalMs << ","
                   // Ejecuta el paso correspondiente del proceso de validación.
                   << averageUs << "\n";

        // Ejecuta el paso correspondiente del proceso de validación.
        cout << threadCount << " threads: " << averageUs << " us per validation\n";

    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del proceso de validación.
    cout << "Results saved in results/benchmark_results.csv\n";


    // Informa al llamador que la operación o validación fue exitosa.
    return true;

// Cierra el bloque de ejecución de condición actual.
}


// Ejecuta el paso correspondiente del algoritmo.
int main(int argc, char *argv[]) {

    // Ejecuta el paso correspondiente del proceso de validación.
    bool benchmarkMode = (argc >= 2 && string(argv[1]) == "--benchmark");

    // Ejecuta el paso correspondiente del proceso de validación.
    string filePath;
    // Ejecuta el paso correspondiente del proceso de validación.
    int threadCount = 11;
    // Ejecuta el paso correspondiente del proceso de validación.
    SudokuGrid grid;

    // Ejecuta el paso correspondiente del algoritmo.
    if(benchmarkMode) {

        // Ejecuta el paso correspondiente del algoritmo.
        if (argc < 3 || argc > 4) {
            // Ejecuta el paso correspondiente del proceso de validación.
            cerr << "Uso: ./sudoku_validator --benchmark <ruta_del_archivo_sudoku> [repeticiones]\n";
            // Devuelve el resultado de la comprobación al llamador.
            return 1;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del proceso de validación.
        filePath = argv[2];

        // Ejecuta el paso correspondiente del proceso de validación.
        int repetitions = 1000;

        // Ejecuta el paso correspondiente del algoritmo.
        if (argc == 4) {
            // Ejecuta el paso correspondiente del algoritmo.
            try {
                // Ejecuta el paso correspondiente del proceso de validación.
                repetitions = stoi(argv[3]);
            // Ejecuta el paso correspondiente del algoritmo.
            } catch (...) {
                // Ejecuta el paso correspondiente del proceso de validación.
                cerr << "Error: El número de repeticiones debe ser un entero válido.\n";
                // Devuelve el resultado de la comprobación al llamador.
                return 1;
            // Cierra el bloque de ejecución de condición actual.
            }
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del algoritmo.
        if (repetitions < 1) {
            // Ejecuta el paso correspondiente del proceso de validación.
            cerr << "Error: El número de repeticiones debe ser mayor que 0.\n";
            // Devuelve el resultado de la comprobación al llamador.
            return 1;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Ejecuta el paso correspondiente del algoritmo.
        if (!loadSudokuFromFile(filePath, grid)) {
            // Ejecuta el paso correspondiente del proceso de validación.
            cerr << "Error al cargar el archivo del sudoku\n";
            // Devuelve el resultado de la comprobación al llamador.
            return 1;
        // Cierra el bloque de ejecución de condición actual.
        }

        // Devuelve el resultado de la comprobación al llamador.
        return runBenchmark(grid, repetitions) ? 0 : 1;
    // Cierra el bloque de ejecución de condición actual.
    }



    // Ejecuta el paso correspondiente del algoritmo.
    if (argc < 2 || argc > 3) {
        // Ejecuta el paso correspondiente del proceso de validación.
        std::cerr << "Uso: ./sudoku_validator <ruta_del_archivo_sudoku> [número_de_hilos]\n";
        // Devuelve el resultado de la comprobación al llamador.
        return 1;
    // Cierra el bloque de ejecución de condición actual.
    }



    // Ejecuta el paso correspondiente del algoritmo.
    if (argc == 3) {
        // Ejecuta el paso correspondiente del algoritmo.
        try {

            // Ejecuta el paso correspondiente del proceso de validación.
            threadCount = std::stoi(argv[2]);
        // Ejecuta el paso correspondiente del algoritmo.
        } catch(...){
            // Ejecuta el paso correspondiente del proceso de validación.
            std::cerr << "Error: Número de hilos invalidos.\n";
            // Devuelve el resultado de la comprobación al llamador.
            return 1;
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    if (threadCount < 1 || threadCount > 27) {
        // Ejecuta el paso correspondiente del proceso de validación.
        std::cerr << "Error: El número de hilos debe estar entre 1 y 27.\n";
        // Devuelve el resultado de la comprobación al llamador.
        return 1;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    if (!loadSudokuFromFile(argv[1], grid)) {

        // Ejecuta el paso correspondiente del proceso de validación.
        std::cerr << "Error al cargar el archivo del sudoku\n";

        // Devuelve el resultado de la comprobación al llamador.
        return 1;

    // Cierra el bloque de ejecución de condición actual.
    }

    // Ejecuta el paso correspondiente del proceso de validación.
    std::cout << "Sudoku cargado correctamente \n";
    // Ejecuta el paso correspondiente del proceso de validación.
    std::cout << "Hilos utilizados: " << threadCount << "\n";

    // Ejecuta el paso correspondiente del algoritmo.
    if (validateSudokuConcurrent(grid, threadCount)) {
        // Ejecuta el paso correspondiente del proceso de validación.
        std::cout << "El sudoku es válido\n";
    // Ejecuta el paso correspondiente del algoritmo.
    } else {
        // Ejecuta el paso correspondiente del proceso de validación.
        std::cout << "El sudoku no es válido\n";
    // Cierra el bloque de ejecución de condición actual.
    }

    // Devuelve el resultado de la comprobación al llamador.
    return 0;
// Cierra el bloque de ejecución de condición actual.
}
