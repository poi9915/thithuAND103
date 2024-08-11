package fpoly.pro1121.thithu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import fpoly.pro1121.thithu.Service.HttpRequest;
import fpoly.pro1121.thithu.databinding.ActivityAddXeBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddXeActivity extends AppCompatActivity {
    private ActivityAddXeBinding binding;
    private static final String TAG = "AddXeActivity";
    private HttpRequest httpRequest;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddXeBinding.inflate(getLayoutInflater());
        httpRequest = new HttpRequest();
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.imgXeMay.setOnClickListener(v -> {
            if (file != null) {
                file.delete();
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImage.launch(intent);
        });
        binding.btnQuayLai.setOnClickListener(v -> {
            startActivity(new Intent(AddXeActivity.this, MainActivity.class));
        });
        binding.btnThem.setOnClickListener(v -> {
            if (validate()){
                String ten = binding.etTenXeMay.getText().toString();
                String gia = binding.etGiaXeMay.getText().toString();
                String mau = binding.etMauXeMay.getText().toString();
                String moTa = binding.etMoTaXeMay.getText().toString();

                RequestBody ten_xe_ph45090 = RequestBody.create(MediaType.parse("multipart/form-data"), ten);
                RequestBody mau_sac_ph45090 = RequestBody.create(MediaType.parse("multipart/form-data"), mau);
                RequestBody gia_ban_ph45090 = RequestBody.create(MediaType.parse("multipart/form-data"), gia);
                RequestBody mo_ta_ph45090 = RequestBody.create(MediaType.parse("multipart/form-data"), moTa);
                MultipartBody.Part hinh_anh_ph45090;
                if (file != null){
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    hinh_anh_ph45090 = MultipartBody.Part.createFormData("hinh_anh_ph45090", file.getName(), requestFile);
                } else {
                    hinh_anh_ph45090 = null;
                }
                httpRequest.callAPI().addXeMay(ten_xe_ph45090, mau_sac_ph45090, gia_ban_ph45090, mo_ta_ph45090, hinh_anh_ph45090).enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(AddXeActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddXeActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(AddXeActivity.this, "Them that bai" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        Toast.makeText(AddXeActivity.this, "Them that ERR", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }

        });
    }

    private boolean validate() {
        if (binding.etTenXeMay.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui long ko de trong ten", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.etGiaXeMay.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui long ko de trong gia", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.etMauXeMay.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui long ko de trong mau" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.etMoTaXeMay.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui long ko de trong mo ta", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (file == null) {
            Toast.makeText(this, "Vui long chon hinh anh", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        file = createFileFromUri(selectedImageUri);
                        Glide.with(AddXeActivity.this)
                                .load(selectedImageUri)
                                .centerCrop()
                                .circleCrop()
                                .into(binding.imgXeMay);
                    } else {
                        // Handle the case where no image was selected (optional)
                        // You can show a toast message or keep the previous image
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where the user did not pick an image (optional)
                    // You can show a toast message or take appropriate action
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            });

    private File createFileFromUri(Uri uri) {
        File _file = new File(getFilesDir(), "avatar" + ".png");
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            FileOutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
            out.close();
            in.close();
            return _file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}