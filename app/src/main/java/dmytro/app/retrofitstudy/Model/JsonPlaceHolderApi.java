package dmytro.app.retrofitstudy.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    // В аннотации мы указываем значение эндпоинта
    //строки url
    @GET("posts")
    Call<List<Post>> getPost();

    //добавляем следующий меод для получения определенной ссылки согласно эндпоинту
    @GET("posts/2/comments")
    Call<List<Comment>> getComments();

    @GET("random/math?json")
    Call<RandomNumber> getRandomNumbers();

    @GET("{number}?json")
    Call<ActualNumber> getActualNumber(@Path("number") int number);


}
