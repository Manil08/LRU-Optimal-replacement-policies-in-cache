from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/')
def index():
    return app.send_static_file('index.html')

@app.route('/simulate', methods=['POST'])
def simulate():
    data = request.json
    cache_size = data['cacheSize']
    address_sequence = data['addressSequence']
    
    # Convert the address sequence into a string format acceptable by the Java program
    sequence_str = ' '.join(map(str, address_sequence))
    
    # Call the Java program using subprocess
    try:
        result = subprocess.run(
            ['java', '21116058_CacheEmulation', str(cache_size), sequence_str], 
            stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True
        )
        if result.returncode != 0:
            raise Exception(result.stderr)

        # Output the results from the Java program
        return jsonify({'result': result.stdout})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
