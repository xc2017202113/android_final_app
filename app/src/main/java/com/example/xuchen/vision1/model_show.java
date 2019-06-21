package com.example.xuchen.vision1;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.threed.jpct.Object3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class model_show extends AppCompatActivity {



    public final static int MSG_LOAD_MODEL_SUC = 0;
    private GLSurfaceView myGLView;
    private RenderView myRenderer;
    private Button btnLoad;
    private MyAdapter adapter;

    private static final String TAG = "model_show";
    private Thread threadLoadModel;
    public static Handler handler;
    private Spinner sex_spinner;
    private Spinner height_spinner;
    private Spinner weight_spinner;
    private List<Cloth>clothList=new ArrayList<>();
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private ImageView picture;
    private Uri imageUri;
    private String picname;

    //进入设置界面;
    private Button btn_set;

    private int Count;

    private String weight_data;
    private String height_data;
    public static String file_name_data;


    //szs
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("wymdsb1","aaaaaaaaaaaaaaaaaa111111");
        weight_data = "65";
        height_data = "175";
        Count = 0;
        picname = "c1";
        file_name_data = "man-"+height_data+"-"+weight_data;
        Log.d("wymdsb1","aaaaaaaaaaaaaaaaaa222222");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_show);

        //szs
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }


        Log.d("wymdsb1","aaaaaaaaaaaaaaaaaa");
        Intent now_intent = getIntent();
        file_name_data = now_intent.getStringExtra("change");
        picname = now_intent.getStringExtra("picname_second");
        if(file_name_data == null)
        {
            file_name_data = "man-175-65";
        }
        if(picname == null)
        {
            picname = "c1";
        }

        Log.d(TAG, "onCreatasdfhasuidhguiadhg"+file_name_data);

        Button btnChange;
        btnChange=(Button)findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(model_show.this,test.class);
                String file_name="man-"+height_data+"-"+weight_data;
                picname = adapter.file_id;
                intent.putExtra("label",file_name);
                intent.putExtra("picname",picname);

                Toast showToast = Toast.makeText(model_show.this, file_name, Toast.LENGTH_SHORT);
                showToast.show();
                startActivity(intent);
                //finish();
            }
        });


        //进设置界面
        btn_set = findViewById(R.id.button_set);
        btn_set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                saveImage(bmp);
                Intent intent = new Intent(model_show.this,Setting.class);
                startActivity(intent);
            }
        });


        ReLoad();
        OtherView();
        //threadLoadModel.stop();



    }
//szs
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }


    public void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "sina");
        File filesDir = model_show.this.getFilesDir();
        Log.d("xyz", String.valueOf(filesDir));
        if (!appDir.exists()) {
            appDir.mkdirs();
            Log.d("xyz", String.valueOf(appDir));
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        model_show.this.sendBroadcast(intent);
    }
//szs


    private void OtherView()
    {

        sex_spinner=(Spinner)findViewById(R.id.sex_spinner);
        sex_spinner.setSelection(0,true);
        sex_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)sex_spinner.getItemAtPosition(position);
                Toast.makeText(model_show.this,data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        height_spinner=(Spinner)findViewById(R.id.height_spinner);
        height_spinner.setSelection(0,true);
        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)height_spinner.getItemAtPosition(position);
                height_data = data;
                if (data!="height"){
                    updata_file();
                }
                Toast.makeText(model_show.this,data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        weight_spinner=(Spinner)findViewById(R.id.weight_spinner);
        weight_spinner.setSelection(0,true);
        weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)weight_spinner.getItemAtPosition(position);
                weight_data = data;
                if (data!="weight"){
                    updata_file();
                }
                Log.d(TAG, "onItemSelected: "+weight_data +"0009sdf"+data);
                Toast.makeText(model_show.this,data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initCloth();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyAdapter(clothList);
        recyclerView.setAdapter(adapter);




        Button takePhoto=(Button)findViewById(R.id.btnCamera);
        picture=(ImageView)findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File outputImage=new File(getExternalCacheDir(),"outputImage.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri=FileProvider.getUriForFile(model_show.this,"com.example.xuchen.vision1.cameraalbumtest.fileprovider",outputImage);
                }else{
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });


        Button chooseFromAlbum=(Button)findViewById(R.id.btnAlbum);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(model_show.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(model_show.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else{
                    openAlbum();
                }
            }
        });
    }



    private void updata_file()
    {
        file_name_data = "man-"+height_data+"-"+weight_data;
    }

    @SuppressLint("HandlerLeak")
    private void ReLoad()
    {

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "model_show:handleMessage: " + msg.what);
                switch (msg.what) {
                    case MSG_LOAD_MODEL_SUC:
                        myRenderer.myWorld.removeAllObjects();
                        Toast.makeText(model_show.this, "模型加载成功", Toast.LENGTH_SHORT).show();
                        Object3D object3D = (Object3D) msg.obj;
                        myRenderer.myWorld.addObject(object3D);

                        break;
                }
            }

        };

        btnLoad = findViewById(R.id.btnLoadModel);
        //myRenderer= new RenderView(this,file_name_data);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ReLoad();

                Toast.makeText(model_show.this, "开始加载模型", Toast.LENGTH_SHORT).show();
                threadLoadModel.start();
            }
        });

        myGLView = (GLSurfaceView) this.findViewById(R.id.surfaceView);
        myGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        myGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        myGLView.setZOrderOnTop(true);

        //file_name_data = "man-"+height_data+"-"+weight_data;
        Log.d(TAG, "asdfasdfaf"+file_name_data);
        myRenderer = new RenderView(model_show.this,file_name_data);

        myGLView.setRenderer(myRenderer);
        Log.d(TAG, "ReLoad: "+picname);
        threadLoadModel = new Thread(new Runnable() {
            @Override
            public void run() {
                myRenderer.addObject(model_show.this,1024,picname);
            }
        });
    }


    private void initCloth(){


        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String style_s =pref.getString("style","");
        Log.d("xyz1",style_s);


         if(style_s.equals("style2")){
            Cloth c4=new Cloth("c4",R.drawable.c4);
            clothList.add(c4);
            Cloth c5=new Cloth("c5",R.drawable.c5);
            clothList.add(c5);
            Cloth c6=new Cloth("c6",R.drawable.c6);
            clothList.add(c6);
        }
        if(style_s.equals("style1") || style_s.equals("")){
            Cloth c1=new Cloth("c1",R.drawable.c1);
            clothList.add(c1);
            Cloth c2=new Cloth("c2",R.drawable.c2);
            clothList.add(c2);
            Cloth c3=new Cloth("c3",R.drawable.c3);
            clothList.add(c3);
        }

    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        saveImage(bitmap);
                        picture.setImageBitmap(bitmap);
                        //TODO:take a photo bitmap


                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
                default:
                    break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                    Uri contentUri=ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                    imagePath=getImagePath(contentUri,null);
                }
            } else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath=getImagePath(uri,null);
            } else if("file".equalsIgnoreCase(uri.getScheme())){
                imagePath=uri.getPath();
            }
            displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            saveImage(bitmap);
            picture.setImageBitmap(bitmap);
            //TODO:from album bitmap
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }

}
