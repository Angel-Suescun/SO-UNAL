CXX = g++

CXXFLAGS = -std=c++17 -Wall -Wextra -pedantic -O2 -pthread

SOURCES = src/main.cpp src/sudoku.cpp src/thread_validator.cpp src/validator.cpp
TARGET = sudoku_validator


all: $(TARGET)

$(TARGET): $(SOURCES)
	$(CXX) $(CXXFLAGS) $(SOURCES) -o $(TARGET)

clean:
	rm -f $(TARGET)

benchmark: $(TARGET)
	./$(TARGET) --benchmark tests/valid/sudoku_01.txt 1000

.PHONY: all clean benchmark


