package com.example.xuchen.vision1;

import android.content.SharedPreferences;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.os.Message;
import android.util.Log;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.Context.MODE_PRIVATE;

public class RenderView implements GLSurfaceView.Renderer{

    private static final String TAG = "RenderView";
    public model_show show1;

    public World myWorld;
    private FrameBuffer frameBuffer;
    private Object3D selectedObj;

    String filename;
    String objfilename;
    String mtlfilename;
    String texturefilename;

    public RenderView(Context context,String Filename) {
        myWorld = new World();
        myWorld.setAmbientLight(25, 25, 25);

        filename = Filename;
        objfilename = Filename+".obj";
        mtlfilename = Filename+".mtl";
        texturefilename = Filename+"_texture";

        Log.d(TAG, "RenderView: "+objfilename);


        Light light = new Light(myWorld);
        light.setIntensity(250, 250, 250);
        light.setPosition(new SimpleVector(0, 0, -15));
        Camera cam = myWorld.getCamera();
        cam.setFOVLimits(0.1f,2.0f);
        cam.setFOV(1.08f);
        cam.setYFOV(1.92f);
        cam.setClippingPlanes(0f,2000f);
        System.out.println(cam.getFOV());
        System.out.println(cam.getYFOV());
        System.out.println(cam.getPosition());
        String[] names=Config.getParameterNames();
        for(String i:names){
            System.out.println(i);
        }

    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {

        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        frameBuffer = new FrameBuffer(gl, w, h);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(1.0f,1.0f,1.0f,0.3f);
    }

    public void onDrawFrame(GL10 gl) {
        frameBuffer.clear(Color.TRANSPARENT);
        myWorld.renderScene(frameBuffer);
        myWorld.draw(frameBuffer);
        frameBuffer.display();
    }

    public void addObject(Context context,int weight,String picname) {

        Object3D newObject = null;

        try {
            createTextures(context,weight,picname);
            Object3D[] objectsArray2 = Loader.loadOBJ(context.getResources().getAssets().open(objfilename), context.getResources()
                    .getAssets().open(mtlfilename), 15f);
            newObject = Object3D.mergeAll(objectsArray2);
            newObject.setTexture(texturefilename);
            newObject.setOrigin(new SimpleVector(0, -200, 200));
            newObject.rotateZ(3.1415926f);
            newObject.rotateY(3.1415926f);
            newObject.setName(objfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newObject.strip();
        newObject.build();
        Message msg=new Message();
        msg.what=model_show.MSG_LOAD_MODEL_SUC;
        msg.obj=newObject;
        model_show.handler.sendMessage(msg);
        selectedObj=newObject;

    }

    public void applyTranslation(float incX, float incY, float incZ) {

        if (this.selectedObj != null) {
            SimpleVector objOrigin = this.selectedObj.getOrigin();
            SimpleVector currentPoition=this.selectedObj.getTransformedCenter();
            System.out.println(currentPoition);
            this.selectedObj.translate(incX, incY, incZ);

        }

    }


    public Bitmap scaleBitmap(Bitmap bitmap,int weight){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Log.d(TAG, "scaleBitmap: "+ w+"-----"+h);
        //bitmap.set
        int destW = 256;
        int destH = 256;

        Matrix matrix = new Matrix();
        matrix.postSkew(0, 0);
        Log.d(TAG, "createTextures:--------------------- "+w);
        Log.d(TAG, "createTextures:--------------------- "+destH);
        Bitmap newbm = Bitmap.createScaledBitmap(bitmap,destW, destH ,true);
        //Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, destW, destH, matrix, false);
        return newbm;
    }

    private void createTextures(Context context,int weight,String picname) {


        Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.c1);
        Log.d(TAG, "createTextures-------: "+picname);
        //Bitmap bitmap;





        if(picname.equals("c1")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c1);
        }
        else if(picname.equals("c2")) {
            Log.d(TAG, "createTextures: asdfasdfadsf");
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c2);
        }
        else if(picname.equals("c3")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c3);
        }

        else if(picname.equals("c4")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c4);
        }

        else if(picname.equals("c5")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c5);
        }

        else if(picname.equals("c6")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c6);
        }
/*
        else if(picname.equals("c7")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c7);
        }

        else if(picname.equals("c8")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c8);
        }
        else if(picname.equals("c9")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.c9);
        }*/






        Log.d(TAG, "createTextures: "+bitmap.getHeight());
        Bitmap bitmap_scale = scaleBitmap(bitmap,weight);

        //Log.d(TAG, "createTextures: "+bitmap_scale.getHeight());
        //bitmap.setHeight();
        Texture texture = new Texture(bitmap_scale);
        //texture.set
        //texture.set
        if(!TextureManager.getInstance().containsTexture(texturefilename)){
            TextureManager.getInstance().addTexture(texturefilename, texture);
        }

    }

}