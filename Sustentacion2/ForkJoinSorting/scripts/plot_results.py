# Carga las herramientas usadas para leer resultados y generar la gráfica.
import csv
# Carga las herramientas usadas para leer resultados y generar la gráfica.
from collections import defaultdict
# Carga las herramientas usadas para leer resultados y generar la gráfica.
from pathlib import Path

# Carga las herramientas usadas para leer resultados y generar la gráfica.
import matplotlib.pyplot as plt

# Calcula la carpeta raíz del proyecto para usar rutas independientes del directorio actual.
project_dir = Path(__file__).resolve().parents[1]
# Calcula la carpeta raíz del proyecto para usar rutas independientes del directorio actual.
input_file = project_dir / "results" / "benchmark.csv"
# Calcula la carpeta raíz del proyecto para usar rutas independientes del directorio actual.
output_file = project_dir / "results" / "benchmark.png"

# Agrupa las mediciones para poder calcular promedios por algoritmo y tamaño.
measurements = defaultdict(list)

# Define la ubicación del CSV que contiene las mediciones del benchmark.
with input_file.open("r", newline="", encoding="utf-8") as file:
    # Prepara la lectura del CSV usando los nombres de sus columnas.
    reader = csv.DictReader(file)

    # Recorre los resultados para transformarlos en datos de la gráfica.
    for row in reader:
        # Ejecuta un paso del procesamiento de resultados.
        algorithm = row["algorithm"]
        # Ejecuta un paso del procesamiento de resultados.
        size = int(row["size"])
        # Ejecuta un paso del procesamiento de resultados.
        time_ms = float(row["time_ms"])

        # Agrupa las mediciones para poder calcular promedios por algoritmo y tamaño.
        measurements[algorithm, size].append(time_ms)

# Ejecuta un paso del procesamiento de resultados.
algorithms = {
    # Ejecuta un paso del procesamiento de resultados.
    "QuickSort": "QuickSort",
    # Ejecuta un paso del procesamiento de resultados.
    "MergeSort": "MergeSort",
# Ejecuta un paso del procesamiento de resultados.
}

# Configura o dibuja la visualización del rendimiento.
plt.figure(figsize=(10, 6))

# Recorre los resultados para transformarlos en datos de la gráfica.
for algorithm, label in algorithms.items():
    # Ejecuta un paso del procesamiento de resultados.
    sizes = sorted(
        # Agrupa las mediciones para poder calcular promedios por algoritmo y tamaño.
        size for name, size in measurements
        # Ejecuta un paso del procesamiento de resultados.
        if name == algorithm
    # Ejecuta un paso del procesamiento de resultados.
    )

    # Calcula el tiempo promedio de las repeticiones disponibles.
    averages = [
        # Agrupa las mediciones para poder calcular promedios por algoritmo y tamaño.
        sum(measurements[algorithm, size])
        # Agrupa las mediciones para poder calcular promedios por algoritmo y tamaño.
        / len(measurements[algorithm, size])
        # Recorre los resultados para transformarlos en datos de la gráfica.
        for size in sizes
    # Ejecuta un paso del procesamiento de resultados.
    ]

    # Inicia un bloque de procesamiento relacionado con el paso anterior.
    if sizes:
        # Calcula el tiempo promedio de las repeticiones disponibles.
        plt.plot(sizes, averages, marker="o", label=label)

# Configura o dibuja la visualización del rendimiento.
plt.xscale("log")
# Configura o dibuja la visualización del rendimiento.
plt.xlabel("Number of elements")
# Configura o dibuja la visualización del rendimiento.
plt.ylabel("Average execution time (ms)")
# Configura o dibuja la visualización del rendimiento.
plt.title("Fork/Join Sorting Algorithms Performance")
# Configura o dibuja la visualización del rendimiento.
plt.grid(True)
# Configura o dibuja la visualización del rendimiento.
plt.legend()

# Define dónde se guardará la imagen resultante.
plt.savefig(output_file, dpi=300, bbox_inches="tight")
# Configura o dibuja la visualización del rendimiento.
plt.show()
# Configura o dibuja la visualización del rendimiento.
plt.close()

# Define dónde se guardará la imagen resultante.
print(f"Graph saved in {output_file}")
