#include "thread_validator.h"
#include "validator.h"

#include <thread>
#include <vector>

bool validateSudokuConcurrently(const SudokuGrid& grid) {
    
    const int threadCount = 11;
    
    std::vector<int> results(threadCount, 0);
    std::vector<std::thread> workers;

    workers.emplace_back([&grid, &results]() {
        
        bool valid =  true;

        for (int row = 0; row < GRID_SIZE; row++) {
            
            valid &= validateRow(grid, row);
        
        }
        results[0] = valid ? 1 : 0;
    });

    workers.emplace_back([&grid, &results]() {
        
        bool valid =  true;

        for (int column = 0; column < GRID_SIZE; column++) {
            
            valid &= validateColumn(grid, column);
        
        }
        results[1] = valid ? 1 : 0;
    });

    for (int row = 0; row < GRID_SIZE; row += 3) {
        for (int column = 0; column < GRID_SIZE; column += 3) {
            
            int resultIndex = 2 + (row / 3) * 3 + (column / 3);

            workers.emplace_back([&grid, &results, row, column, resultIndex]() {
                
                results[resultIndex] = 
                    validateSubgrid(grid, row, column) ? 1 : 0;
            
                });
        }
    }

    for (auto& worker : workers) {

            worker.join();

        }

        for (int result : results) {

            if (result == 0) {

                return false;
            
            }
        
        }

        return true;

}


