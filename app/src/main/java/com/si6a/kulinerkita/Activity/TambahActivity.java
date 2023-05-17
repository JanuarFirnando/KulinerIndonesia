package com.si6a.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si6a.kulinerkita.API.APIRequestData;
import com.si6a.kulinerkita.API.RetroServer;
import com.si6a.kulinerkita.Model.ModelResponse;
import com.si6a.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText et_nama, et_asal, et_deskripsi_singkat;
    private String nama, asal, deskripsi_singkat;
    private Button btn_tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        et_nama = findViewById(R.id.et_nama);
        et_asal = findViewById(R.id.et_asal);
        et_deskripsi_singkat = findViewById(R.id.et_deskripsi_singkat);
        btn_tambah = findViewById(R.id.btn_tambah);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = et_nama.getText().toString();
                asal = et_asal.getText().toString();
                deskripsi_singkat = et_deskripsi_singkat.getText().toString();

                if(nama.trim().equals(""))
                {
                    et_nama.setError("nama harus diisi");
                }
                if(asal.trim().equals(""))
                {
                    et_asal.setError("Asal Kota harus diisi");
                }
                if(deskripsi_singkat.trim().equals(""))
                {
                    et_deskripsi_singkat.setError("Deskripsi Singkat harus diisi");
                }
                else{
                    tambahKuliner();
                }
            }
        });

    }

    private void tambahKuliner()
    {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(nama,asal,deskripsi_singkat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(TambahActivity.this, "Kode : " + kode + ", Pesan : " + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}