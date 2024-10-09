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
    .then(response => {
        if (!response.ok) {
            return response.json().then(text => { throw new Error(text.error) });
        }
        return response.text();  // Change to handle HTML response
    })
    .then(html => {
        // Open a new window and write the HTML response
        const newWindow = window.open();
        newWindow.document.write(html);
        newWindow.document.close();
    })
    .catch(error => {
        alert('Error: ' + error.message);
    });
});
