# ForkJoinSorting

Proyecto en Java que compara dos algoritmos de ordenamiento paralelo usando
`ForkJoinPool`: **QuickSort** y **MergeSort**.

## Objetivo

El proyecto mide el tiempo que tardan ambos algoritmos al ordenar arreglos de
diferentes tamaños y con una cantidad configurable de hilos.

Los arreglos se generan con una semilla fija, por lo que las pruebas son
reproducibles. Después de cada ordenamiento se verifica que el arreglo haya
quedado correctamente ordenado.

## Estructura

```text
ForkJoinSorting/
├── src/
│   ├── ArrayGenerator.java  # Generación y verificación de arreglos
│   ├── Benchmark.java       # Comparación y medición de algoritmos
│   ├── MergeSort.java       # MergeSort paralelo
│   └── QuickSort.java       # QuickSort paralelo
├── scripts/
│   └── plot_results.py      # Generación de gráfica desde el CSV
└── results/
    ├── benchmark.csv        # Resultados de las mediciones
    └── benchmark.png        # Gráfica generada
```

## Funcionamiento

### QuickSort

1. Selecciona un pivote usando la mediana del primer, elemento central y último
   elemento del segmento.
2. Reorganiza el segmento colocando los elementos menores a la izquierda y
   los mayores a la derecha.
3. Crea dos tareas `ForkJoin`: una para cada mitad.
4. Ordena secuencialmente los segmentos de hasta 100 elementos mediante
   `Insertion Sort`, evitando crear tareas pequeñas innecesarias.

### MergeSort

1. Divide el arreglo en dos mitades.
2. Crea una tarea `ForkJoin` para cada mitad.
3. Ordena recursivamente ambas partes.
4. Fusiona las dos partes ordenadas usando un arreglo auxiliar.
5. Para segmentos de hasta 100 elementos utiliza `Insertion Sort`.

El método `invokeAll` permite ejecutar las dos tareas hijas y esperar a que
terminen antes de continuar con la siguiente etapa.

## Requisitos

- Java JDK 8 o superior.
- Python 3 si se desea generar la gráfica.
- `matplotlib` para el script de visualización.

## Compilación

Desde esta carpeta:

```bash
mkdir -p out
javac -d out src/*.java
```

## Ejecución individual

### QuickSort

```bash
java -cp out QuickSort [tamaño] [hilos]
```

Ejemplo:

```bash
java -cp out QuickSort 100000 4
```

### MergeSort

```bash
java -cp out MergeSort [tamaño] [hilos]
```

Ejemplo:

```bash
java -cp out MergeSort 100000 4
```

Si no se indican argumentos, cada programa utiliza un arreglo de `100000`
elementos y tantos hilos como procesadores disponibles.

## Benchmark

El benchmark prueba los tamaños `1`, `10`, `100`, `1000`, `10000`, `100000` y
`1000000`, ejecutando cinco repeticiones por defecto.

```bash
java -cp out Benchmark [repeticiones] [hilos]
```

Ejemplo:

```bash
java -cp out Benchmark 5 4
```

Los resultados se guardan en:

```text
results/benchmark.csv
```

El archivo contiene el algoritmo, tamaño del arreglo, número de hilos,
repetición y tiempo en milisegundos.

## Generar la gráfica

Instalar la dependencia si es necesario:

```bash
python3 -m pip install matplotlib
```

Después ejecutar:

```bash
python3 scripts/plot_results.py
```

La gráfica se guarda en `results/benchmark.png`.

## Verificación

Cada ejecución individual muestra si el arreglo quedó ordenado correctamente:

```text
Correctamente ordenado: true
```

o:

```text
Arreglo ordenado: true
```

