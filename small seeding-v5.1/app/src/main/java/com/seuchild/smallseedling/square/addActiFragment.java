package com.seuchild.smallseedling.square;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * create by cz
 */
public class addActiFragment extends Fragment {
    private static final int IMAGE=1;
    private Context mContext;
    public View view;
    private Button mbutton_addphotos;
    private ImageView miv_addpresent;
    private EditText meditText;
    private OkHttpClient client=new OkHttpClient();
    private String iamge_url;

    public void setImage_url(String s){
        this.iamge_url=s;

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mContext=getContext();
        view=inflater.inflate(R.layout.fragment_addactivities,null);
        //部件初始化
        mbutton_addphotos=view.findViewById(R.id.bu_adda_photos);
        miv_addpresent=view.findViewById(R.id.adda_iv_present);
        meditText=view.findViewById(R.id.et_adda);

        //添加监听事件
        //添加图片
        mbutton_addphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMAGE);
            }
        });




        //动态发布，重要组件
        miv_addpresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str=meditText.getText().toString();
                if(str.equals("")){
                    Toast toast=Toast.makeText(mContext,"上传动态不能为空", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Thread post=new Thread(new Runnable() {
                        @Override
                        public void run() {
                           String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/acti";


                            FormBody formBody=new FormBody.Builder().add("m_content",str).
                                    add("m_name","沈芷夏").add("m_url",iamge_url).build();
                            Request request = new Request.Builder().url(url).post(formBody).build();
                            try {
                                Response response = client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    post.start();
                    getActivity().finish();
                    startActivity(new Intent(getContext(),SquareActivity.class));
                }

            }
        });
        return view;


    }




    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    public static addActiFragment newInstance(){return new addActiFragment();}


    //重载该方法，获取图片的路径
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        System.out.println(requestCode+"");
        if(requestCode==1)
        {
            Uri uri = data.getData();
            ContentResolver cr = this.getContext().getContentResolver();
            Bitmap bitmap;
            try
            {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                System.out.println("GOOD");
                Cursor cursor =this.getContext().getContentResolver().query(uri, null, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                final String path = cursor.getString(column_index);
                System.out.println(path);
                final String key="9a98d55f6cdf1d566a2c96439e121d18";

                File file = new File(path);
                if(file.isFile())
                {
                    final MediaType MEDIA_TYPE_JPG = MediaType.parse("application/octet-stream");
                    RequestBody requestBody =  new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("key", "9a98d55f6cdf1d566a2c96439e121d18")
                            .addFormDataPart("format","txt")
                            .addFormDataPart("source", file.getName(),RequestBody.create(MEDIA_TYPE_JPG, file)).build();
                    String url_api="http://39.105.38.48/api/1/upload";
                   final Request request = new Request.Builder()
                            .url(url_api)
                            .post(requestBody)
                            .build();
                    Thread present=new Thread(new Runnable() {
                        //实现异步加载
                        public void run(){
                            Call call =  client.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                       System.out.println("failed");
                                   }
                                   @Override
                                   public void onResponse(Call call, Response response) throws IOException {
                                           String s =response.body().string();
                                           setImage_url(s);
                                           System.out.println(iamge_url);
                                   }
                               });
                            }


                    });
                    present.start();

                }
                else
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");

            }
            catch (Exception e)
            {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                System.out.println("BAD");

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
