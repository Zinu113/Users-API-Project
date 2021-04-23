package com.julius.usersproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.julius.usersproject.model.User;
import com.julius.usersproject.service.ApiClient;
import com.julius.usersproject.service.CustomAdapter;
import com.julius.usersproject.service.GetService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


    public class MainActivity extends AppCompatActivity {
        private CustomAdapter adapter;
        private RecyclerView recyclerView;
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("loading");
            progressDialog.show();

            GetService service = ApiClient.getRetrofitInstance().create(GetService.class);
            Call<List<User>> call = service.getUsersList();
            call.enqueue(new Callback<List<User>>(){
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response){
                    progressDialog.dismiss();
                    generateDataList(response.body());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan, harap coba lagi!"+t, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void generateDataList(List<User> userList){
            recyclerView = findViewById(R.id.CsRecyclerView);
            adapter      = new CustomAdapter(this, userList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }