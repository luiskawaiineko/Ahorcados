package util;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

class FirebaseAdapter extends FirebaseRecyclerAdapter<String, ChatMessageHolder> {

    Context context;

    public FirebaseAdapter(Class<String> modelClass, int modelLayout, Class<ChatMessageHolder> viewHolderClass, DatabaseReference ref, Context c)
    {
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }

    @Override
    protected void populateViewHolder(ChatMessageHolder chatMessageHolder, final String chatMessage, int position) {
        chatMessageHolder.chatMessage.setText(chatMessage);
    }
}