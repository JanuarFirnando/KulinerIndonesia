package com.si6a.kulinerkita.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.si6a.kulinerkita.Model.ModelKuliner;
import com.si6a.kulinerkita.R;

import java.util.List;

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
        }
    }
}
