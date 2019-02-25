package layout.milad.com.socketiotest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import layout.milad.com.socketiotest.R;
import layout.milad.com.socketiotest.adapter.ChatBoxAdapter;
import layout.milad.com.socketiotest.model.Message;

public class ChatBoxActivity extends AppCompatActivity {

    private Socket socket;
    private String nickName;
    private RecyclerView recyclerView;
    private List<Message> messagesList;
    private ChatBoxAdapter chatBoxAdapter;
    private EditText messagetxt;
    private Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        messagetxt = findViewById(R.id.edit_message);
        send = findViewById(R.id.send);
        messagesList = new ArrayList<>();
        recyclerView = findViewById(R.id.messagelist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messagetxt.getText().toString().isEmpty()) {
                    socket.emit("messagedetection", nickName, messagetxt.getText().toString());
                    messagetxt.setText("");
                }
            }
        });

        nickName = getIntent().getExtras().getString(MainActivity.NICKNAME);
        try {
            socket = IO.socket("http://localhost:3000");
            socket.connect();
            socket.emit("join", nickName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];

                        try {
                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");

                            Message m = new Message(nickname, message);
                            messagesList.add(m);

                            chatBoxAdapter = new ChatBoxAdapter(messagesList);
                            chatBoxAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(chatBoxAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}

