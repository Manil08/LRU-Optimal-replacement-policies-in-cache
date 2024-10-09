document.getElementById('cacheForm').addEventListener('submit', function (e) {
    e.preventDefault();
    
    const cacheSize = document.getElementById('cacheSize').value;
    const addressSequence = document.getElementById('addressSequence').value.split(',').map(Number);
    
    fetch('/simulate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ cacheSize, addressSequence })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('output').textContent = data.result;
    })
    .catch(error => {
        document.getElementById('output').textContent = 'Error: ' + error.message;
    });
});
