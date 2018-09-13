package com.seuchild.smallseedling.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.messageHolder> {

    private Context messageContext;
    private List<String> persons;
    private List<String> messages;

    public MessageAdapter(Context context,List<String> text,List<String> persons){
        this.messageContext = context;
        this.messages = text;
        this.persons = persons;
    }

    @NonNull
    @Override
    public messageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(messageContext).inflate(R.layout.message_item,null);
        return new messageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messageHolder messageHolder, int position) {
        messageHolder.textView.setText(messages.get(position));
        if(persons.get(position).equals("父亲")){
            messageHolder.imageView.setImageResource(R.drawable.message_father);
        }else if(persons.get(position).equals("母亲")){
            messageHolder.imageView.setImageResource(R.drawable.message_mother);
        }else if(persons.get(position).equals("同学")){
            messageHolder.imageView.setImageResource(R.drawable.message_friend);
        }else{
            messageHolder.imageView.setImageResource(R.drawable.message_teacher);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class messageHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;
        public messageHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
        }
    }


}
