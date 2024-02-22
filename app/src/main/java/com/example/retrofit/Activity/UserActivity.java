package com.example.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.retrofit.API.UserAPIClient;
import com.example.retrofit.API.UserService;
import com.example.retrofit.Adapter.UserAdapter;
import com.example.retrofit.Entity.User;
import com.example.retrofit.Entity.UserRespose;
import com.example.retrofit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private RecyclerView rv_user;
    private UserAdapter userAdapter;
    private Spinner sp_page;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        rv_user = findViewById(R.id.rv_user);
        //Set layout dạng linear
        rv_user.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, userList);
        rv_user.setAdapter(userAdapter);

        sp_page = findViewById(R.id.sp_page);

        // Thiết lập Spinner
        setupSpinner();

        // Gọi API để lấy dữ liệu cho trang đầu tiên
        fetchDataFromAPI(1);
    }

    private void setupSpinner() {
        // Tạo mảng số trang
        String[] pages = new String[]{"1", "2", "3", "4", "5"};

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pages);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_page.setAdapter(spinnerAdapter);

        // Đặt sự kiện khi chọn 1 item trong spinner
        sp_page.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy số trang được chọn từ Spinner
                int selectedPage = Integer.parseInt(sp_page.getSelectedItem().toString());

                // Gọi API để lấy dữ liệu cho trang được chọn
                fetchDataFromAPI(selectedPage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì khi không có trang nào được chọn
            }
        });
    }

    private void fetchDataFromAPI(int page) {
        // Khởi tạo Retrofit Client
        UserAPIClient userAPIClient = new UserAPIClient();
        UserService userService = userAPIClient.getUserService();

        // Gọi API
        Call<UserRespose> call = userService.getUsers(page);

        call.enqueue(new Callback<UserRespose>() {
            @Override
            public void onResponse(Call<UserRespose> call, Response<UserRespose> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserRespose userResponse = response.body();
                    userList.clear(); // Xóa dữ liệu cũ
                    userList.addAll(userResponse.getData()); // Thêm dữ liệu mới
                    userAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                } else {
                    // Xử lý trường hợp không thành công khi gọi API
                    Toast.makeText(UserActivity.this, "Lỗi lấy dữ liệu:", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserRespose> call, Throwable t) {
                // Xử lý trường hợp gặp lỗi khi gọi API
                Toast.makeText(UserActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
