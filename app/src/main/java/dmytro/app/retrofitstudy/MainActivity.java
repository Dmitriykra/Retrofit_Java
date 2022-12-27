package dmytro.app.retrofitstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmytro.app.retrofitstudy.Model.ActualNumber;
import dmytro.app.retrofitstudy.Model.JsonPlaceHolderApi;
import dmytro.app.retrofitstudy.Model.RandomNumber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int userNumber;
    private TextView text_result_tv, previous_requests_tv;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Button get_fact_btn, get_rand_fact_btn;
    private TextInputEditText enterNumber;
    private TextInputLayout enterLayout;
    private String enterNumber_st;
    private RecyclerView recyclerView;
    List<MainData> dataList = new ArrayList<>();
    RoomData roomData;
    MainAdapter mainAdapter;
    private int lastPosition;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    private ProgressBar progress_circular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_result_tv = findViewById(R.id.text_result_tv);
        get_fact_btn = findViewById(R.id.get_fact_btn);
        get_rand_fact_btn = findViewById(R.id.get_rand_fact_btn);
        enterNumber = findViewById(R.id.number_et);
        enterLayout = findViewById(R.id.number_il);
        recyclerView = findViewById(R.id.rec_view_start_id);
        previous_requests_tv = findViewById(R.id.previous_requests_tv);
        progress_circular = findViewById(R.id.progress_circular);

        roomData = RoomData.getInstance(this);
        dataList = roomData.myDao().getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(mainAdapter);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://numbersapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        get_fact_btn.setOnClickListener(v -> {
            progress_circular.setVisibility(View.VISIBLE);
            enterNumber_st = enterNumber.getText().toString().trim();
            if(enterNumber_st.isEmpty()){
                Toast.makeText(this, "Please, wright a number", Toast.LENGTH_SHORT).show();
                progress_circular.setVisibility(View.GONE);
                return;
            }
            try {
                userNumber = Integer.parseInt(Objects.requireNonNull(enterNumber.getText())
                        .toString().trim());
                text_result_tv.setVisibility(View.VISIBLE);
                GetActualNumber(userNumber);
                enterLayout.setErrorEnabled(false);
            } catch (NumberFormatException numberFormatException){
                enterLayout.setErrorEnabled(true);
                enterLayout.setError("Please, wright correct number");
                progress_circular.setVisibility(View.GONE);
            }
        });
        get_rand_fact_btn.setOnClickListener(v -> {
            progress_circular.setVisibility(View.VISIBLE);
            text_result_tv.setVisibility(View.VISIBLE);
            GetRandomNumbers();
            previous_requests_tv.setText(getString(R.string.history) + lastPosition);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        pref = getApplicationContext().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = pref.edit();
        lastPosition = pref.getInt("lastPosition", 0);
        previous_requests_tv.setText(getString(R.string.history) + lastPosition);

    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void GetActualNumber(int newUserNumber) {
        Call<ActualNumber> call = jsonPlaceHolderApi.getActualNumber(newUserNumber);
        call.enqueue(new Callback<ActualNumber>() {
            @Override
            public void onResponse(Call<ActualNumber> call, Response<ActualNumber> response) {
                progress_circular.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Some error, try again", Toast.LENGTH_SHORT).show();
                }

                ActualNumber actualNumber =  response.body();
                String content = actualNumber.getText().toString().trim();
                text_result_tv.setText(content);
                AddData(content);
            }

            @Override
            public void onFailure(Call<ActualNumber> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*private void GetNumbers(){
        Call<List<RandomNumber>> call = jsonPlaceHolderApi.getNumbers();
        call.enqueue(new Callback<List<RandomNumber>>() {
            @Override
            public void onResponse(Call<List<RandomNumber>> call, Response<List<RandomNumber>> response) {
                Log.d("TAG", "onResponse: "+response);
                if(!response.isSuccessful()){
                    textView.setText("Code: "+response.body());
                    return;
                }
                List<RandomNumber> numberList = response.body();
                for(RandomNumber number : numberList){
                    String number_result = number.getText();
                    textView.setText(number_result);
                }
            }

            @Override
            public void onFailure(Call<List<RandomNumber>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }*/

    private void GetRandomNumbers() {
        Call<RandomNumber> call = jsonPlaceHolderApi.getRandomNumbers();
        call.enqueue(new Callback<RandomNumber>() {
            @Override
            public void onResponse(Call<RandomNumber> call, Response<RandomNumber> response) {
                progress_circular.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Some error, try again", Toast.LENGTH_SHORT).show();
                }
                RandomNumber randomNumber =  response.body();
                String content = randomNumber.getText().toString().trim();
                text_result_tv.setText(content);
                AddData(content);
            }

            @Override
            public void onFailure(Call<RandomNumber> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                progress_circular.setVisibility(View.GONE);

            }
        });
    }

    private void AddData(String number_text){
        MainData data = new MainData();
        data.setText(number_text);
        roomData.myDao().insert(data);
        dataList.clear();
        dataList.addAll(roomData.myDao().getAll());
        lastPosition = dataList.size();
        previous_requests_tv.setText(getString(R.string.history) + lastPosition);
        recyclerView.smoothScrollToPosition(lastPosition);
        mainAdapter.notifyDataSetChanged();
        SavedRequestSize(lastPosition);
    }

    private void SavedRequestSize(int lastPosition) {
        editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putInt("lastPosition", lastPosition);
        editor.apply();
    }
}