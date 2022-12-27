package dmytro.app.retrofitstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

public class NumberInfoActivity extends AppCompatActivity {

    TextView text_result_tv;
    ImageButton imageButton;
    private MainData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_info);

        text_result_tv = findViewById(R.id.text_result_tv);
        imageButton = findViewById(R.id.imageButton);

        data = (MainData) getIntent().getSerializableExtra("data");
        if(data!=null) {
            text_result_tv.setText(data.getText());
        }

        imageButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}