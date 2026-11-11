# Carga las herramientas usadas para leer resultados y generar la gráfica.
import csv
# Carga las herramientas usadas para leer resultados y generar la gráfica.
import os
# Carga las herramientas usadas para leer resultados y generar la gráfica.
import matplotlib.pyplot as plt

# Ejecuta un paso del procesamiento de resultados.
os.makedirs("results", exist_ok=True)

# Define la ubicación del CSV que contiene las mediciones del benchmark.
input_file = 'results/benchmark.csv'
# Define dónde se guardará la imagen resultante.
output_file = 'results/benchmark_plot.png'

# Ejecuta un paso del procesamiento de resultados.
threads = []
# Calcula el tiempo promedio de las repeticiones disponibles.
average_times = []

# Define la ubicación del CSV que contiene las mediciones del benchmark.
with open(input_file, 'r') as csvfile:

    # Prepara la lectura del CSV usando los nombres de sus columnas.
    reader = csv.DictReader(csvfile)

    # Recorre los resultados para transformarlos en datos de la gráfica.
    for row in reader:
        # Ejecuta un paso del procesamiento de resultados.
        threads.append(int(row['Threads']))
        # Calcula el tiempo promedio de las repeticiones disponibles.
        average_times.append(float(row['average_time_us']))

# Configura o dibuja la visualización del rendimiento.
plt.figure(figsize=(9, 5))

# Calcula el tiempo promedio de las repeticiones disponibles.
plt.plot(threads, average_times, marker='o')

# Configura o dibuja la visualización del rendimiento.
plt.title('Sudoku Validation Benchmark')
# Configura o dibuja la visualización del rendimiento.
plt.xlabel('Number of Threads')
# Configura o dibuja la visualización del rendimiento.
plt.ylabel('Average Time (microseconds)')

# Configura o dibuja la visualización del rendimiento.
plt.xticks(threads)
# Configura o dibuja la visualización del rendimiento.
plt.grid(True)

# Ejecuta un paso del procesamiento de resultados.
os.makedirs("results", exist_ok=True)

# Define dónde se guardará la imagen resultante.
plt.savefig(output_file, dpi=300, bbox_inches='tight')

# Define dónde se guardará la imagen resultante.
print(f'Benchmark plot saved as {output_file}')

# Configura o dibuja la visualización del rendimiento.
plt.show()
