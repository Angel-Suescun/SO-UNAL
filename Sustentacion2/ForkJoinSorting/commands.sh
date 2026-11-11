mkdir -p out
javac -d out src/*.java

java -cp out QuickSort 1000000 8

java -cp out MergeSort 1000000 8

java -cp out Benchmark 5 8

python3 scripts/plot_results.py