document.getElementById('cacheForm').addEventListener('submit', function (e) {
    e.preventDefault();
    
    const cacheSize = document.getElementById('cacheSize').value;
    const addressSequence = document.getElementById('addressSequence').value.split(',').map(Number);
    const algorithm = document.getElementById('algorithm').value;  // Get selected algorithm

    fetch('/simulate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ cacheSize, addressSequence, algorithm })  // Include algorithm in the request
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('output').textContent = data.result || data.error;
    })
    .catch(error => {
        document.getElementById('output').textContent = 'Error: ' + error.message;
    });
});
