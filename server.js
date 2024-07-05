const express = require('express');
const fs = require('fs');
const path = require('path');
const bodyParser = require('body-parser');
const cors = require('cors'); // Import the cors package

const app = express();
const PORT = 3000;

// Body parser middleware
app.use(bodyParser.json());

// Use CORS middleware
app.use(cors());

// Serve static files
app.use(express.static(path.join(__dirname, 'src/main/java/resources/map/html')));

// Endpoint to save a place
app.post('/save-place', (req, res) => {
    const placeData = req.body;
    const filePath = path.join(__dirname, 'src/main/java/resources/map/js/saved_places.txt');

    fs.appendFile(filePath, JSON.stringify(placeData) + '\n', (err) => {
        if (err) {
            console.error('Error saving place:', err);
            res.status(500).send('Internal Server Error');
        } else {
            res.status(200).send('Place saved successfully');
        }
    });
});

// Endpoint to get saved places
app.get('/saved-places', (req, res) => {
    const filePath = path.join(__dirname, 'src/main/java/resources/map/js/saved_places.txt');
    fs.readFile(filePath, 'utf8', (err, data) => {
        if (err) {
            console.error('Error reading saved places:', err);
            res.status(500).send('Internal Server Error');
        } else {
            const places = data.trim().split('\n').map(line => JSON.parse(line));
            res.status(200).json(places);
        }
    });
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
