package dmytro.app.retrofitstudy.Model;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int userId;
    private int id;
    private String title;

    //Тут название строки отличается от названия поля
    // в json объекте, поэтому мы добавляем аннотацию
    //@SerializedName("body") в которую вписываем
    //истенное название поля в json объекте - body
    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
