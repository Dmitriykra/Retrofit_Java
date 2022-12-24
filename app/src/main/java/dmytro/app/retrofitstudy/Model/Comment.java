package dmytro.app.retrofitstudy.Model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;

    //Тут название строки отличается от названия поля
    // в json объекте, поэтому мы добавляем аннотацию
    //@SerializedName("body") в которую вписываем
    //истенное название поля в json объекте - body
    @SerializedName("body")
    private String text;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
