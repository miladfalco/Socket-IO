package layout.milad.com.socketiotest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import layout.milad.com.socketiotest.R;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText nickName;
    public static final String NICKNAME = "usernickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.enterchat);
        nickName = findViewById(R.id.nickname);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nickName.getText().toString().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, ChatBoxActivity.class);
                    intent.putExtra(NICKNAME, nickName.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}
