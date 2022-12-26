package dmytro.app.retrofitstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    private TextView text_result_tv;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Button get_fact_btn, get_rand_fact_btn;
    private TextInputEditText enterNumber;
    private TextInputLayout enterLayout;
    private String enterNumber_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_result_tv = findViewById(R.id.text_result_tv);
        get_fact_btn = findViewById(R.id.get_fact_btn);
        get_rand_fact_btn = findViewById(R.id.get_rand_fact_btn);
        enterNumber = findViewById(R.id.number_et);
        enterLayout = findViewById(R.id.number_il);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://numbersapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //раскоментируй этот метод и закоментируй GetComments();
        //getPosts();
        //GetComments();
        //GetRandomNumbers();



        get_fact_btn.setOnClickListener(v -> {
            enterNumber_st = enterNumber.getText().toString().trim();
            if(enterNumber_st.isEmpty()){
                Toast.makeText(this, "Please, wright a number", Toast.LENGTH_SHORT).show();
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
            }
        });
        get_rand_fact_btn.setOnClickListener(v -> {
            text_result_tv.setVisibility(View.VISIBLE);
            GetRandomNumbers();
        });
    }

    private void GetActualNumber(int newUserNumber) {
        Call<ActualNumber> call = jsonPlaceHolderApi.getActualNumber(newUserNumber);
        call.enqueue(new Callback<ActualNumber>() {
            @Override
            public void onResponse(Call<ActualNumber> call, Response<ActualNumber> response) {
                if(!response.isSuccessful()){
                    text_result_tv.setText("Code " + response.body());
                }

                ActualNumber numberCls =  response.body();
                String content = numberCls.getText().toString().trim();
                text_result_tv.setText(content);

            }

            @Override
            public void onFailure(Call<ActualNumber> call, Throwable t) {
                text_result_tv.setText(t.getMessage());

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
                if(!response.isSuccessful()){
                    text_result_tv.setText("Code " + response.body());
                }

                RandomNumber numberCls =  response.body();
                String content = numberCls.getText().toString().trim();
                text_result_tv.setText(content);

            }

            @Override
            public void onFailure(Call<RandomNumber> call, Throwable t) {
                text_result_tv.setText(t.getMessage());

            }
        });
    }


    /*private void GetComments() {
        //Создаем вызов списка из класса-модели и присваеваем ему
        //метод из интерфейса
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code " + response.body());
                }

                List<Comment> commentList =  response.body();
                for (Comment comment : commentList) {
                    String content = "";
                    content += "id: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() +"\n";
                    content += "Text: " + comment.getText() +"\n\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }*/

    /*private void getPosts() {
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post : posts){
                    String content = "";
                    content += "Id: " + post.getId() + "\n";
                    content += "UserId: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text " + post.getText() +"\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }*/
}