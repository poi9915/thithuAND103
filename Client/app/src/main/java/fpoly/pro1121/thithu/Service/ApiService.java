package fpoly.pro1121.thithu.Service;

import java.util.ArrayList;

import fpoly.pro1121.thithu.Model.Response;
import fpoly.pro1121.thithu.Model.XeMay;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    // Địa chỉ server , Sử dụng terminal hoặc cmd gõ ipconfig để lấy địa chỉ ipv4
    // VD : IPv4 Address. . . . . . . . . . . : 172.20.160.1 thì Base_Url = http://172.20.160.1: + PORT của server
    public static String BASE_URL = "http://172.31.80.1:5000/";

    // Get list tất cả XeMay;
    @GET("api/getAll")
    Call<Response<ArrayList<XeMay>>> getAllXeMay();

    // Get  XeMay theo id
    @GET("api/get/{id}")
    Call<Response<XeMay>> getXeMayById(@Path("id") String id);

    //Search XeMay theo tên
    @GET("api/search/{name}")
    Call<Response<ArrayList<XeMay>>> searchXeMay(@Path("name") String id);

    // POST XeMay
    @Multipart
    @POST("api/post")
    Call<Void> addXeMay(
            @Part("ten_xe_ph45090") RequestBody ten_xe_ph45090,
            @Part("mau_sac_ph45090") RequestBody mau_sac_ph45090,
            @Part("gia_ban_ph45090") RequestBody gia_ban_ph45090,
            @Part("mo_ta_ph45090") RequestBody mo_ta_ph45090,
            @Part MultipartBody.Part hinh_anh_ph45090
    );

    // Delete
    @DELETE("api/delete/{id}")
    Call<Void> deleteXeMay(@Path("id") String id);
}
