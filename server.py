from flask import Flask, request, jsonify, send_from_directory
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
    
    # Select which algorithm to run, you can decide based on the request or add a field in the frontend
    algorithm = data.get('algorithm', 'LRU')  # Default to LRU if not specified
    
    try:
        result = subprocess.run(
            ['java', algorithm, str(cache_size)] + sequence_str.split(),
            stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True
        )
        if result.returncode != 0:
            raise Exception(result.stderr)

        return jsonify({'result': result.stdout.strip()})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
