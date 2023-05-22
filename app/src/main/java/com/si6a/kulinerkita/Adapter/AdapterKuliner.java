package com.si6a.kulinerkita.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si6a.kulinerkita.API.APIRequestData;
import com.si6a.kulinerkita.API.RetroServer;
import com.si6a.kulinerkita.Activity.MainActivity;
import com.si6a.kulinerkita.Model.ModelKuliner;
import com.si6a.kulinerkita.Model.ModelResponse;
import com.si6a.kulinerkita.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKuliner extends RecyclerView.Adapter<AdapterKuliner.VHKuliner> {

    private Context ctx;
    private List<ModelKuliner> listkuliner;

    public AdapterKuliner(Context ctx, List<ModelKuliner> listkuliner) {
        this.ctx = ctx;
        this.listkuliner = listkuliner;
    }


    @NonNull
    @Override
    public AdapterKuliner.VHKuliner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner,parent,false);
        return new VHKuliner(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKuliner.VHKuliner holder, int position) {
        ModelKuliner MK = listkuliner.get(position);

        holder.tvID.setText(MK.getId());
        holder.tvNama.setText(MK.getNama() + " (" + MK.getAsal() + ")");

        holder.tvDeskripsiSingkat.setText(MK.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listkuliner.size();
    }

    public class VHKuliner extends RecyclerView.ViewHolder {
        TextView tvID, tvNama, tvAsal, tvDeskripsiSingkat;

        public VHKuliner(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_deskripsi_singkat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HapusKuliner(tvID.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    pesan.show();
                    return false;
                }
            });
        }

        private void HapusKuliner(String idKuliner)
        {
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ARD.ardDelete(idKuliner);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode: " + kode + " Pesan: " +pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).RetrieveKuliner();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
