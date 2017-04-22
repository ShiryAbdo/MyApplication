package com.example.shady.myapplication.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shady.myapplication.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by EL.GAMAL on 4/1/2017.
 */

public class PhotoFragment extends Fragment {

    private Uri fileUri;
    Button btnCapture;
    private ImageView imgPreview;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_photo, container, false);


        imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
        btnCapture = (Button ) view.findViewById(R.id.btn_photo);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(getActivity());

        if(savedInstanceState != null){
            fileUri = savedInstanceState.getParcelable("file_uri");
        }

        return view;
    }



    public void process(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 0);
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Medicine");

        if (requestCode == 0) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    previewCapturedImage();
                    Toast.makeText(getActivity(), "The file was saved at " + imgFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

//                   mProgress.setMessage("Uploading Image ...");
//                   mProgress.show();
//
//                   Uri uri = data.getData();
//                    StorageReference path = mStorageRef.child("Medicine").child(uri.getLastPathSegment());
//                    path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            mProgress.dismiss();
//                            Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
//                        }
//                    });



                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_LONG).show();
                    break;

                default:
                    Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_LONG).show();
                    break;
            }

        }


    }


    private void previewCapturedImage() {
        try {

            imgPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            imgPreview.setImageBitmap(bitmap);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Medicine");
        if (!imgFile.exists()) {
            if (!imgFile.mkdirs()) {
                Log.d("Medicines", "Oops! Failed create " + "Medicine" + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(imgFile.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

}
