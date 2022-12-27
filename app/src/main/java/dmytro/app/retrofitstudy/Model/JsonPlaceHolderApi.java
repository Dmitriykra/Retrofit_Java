package dmytro.app.retrofitstudy.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("random/math?json")
    Call<RandomNumber> getRandomNumbers();

    @GET("{number}?json")
    Call<ActualNumber> getActualNumber(@Path("number") int number);


}
