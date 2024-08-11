package fpoly.pro1121.thithu.Adapter;

import static fpoly.pro1121.thithu.Service.ApiService.BASE_URL;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.pro1121.thithu.Model.Response;
import fpoly.pro1121.thithu.Model.XeMay;
import fpoly.pro1121.thithu.R;
import fpoly.pro1121.thithu.Service.HttpRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class XeMayAdapter extends RecyclerView.Adapter<XeMayAdapter.ViewHolder> {
    ArrayList<XeMay> listXeMay;
    Context context;
    HttpRequest httpRequest;

    public XeMayAdapter(ArrayList<XeMay> listXeMay, Context context) {
        this.listXeMay = listXeMay;
        this.context = context;
        httpRequest = new HttpRequest();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rec_xe_may, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        XeMay xeMay = listXeMay.get(position);
        holder.tv_ten_xe_may.setText(xeMay.getTen_xe_ph45090());
        holder.tv_gia_xe_may.setText(xeMay.getGia_ban_ph45090() + " VND");
        holder.tv_mau_xe_may.setText(xeMay.getMau_sac_ph45090());
        Glide.with(holder.itemView.getContext()).load(BASE_URL + xeMay.getHinh_anh_ph45090()).into(holder.img_xe_may);
        holder.btn_detail.setOnClickListener(v -> {
            String id = xeMay.get_id();
            httpRequest.callAPI().deleteXeMay(id).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                    if (response.isSuccessful()) {
                        listXeMay.remove(xeMay);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {

                }
            });
        });
        holder.itemView.setOnClickListener(v -> {
            String id = xeMay.get_id();
            HttpRequest httpRequest = new HttpRequest();
            // Lấy thông tin xe máy theo id
            httpRequest.callAPI().getXeMayById(id).enqueue(new Callback<Response<XeMay>>() {
                @Override
                public void onResponse(Call<Response<XeMay>> call, retrofit2.Response<Response<XeMay>> response) {
                    if (response.isSuccessful()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = View.inflate(context, R.layout.detail_dialog, null);

                        ImageView img_xe_may = view.findViewById(R.id.img_xe_mayD);
                        TextView tv_ten_xe_may = view.findViewById(R.id.tv_ten_xe_mayD);
                        TextView tv_gia_xe_may = view.findViewById(R.id.tv_gia_xe_mayD);
                        TextView tv_mau_xe_may = view.findViewById(R.id.tv_mau_xe_mayD);
                        TextView tv_mo_ta_xe_may = view.findViewById(R.id.tv_mo_ta_xe_mayD);

                        Glide.with(context).load(BASE_URL + response.body().getData().getHinh_anh_ph45090()).into(img_xe_may);
                        tv_ten_xe_may.setText(response.body().getData().getTen_xe_ph45090());
                        tv_gia_xe_may.setText("Giá: " + response.body().getData().getGia_ban_ph45090() + " VND");
                        tv_mau_xe_may.setText("Màu:" + response.body().getData().getMau_sac_ph45090());
                        tv_mo_ta_xe_may.setText("Mô tả :" + response.body().getData().getMo_ta_ph45090());
                        builder.setView(view);
                        builder.show();
                    }
                }

                @Override
                public void onFailure(Call<Response<XeMay>> call, Throwable throwable) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listXeMay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_xe_may;
        TextView tv_ten_xe_may;
        TextView tv_gia_xe_may;
        TextView tv_mau_xe_may;
        Button btn_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_xe_may = itemView.findViewById(R.id.img_xe_may);
            tv_ten_xe_may = itemView.findViewById(R.id.tv_ten_xe_may);
            tv_gia_xe_may = itemView.findViewById(R.id.tv_gia_xe_may);
            tv_mau_xe_may = itemView.findViewById(R.id.tv_mau_xe_may);
            btn_detail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
