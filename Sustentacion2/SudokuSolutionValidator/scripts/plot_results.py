import csv
import os
import matplotlib.pyplot as plt

os.makedirs("results", exist_ok=True)

input_file = 'results/benchmark_results.csv'
output_file = 'results/benchmark_plot.png'

threads = []
average_times = []

with open(input_file, 'r') as csvfile:

    reader = csv.DictReader(csvfile)

    for row in reader:
        threads.append(int(row['Threads']))
        average_times.append(float(row['average_time_us']))

plt.figure(figsize=(9, 5))

plt.plot(threads, average_times, marker='o')

plt.title('Sudoku Validation Benchmark')
plt.xlabel('Number of Threads')
plt.ylabel('Average Time (microseconds)')

plt.xticks(threads)
plt.grid(True)

os.makedirs("results", exist_ok=True)

plt.savefig(output_file, dpi=300, bbox_inches='tight')

print(f'Benchmark plot saved as {output_file}')

plt.show()