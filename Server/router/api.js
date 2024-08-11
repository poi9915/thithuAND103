const express = require('express');
const router = express.Router();
const upload = require('../config/common/upload')
const XeMay = require('../models/XeMay')
//Api cho chức năng hiển thị danh sách
router.get('/getAll', async (req, res) => {

    try {
        const data = await XeMay.find()
        res.json({
            status: 200,
            message: "List all XeMay",
            data: data
        })
    } catch (err) {
        console.log('GET all err : ' + err)
    }
})
// Api cho chức năng xem chi tiết
router.get('/get/:id', async (req, res) => {
    const {id} = req.params
    const data = await XeMay.findById(id)
    try {
        res.json({
            status: 200,
            message: "XeMay detail by id",
            data: data
        })
    } catch (err) {
        console.log("Get by Id err : " + err)
    }
})
// api cho chúc năng thêm
// trong upload.single('hinh_anh_ph45090') phần hinh_anh_ph45090 nên để giống với trong models và phần body
router.post('/post', upload.single('hinh_anh_ph45090'), async (req, res) => {
    try {
        const data = req.body;
        const {file} = req;
        const hinh_anh_ph45090 = `/uploads/${file.filename}`
        const Xemay = new XeMay({
            ten_xe_ph45090: data.ten_xe_ph45090,
            mau_sac_ph45090: data.mau_sac_ph45090,
            gia_ban_ph45090: data.gia_ban_ph45090,
            mo_ta_ph45090: data.mo_ta_ph45090,
            hinh_anh_ph45090: hinh_anh_ph45090
        })
        const result = await Xemay.save()

        res.json({
            status: 200,
            message: "Add successful",
            data: result
        })
    } catch (err) {
        console.log("Add xe err : " + err)
    }

})

router.get('/search/:name', async (req, res) => {
    try {
        const {name} = req.params;
        const data = await XeMay.find({
            ten_xe_ph45090: {$regex: name, $options: 'i'}
        })
        res.json({
            status: 200,
            message: "List xe ",
            data: data
        })
    } catch (err) {
        console.log("Search err : " + err)
    }
})
router.delete('/delete/:id', async (req, res) => {
    try {
        const {id} = req.params;
        const result = await XeMay.findOneAndDelete(id)
        if (result) {
            res.status(200)
        }
    } catch (err) {
        console.log("DELETE err : " + err)
    }
})
router.put('/update/:id', upload.single('hinh_anh_ph45090'), async (req, res) => {

    try {
        const id = req.params.id;
        const data = req.body;
        const {file} = req;
        const XeMayObj = await XeMay.findById(id)
        const hinh_anh_ph45090 = `/uploads/${file.filename}`
        if (XeMayObj) {
            XeMayObj.ten_xe_ph45090 = data.ten_xe_ph45090 ?? XeMayObj.ten_xe_ph45090
            XeMayObj.mau_sac_ph45090 = data.mau_sac_ph45090 ?? XeMayObj.mau_sac_ph45090
            XeMayObj.gia_ban_ph45090 = data.gia_ban_ph45090 ?? XeMayObj.gia_ban_ph45090
            XeMayObj.mo_ta_ph45090 = data.mo_ta_ph45090 ?? XeMayObj.mo_ta_ph45090
            XeMayObj.hinh_anh_ph45090 = hinh_anh_ph45090 ?? XeMayObj.hinh_anh_ph45090
        }
        const result = await Xemay.save()
        if (result) {
            res.json({
                status: 200,
                message: "Update successful",
                data: result
            })
        }

    } catch (err) {
        console.log("Add xe err : " + err)
    }
})
module.exports = router