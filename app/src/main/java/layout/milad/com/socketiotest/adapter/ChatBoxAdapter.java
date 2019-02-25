package layout.milad.com.socketiotest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import layout.milad.com.socketiotest.R;
import layout.milad.com.socketiotest.model.Message;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder> {

    private List<Message> messageList;


    public ChatBoxAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ChatBoxAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Message m = messageList.get(position);
        viewHolder.nickname.setText(m.getNickName());
        viewHolder.message.setText(m.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nickname, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.nickName);
            message = itemView.findViewById(R.id.message);
        }
    }
}

