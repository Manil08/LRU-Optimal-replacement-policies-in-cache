# Project README

## Overview
This project demonstrates the implementation of LRU (Least Recently Used) and Optimal cache replacement policies using Java. It provides a web interface using Flask for interactive simulations and comparisons of cache performance metrics. The application analyzes cache performance by measuring hits and misses on various memory access patterns.

## Key Features
- **LRU Cache Replacement Policy**: Implements the LRU policy to manage cache efficiently based on recent usage.
- **Optimal Cache Replacement Policy**: Implements the Optimal policy, which replaces the cache entry that will not be used for the longest period in the future.
- **Web Interface**: A Flask web application that allows users to input cache size and address sequences for simulation.

## Files
The project comprises the following main files:

- **21116058_CacheEmulation.java**: Contains the implementations of both LRU and Optimal cache replacement policies.
- **server.py**: The Flask application that handles HTTP requests, executes the Java program, and displays the simulation results.
- **index.html**: The frontend interface for inputting cache size and memory access sequences.
- **script.js**: The JavaScript file that manages frontend interactions and sends requests to the Flask backend.
- **results.html**: Displays the simulation results, comparing the performance of LRU and Optimal policies.

## Detailed Description

### 1. 21116058_CacheEmulation.java
This Java file includes the implementation of the LRU and Optimal cache replacement policies, along with logic to read memory access sequences from an input file and output results to a specified file.

**Key Components**
- **LRU Class**: Implements the LRU replacement policy and calculates cache hits, misses, compulsory misses, and capacity misses.
- **OPTIMAL Class**: Implements the Optimal replacement policy using a future knowledge approach to minimize misses.
- **File Handling**: Reads input data from a text file and writes results to an output file.

### 2. server.py
This Python file sets up a Flask web server to handle HTTP requests for cache simulation and visualization.

**Key Components**
- **Flask Routes**: Defines routes for serving the frontend and handling simulation requests.
  - `/`: Serves the index.html file.
  - `/simulate`: Handles simulation requests by executing the Java program and returning results.

### 3. index.html
This HTML file provides the frontend interface for users to input cache size and memory access sequences.

**Key Components**
- **Input Form**: Allows users to enter cache size and memory access sequences.
- **Submission Handling**: Sends user input to the backend for processing.

### 4. script.js
This JavaScript file manages user interactions on the frontend and communicates with the Flask backend.

**Key Components**
- **Form Submission**: Collects user data and sends it to the backend for processing.
- **Result Handling**: Displays the simulation results on a new page.

### 5. results.html
This HTML file displays the results of the cache simulation, providing a side-by-side comparison of the LRU and Optimal policies.

## Setup and Execution

### Prerequisites
- Python setup in your code editor (e.g., VS Code)
- Flask should be installed
- Java JDK (to compile and run the Java program)
- Maven (optional, if using for Java dependency management)

### Steps to Run the Project

1. **Compile the Java Program**:

   ```bash
   javac 21116058_CacheEmulation.java
   ```

2. **Setup Python Development Environment**:

  ```bash
  pip install flask
  ```

3. **Run the Flask Application**:

  ```bash
  python server.py
  ```

4. **Access the Frontend Interface**: Open a web browser and navigate to `http://127.0.0.1:5000/`.

5. **Input Cache Size and Address Sequence**:
   - Enter the cache size in the input field.
   - Provide the address access sequence in the textarea, separated by commas.

6. **Simulate the Cache Replacement Policies**:
   - Click the "Simulate" button to run the simulation.
   - The results will be displayed on a new page comparing LRU and Optimal policies.

### Results Display
The application displays the following metrics for both the LRU and Optimal policies:
- **Total Accesses**
- **Total Misses**
- **Compulsory Misses**
- **Capacity Misses**
- **Total Hits**

Each metric is color-coded for easy comparison, where:
- Green indicates better performance.
- Red indicates poorer performance.
- Yellow indicates equality.

## Summary of Cache Replacement Policies

### LRU (Least Recently Used):
- **Theory**: 
  - Evicts the least recently used items first.
  - Assumes that data not accessed for a while is less likely to be needed soon.
- **Code**: 
  - Maintains a list or queue to track cache entries' usage.
  - Replaces the item that hasn’t been accessed for the longest time during a cache miss.

### Optimal:
- **Theory**: 
  - Replaces the item that will not be used for the longest period in the future.
  - Serves as a theoretical benchmark for evaluating other cache replacement policies.
- **Code**: 
  - Scans future access patterns to identify which cached item will not be needed longest.
  - Replaces that item during a cache miss.


Each cache replacement policy has its advantages and disadvantages, depending on workload characteristics (e.g., temporal locality, spatial locality). LRU is effective in scenarios where recent usage patterns tend to repeat, while the Optimal policy provides the best theoretical performance, serving as a benchmark for evaluating practical implementations like LRU. Understanding the differences between these policies helps in choosing the right approach for specific applications and workloads.