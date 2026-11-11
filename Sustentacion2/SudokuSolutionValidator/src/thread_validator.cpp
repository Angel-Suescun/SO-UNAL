#include "thread_validator.h"
#include "validator.h"

#include <thread>
#include <vector>

bool validateSudokuConcurrent(  const SudokuGrid& grid, int threadCount) {
    
    const int taskCount = 27;

    if (threadCount < 1 || threadCount > taskCount) {
     
        return false;
    
    }
    
    std::vector<int> results(taskCount, 0);
    std::vector<std::thread> workers;

    for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
        
        workers.emplace_back([&grid, &results, threadIndex, threadCount]() {

            for (int task = threadIndex; task < taskCount; task += threadCount) {
                
                if (task < 9) {
    
                    results[task] = validateRow(grid, task) ? 1 : 0;
    
                } else if (task < 18) {
    
                    int column = task - 9;
                    results[task] = validateColumn(grid, column) ? 1 : 0;
    
                } else {
    
                    int subgrid = task - 18;
                    
                    int startRow = (subgrid / 3) * 3;
                    int startCol = (subgrid % 3) * 3;
                    results[task] = validateSubgrid(grid, startRow, startCol) ? 1 : 0;
    
                }
    
            }
    
        });
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


