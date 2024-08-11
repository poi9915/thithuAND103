package fpoly.pro1121.thithu.Model;

public class XeMay {
    // Tạo các thuộc tính cho đối tượng XeMay giống như server (giống từng chữ , kí tự)
//    ten_xe_ph45090: {type: String, required: true},
//    mau_sac_ph45090: {type: String, required: true},
//    gia_ban_ph45090: {type: Number, required: true},
//    mo_ta_ph45090: {type: String, required: true},
//    hinh_anh_ph45090: {type: String, required: true},
    private String _id;
    private String ten_xe_ph45090;
    private String mau_sac_ph45090;
    private int gia_ban_ph45090;
    private String mo_ta_ph45090;
    private String hinh_anh_ph45090;

    public XeMay(String _id, String ten_xe_ph45090, String mau_sac_ph45090, int gia_ban_ph45090, String mo_ta_ph45090, String hinh_anh_ph45090) {
        this._id = _id;
        this.ten_xe_ph45090 = ten_xe_ph45090;
        this.mau_sac_ph45090 = mau_sac_ph45090;
        this.gia_ban_ph45090 = gia_ban_ph45090;
        this.mo_ta_ph45090 = mo_ta_ph45090;
        this.hinh_anh_ph45090 = hinh_anh_ph45090;
    }

    public XeMay() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen_xe_ph45090() {
        return ten_xe_ph45090;
    }

    public void setTen_xe_ph45090(String ten_xe_ph45090) {
        this.ten_xe_ph45090 = ten_xe_ph45090;
    }

    public String getMau_sac_ph45090() {
        return mau_sac_ph45090;
    }

    public void setMau_sac_ph45090(String mau_sac_ph45090) {
        this.mau_sac_ph45090 = mau_sac_ph45090;
    }

    public int getGia_ban_ph45090() {
        return gia_ban_ph45090;
    }

    public void setGia_ban_ph45090(int gia_ban_ph45090) {
        this.gia_ban_ph45090 = gia_ban_ph45090;
    }

    public String getMo_ta_ph45090() {
        return mo_ta_ph45090;
    }

    public void setMo_ta_ph45090(String mo_ta_ph45090) {
        this.mo_ta_ph45090 = mo_ta_ph45090;
    }

    public String getHinh_anh_ph45090() {
        return hinh_anh_ph45090;
    }

    public void setHinh_anh_ph45090(String hinh_anh_ph45090) {
        this.hinh_anh_ph45090 = hinh_anh_ph45090;
    }
}
