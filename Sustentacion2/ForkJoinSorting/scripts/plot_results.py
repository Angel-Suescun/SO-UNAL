import csv
from collections import defaultdict
from pathlib import Path

import matplotlib.pyplot as plt

project_dir = Path(__file__).resolve().parents[1]
input_file = project_dir / "results" / "benchmark.csv"
output_file = project_dir / "results" / "benchmark.png"

measurements = defaultdict(list)

with input_file.open("r", newline="", encoding="utf-8") as file:
    
    reader = csv.DictReader(file)

    for row in reader:
       
        algorithm = row["algorithm"]
        size = int(row["size"])
        time_ms = float(row["time_ms"])

        measurements[algorithm, size].append(time_ms)

algorithms = {
    "QuickSort": "QuickSort",
    "MergeSort": "MergeSort",
}

plt.figure(figsize=(10, 6))

for algorithm, label in algorithms.items():
   
    sizes = sorted(
        size for name, size in measurements
        if name == algorithm
    )

    averages = [
        sum(measurements[algorithm, size])
        / len(measurements[algorithm, size])
        for size in sizes
    ]

    if sizes:
        plt.plot(sizes, averages, marker="o", label=label)

plt.xscale("log")
plt.xlabel("Number of elements")
plt.ylabel("Average execution time (ms)")
plt.title("Fork/Join Sorting Algorithms Performance")
plt.grid(True)
plt.legend()

plt.savefig(output_file, dpi=300, bbox_inches="tight")
plt.show()
plt.close()

print(f"Graph saved in {output_file}")
