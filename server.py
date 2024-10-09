from flask import Flask, request, render_template, send_from_directory
import subprocess

app = Flask(__name__, static_url_path='', static_folder='.')

@app.route('/')
def index():
    return send_from_directory('.', 'index.html')

@app.route('/simulate', methods=['POST'])
def simulate():
    data = request.json
    cache_size = data['cacheSize']
    address_sequence = data['addressSequence']
    
    # Convert the address sequence into a string format acceptable by the Java program
    sequence_str = ' '.join(map(str, address_sequence))
    
    results = {}

    try:
        # Run the LRU Java program
        subprocess.run(['java', 'LRU', str(cache_size)] + sequence_str.split(), check=True)
        # Run the OPTIMAL Java program
        subprocess.run(['java', 'OPTIMAL', str(cache_size)] + sequence_str.split(), check=True)

        # Function to parse output file
        def parse_output(file_path):
            metrics = {}
            with open(file_path, 'r') as file:
                for line in file:
                    if line.startswith("TOTAL_ACCESSES"):
                        metrics['total_accesses'] = int(line.split('=')[1].strip())
                    elif line.startswith("TOTAL_MISSES"):
                        metrics['total_misses'] = int(line.split('=')[1].strip())
                    elif line.startswith("COMPULSORY_MISSES"):
                        metrics['compulsory_misses'] = int(line.split('=')[1].strip())
                    elif line.startswith("CAPACITY_MISSES"):
                        metrics['capacity_misses'] = int(line.split('=')[1].strip())
            
            # Calculate total hits
            metrics['total_hits'] = metrics['total_accesses'] - metrics['total_misses']

            return metrics

        lru_metrics = parse_output("21116058_LRU_output.out")
        optimal_metrics = parse_output("21116058_OPTIMAL_output.out")

        results = {
            'LRU': lru_metrics,
            'OPTIMAL': optimal_metrics
        }

        return render_template('results.html', results=results)

    except Exception as e:
        return {'error': str(e)}, 500

if __name__ == '__main__':
    app.run(debug=True)
