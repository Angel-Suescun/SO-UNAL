# SudokuSolutionValidator

Proyecto en C++17 que valida soluciones de Sudoku de 9x9 y permite comparar el
rendimiento de la validación secuencial y concurrente usando hilos.

## Objetivo

El programa comprueba que un Sudoku cumpla simultáneamente estas condiciones:

- Cada fila contiene los números del 1 al 9 sin repetir.
- Cada columna contiene los números del 1 al 9 sin repetir.
- Cada subcuadro de 3x3 contiene los números del 1 al 9 sin repetir.

La validación concurrente divide el trabajo en 27 tareas independientes:
9 filas, 9 columnas y 9 subcuadros. Estas tareas se distribuyen entre la
cantidad de hilos indicada por el usuario.

## Estructura

```text
SudokuSolutionValidator/
├── src/
│   ├── main.cpp             # Programa principal y modo benchmark
│   ├── sudoku.cpp           # Lectura del Sudoku desde un archivo
│   ├── sudoku.h             # Tipo y constantes de la cuadrícula
│   ├── validator.cpp        # Validación de filas, columnas y subcuadros
│   ├── validator.h          # Declaraciones del validador
│   ├── thread_validator.cpp # Distribución del trabajo entre hilos
│   └── thread_validator.h   # Declaración del validador concurrente
├── tests/
│   ├── valid/               # Sudokus válidos
│   └── invalid/             # Sudokus con errores
├── scripts/
│   └── plot_results.py      # Generación de gráfica del benchmark
├── results/
│   ├── benchmark_results.csv
│   └── benchmark_plot.png
├── Makefile
└── sudoku_validator         # Ejecutable generado
```

## Formato de entrada

El archivo debe contener exactamente 81 números enteros, organizados como
nueve filas de nueve valores. Cada valor debe estar entre 1 y 9.

Ejemplo:

```text
5 3 4 6 7 8 9 1 2
6 7 2 1 9 5 3 4 8
1 9 8 3 4 2 5 6 7
8 5 9 7 6 1 4 2 3
4 2 6 8 5 3 7 9 1
7 1 3 9 2 4 8 5 6
9 6 1 5 3 7 2 8 4
2 8 7 4 1 9 6 3 5
3 4 5 2 8 6 1 7 9
```

## Requisitos

- Compilador compatible con C++17.
- `g++`.
- `make`.
- `pthread`, incluido normalmente en sistemas Linux.
- Python 3 y `matplotlib` para generar la gráfica.

## Compilación

Desde esta carpeta:

```bash
make
```

También se puede limpiar el ejecutable generado:

```bash
make clean
```

## Validar un Sudoku

```bash
./sudoku_validator <archivo_sudoku> [número_de_hilos]
```

Ejemplo:

```bash
./sudoku_validator tests/valid/sudoku_01.txt 4
```

El número de hilos debe estar entre 1 y 27. Si no se indica, se utilizan
11 hilos.

Una solución válida produce:

```text
El sudoku es válido
```

Una solución incorrecta produce:

```text
El sudoku no es válido
```

## Benchmark

El modo benchmark prueba automáticamente estas cantidades de hilos:

```text
1, 2, 4, 8, 16 y 27
```

Uso:

```bash
./sudoku_validator --benchmark <archivo_sudoku> [repeticiones]
```

Ejemplo:

```bash
./sudoku_validator --benchmark tests/valid/sudoku_01.txt 1000
```

También existe el objetivo equivalente:

```bash
make benchmark
```

Los resultados se guardan en:

```text
results/benchmark_results.csv
```

El CSV contiene el número de hilos, las repeticiones, el tiempo total en
milisegundos y el tiempo promedio en microsegundos.

## Generar la gráfica

Instalar `matplotlib` si es necesario:

```bash
python3 -m pip install matplotlib
```

Luego ejecutar:

```bash
python3 scripts/plot_results.py
```

La imagen se genera en:

```text
results/benchmark_plot.png
```

## Pruebas incluidas

Sudokus válidos:

```bash
tests/valid/sudoku_01.txt
tests/valid/sudoku_02.txt
```

Sudokus inválidos:

```bash
tests/invalid/invalid_row.txt
tests/invalid/invalid_column.txt
tests/invalid/invalid_subgrid.txt
tests/invalid/invalid_multiple_errors.txt
```

Ejemplo de prueba:

```bash
./sudoku_validator tests/invalid/invalid_row.txt 4
```

