package util;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luismi.ahorcado.R;

public class ChatMessageHolder extends RecyclerView.ViewHolder {
    TextView chatMessage;

    public void setChatMessage(String chatMessage) {
        this.chatMessage.setText(chatMessage);
    }

    public ChatMessageHolder(View itemView) {
        super(itemView);
        chatMessage = itemView.findViewById(R.id.chatMessage);
    }
}
