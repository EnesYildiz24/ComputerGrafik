import matplotlib.pyplot as plt

#author chatgpt 
# Wir kannten nur Python zur erstellung von Diagrammen.
# mit java haben wir es versucht aber ohne erfolg

# Datei mit den Benchmark-Ergebnissen
file_path = 'benchmark_results.txt'

# Daten einlesen
threads = []
times = []

with open(file_path, 'r') as file:
    lines = file.readlines()
    for line in lines:
        parts = line.strip().split(',')
        threads.append(int(parts[0].split(':')[1]))
        times.append(int(parts[1].split(':')[1]))

# Diagramm erstellen
plt.figure(figsize=(10, 6))
plt.bar(threads, times, color='skyblue')
plt.title('Benchmark Results')
plt.xlabel('Number of Threads')
plt.ylabel('Time (s)')
plt.grid(axis='y')

# Diagramm speichern
output_path = 'doc/a08-results.png'
plt.savefig(output_path)
plt.show()

print(f'Diagram saved to {output_path}')
