package fpoly.pro1121.thithu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;

import fpoly.pro1121.thithu.Adapter.XeMayAdapter;
import fpoly.pro1121.thithu.Model.Response;
import fpoly.pro1121.thithu.Model.XeMay;
import fpoly.pro1121.thithu.Service.HttpRequest;
import fpoly.pro1121.thithu.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private HttpRequest httpRequest;
    private ArrayList<XeMay> list;
    private XeMayAdapter adapter;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        httpRequest = new HttpRequest();
        httpRequest.callAPI().getAllXeMay().enqueue(new Callback<Response<ArrayList<XeMay>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<XeMay>>> call, retrofit2.Response<Response<ArrayList<XeMay>>> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    list.addAll(response.body().getData());
                    adapter = new XeMayAdapter(list, MainActivity.this);
                    binding.recXeMay.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    binding.recXeMay.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<XeMay>>> call, Throwable throwable) {
                //Toast.makeText(MainActivity.this, "ERR : " +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + throwable.getMessage());
            }
        });

        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddXeActivity.class));
        });
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = binding.edSearch.getText().toString().trim();
                httpRequest.callAPI()
                        .searchXeMay(query)
                        .enqueue(new Callback<Response<ArrayList<XeMay>>>() {
                            @Override
                            public void onResponse(Call<Response<ArrayList<XeMay>>> call, retrofit2.Response<Response<ArrayList<XeMay>>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Response<ArrayList<XeMay>> responseBody = response.body();
                                    if (responseBody.getData() != null) {
                                        list.clear();
                                        list.addAll(responseBody.getData());
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.d("TAG:HomeFragment", "Data is null");
                                    }
                                } else {
                                    try {
                                        Log.d("TAG:HomeFragment", "Error response: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<ArrayList<XeMay>>> call, Throwable throwable) {
                                Log.d("DDDDD", "onFailure: " + throwable.getMessage());
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        httpRequest.callAPI().getAllXeMay().enqueue(new Callback<Response<ArrayList<XeMay>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<XeMay>>> call, retrofit2.Response<Response<ArrayList<XeMay>>> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    list.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<XeMay>>> call, Throwable throwable) {
                //Toast.makeText(MainActivity.this, "ERR : " +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }
}