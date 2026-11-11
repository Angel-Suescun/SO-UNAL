# Configura una variable usada durante la compilación.
CXX = g++

# Configura una variable usada durante la compilación.
CXXFLAGS = -std=c++17 -Wall -Wextra -pedantic -O2 -pthread

# Configura una variable usada durante la compilación.
SOURCES = src/main.cpp src/sudoku.cpp src/thread_validator.cpp src/validator.cpp
# Configura una variable usada durante la compilación.
TARGET = sudoku_validator


# Define una tarea del proyecto y sus dependencias.
all: $(TARGET)

# Define una tarea del proyecto y sus dependencias.
$(TARGET): $(SOURCES)
	# Declara una configuración del proceso de construcción.
	$(CXX) $(CXXFLAGS) $(SOURCES) -o $(TARGET)

# Define una tarea del proyecto y sus dependencias.
clean:
	# Declara una configuración del proceso de construcción.
	rm -f $(TARGET)

# Define una tarea del proyecto y sus dependencias.
benchmark: $(TARGET)
	# Declara una configuración del proceso de construcción.
	./$(TARGET) --benchmark tests/valid/sudoku_01.txt 1000

# Define una tarea del proyecto y sus dependencias.
.PHONY: all clean benchmark


