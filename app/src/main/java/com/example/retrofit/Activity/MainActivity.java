package com.example.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofit.API.BankAPIClient;
import com.example.retrofit.Adapter.BankAdapter;
import com.example.retrofit.Entity.BankResponse;
import com.example.retrofit.Entity.Banks;
import com.example.retrofit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private BankAdapter bankAdapter;
    private ArrayList<Banks> banksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_bank);
        rv.setLayoutManager(new GridLayoutManager(this, 4)); // Hiển thị 4 mục trên mỗi hàng

        banksList = new ArrayList<>();
        bankAdapter = new BankAdapter(this, banksList);
        rv.setAdapter(bankAdapter);

        // Call API
        BankAPIClient bankAPIClient = new BankAPIClient();
        bankAPIClient.getBankService().getBanks().enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BankResponse bankResponse = response.body();
                    List<Banks> bankList = bankResponse.getBankList();
                    int size = bankList.size();
                    banksList.clear();
                    banksList.addAll(bankList);
                    bankAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BankResponse> call, Throwable t) {
                Log.d("APICHECKLOG", t.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
