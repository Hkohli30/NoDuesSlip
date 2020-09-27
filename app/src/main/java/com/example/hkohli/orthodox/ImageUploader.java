package com.example.hkohli.orthodox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUploader extends AppCompatActivity implements View.OnClickListener,AsyncQueryResponse {

    FloatingActionButton camera_btn,uploader_btn,gallery_btn;
    private static final String PAGE_URL = "http://www.coderzguild.16mb.com/QueryExecuter.php";
    static final int REQUEST_CAMERA = 102;
    static final int GALLERY_PICK = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uploader);

        // Fetching id's
        camera_btn = (FloatingActionButton)findViewById(R.id.image_uploader_camera);
        uploader_btn = (FloatingActionButton)findViewById(R.id.image_uploader_uploader);
        gallery_btn =(FloatingActionButton)findViewById(R.id.image_uploader_gallery);

        camera_btn.setOnClickListener(this);
        gallery_btn.setOnClickListener(this);
        uploader_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == camera_btn)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_CAMERA);
        }

        if(v == gallery_btn)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent.createChooser(intent, "Select Picture"), GALLERY_PICK);
        }

        if(v == uploader_btn)
        {
//            String sql = "UPDATE";
//            QueryManager queryManager = new QueryManager(this,PAGE_URL);
//            queryManager.DELEGATE_RESPONSE = this;
//            queryManager.execute("sql",sql);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_CAMERA)
            {
                Bundle extras = data.getExtras();
                Bitmap thumbnail = (Bitmap)extras.get("data");
                ((ImageView)findViewById(R.id.image_uploader_imageview)).setImageBitmap(thumbnail);
            }

            else if(requestCode == GALLERY_PICK && data !=null
                    && data.getData() != null)
            {
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    ((ImageView)findViewById(R.id.image_uploader_imageview)).setImageBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

            else
            {
                Toast.makeText(ImageUploader.this, "Unable to get Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void processResponse(String output) {
        if(output != null)
        {
            Toast.makeText(ImageUploader.this, ""+output.trim(), Toast.LENGTH_SHORT).show();
        }
    }
}
