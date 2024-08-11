const mongoose = require('mongoose');
require("dotenv").config();

const connect = async () => {
    try {
        await mongoose.connect(process.env.MONGODB_URL)
    } catch (err) {
        console.error("Connect to mongoDb err : " + err);
    }
}
module.exports = {connect}
