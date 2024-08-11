const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const XeMaySchema = new Schema({
    // Theo dê bài thuộc tính trong Db phải có mã sv phía sau vd : ten_xe => ten_xe_ph123
    ten_xe_ph45090: {type: String, required: true},
    mau_sac_ph45090: {type: String, required: true},
    gia_ban_ph45090: {type: Number, required: true},
    mo_ta_ph45090: {type: String, required: true},
    hinh_anh_ph45090: {type: String, required: true},
}, {
    timestamps: true
})

module.exports = mongoose.model('XeMay', XeMaySchema);