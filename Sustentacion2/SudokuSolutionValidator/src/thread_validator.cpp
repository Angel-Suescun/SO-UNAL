// Configura el compilador para disponer de las definiciones necesarias.
#include "thread_validator.h"
// Configura el compilador para disponer de las definiciones necesarias.
#include "validator.h"

// Configura el compilador para disponer de las definiciones necesarias.
#include <thread>
// Configura el compilador para disponer de las definiciones necesarias.
#include <vector>

// Distribuye las 27 comprobaciones del Sudoku entre varios hilos y reúne sus resultados.
bool validateSudokuConcurrent(  const SudokuGrid& grid, int threadCount) {

    // Define las 27 comprobaciones independientes: 9 filas, 9 columnas y 9 subcuadros.
    const int taskCount = 27;

    // Ejecuta el paso correspondiente del algoritmo.
    if (threadCount < 1 || threadCount > taskCount) {
        // Informa al llamador que la operación o validación falló.
        return false;
    // Cierra el bloque de ejecución de condición actual.
    }

    // Reserva un resultado por comprobación y los inicializa como inválidos hasta que cada tarea termine.
    std::vector<int> results(taskCount, 0);
    // Prepara la colección donde se guardarán los hilos de validación.
    std::vector<std::thread> workers;

    // Crea tantos hilos como se solicitaron para repartir las tareas del Sudoku.
    for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {

        // Crea e inicia un hilo; cada hilo procesa las tareas que le corresponden mediante saltos de threadCount.
        workers.emplace_back([&grid, &results, threadIndex, threadCount]() {

            // Asigna a cada hilo tareas intercaladas, evitando que todos procesen la misma comprobación.
            for (int task = threadIndex; task < taskCount; task += threadCount) {

                // Identifica las primeras nueve tareas como validaciones de filas.
                if (task < 9) {
                    // Valida la fila asignada a esta tarea y guarda 1 si es correcta o 0 si contiene un error.
                    results[task] = validateRow(grid, task) ? 1 : 0;
                // Identifica las tareas 9 a 17 como validaciones de columnas.
                } else if (task < 18) {
                    // Ejecuta el paso correspondiente del proceso de validación.
                    int column = task - 9;
                    // Valida la columna asignada y registra el resultado de esa comprobación.
                    results[task] = validateColumn(grid, column) ? 1 : 0;
                // Ejecuta el paso correspondiente del algoritmo.
                } else {
                    // Ejecuta el paso correspondiente del proceso de validación.
                    int subgrid = task - 18;

                    // Convierte el índice del subcuadro en sus coordenadas iniciales dentro de la cuadrícula.
                    int startRow = (subgrid / 3) * 3;
                    // Convierte el índice del subcuadro en sus coordenadas iniciales dentro de la cuadrícula.
                    int startCol = (subgrid % 3) * 3;
                    // Valida el subcuadro 3x3 calculado y registra si cumple las reglas del Sudoku.
                    results[task] = validateSubgrid(grid, startRow, startCol) ? 1 : 0;
                // Cierra el bloque de ejecución de =.
                }
            // Cierra el bloque de ejecución de =.
            }
        // Ejecuta el paso correspondiente del proceso de validación.
        });
    // Cierra el bloque de ejecución de =.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    for (auto& worker : workers) {
        // Espera a que el hilo termine para garantizar que todas las validaciones finalizaron antes de revisar resultados.
        worker.join();
    // Cierra el bloque de ejecución de recorrido actual.
    }

    // Ejecuta el paso correspondiente del algoritmo.
    for (int result : results) {
        // Ejecuta el paso correspondiente del algoritmo.
        if (result == 0) {
            // Informa al llamador que la operación o validación falló.
            return false;
        // Cierra el bloque de ejecución de condición actual.
        }
    // Cierra el bloque de ejecución de condición actual.
    }

    // Informa al llamador que la operación o validación fue exitosa.
    return true;
// Cierra el bloque de ejecución de condición actual.
}


