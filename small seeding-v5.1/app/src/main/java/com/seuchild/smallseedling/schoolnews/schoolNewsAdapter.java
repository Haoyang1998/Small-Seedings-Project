package com.seuchild.smallseedling.schoolnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seuchild.smallseedling.R;

import java.util.List;

public class schoolNewsAdapter extends RecyclerView.Adapter<schoolNewsAdapter.newsViewHolder> {

    private Context schoolnewsContext;
    private List<String> title,type,time,place,introduce;
    private List<String> imagess;
    private List<String> webUrls;
    public schoolNewsAdapter(Context context,List<String> title,List<String> type,List<String> time,List<String> place,List<String> introduce,List<String> images,List<String> webUrls){
        this.schoolnewsContext = context;
        this.title = title;
        this.time = time;
        this.type = type;
        this.place = place;
        this.introduce = introduce;
        this.imagess = images;
        this.webUrls = webUrls;
    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(schoolnewsContext).inflate(R.layout.schoolnews_item,null);
        return new newsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder viewHolder, int position) {
        viewHolder.textView1.setText(title.get(position));
        viewHolder.textView2.setText(type.get(position));
        viewHolder.textView3.setText(time.get(position));
        viewHolder.textView4.setText(place.get(position));
        viewHolder.textView5.setText(introduce.get(position));
        viewHolder.webView.setText(webUrls.get(position));
        System.out.println(webUrls.get(position)+"孙国钦孙国钦！！！");
        Glide.with(schoolnewsContext).load(imagess.get(position)).into(viewHolder.imageView);
        System.out.println("Adapter里面 图片的Url!!!!!!!!"+imagess.get(position));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    class newsViewHolder extends RecyclerView.ViewHolder{

        private TextView textView1,textView2,textView3,textView4,textView5,webView;
        private ImageView imageView;

        public newsViewHolder(View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            webView = itemView.findViewById(R.id.webTextView);

        }
    }


}

