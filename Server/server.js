const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const Database = require('./config/db');
const router = require('./router/api');
app.use(bodyParser.json());
//express.static dùng để có thể load ảnh từ url
app.use(express.static('public/'));
PORT = process.env.PORT
// Listen Port dc set
app.listen(PORT, () => {
    console.log("Server listening on port " + PORT);
})

// Router
app.use('/api', router)

// Connect vào mongodb
Database.connect();
