package com.example.dell.agrimart1.Frgments;




import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.agrimart1.Farmer_Activity;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.Models.Upload;
import com.example.dell.agrimart1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upload_Fragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private EditText mEditTextPrice;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
private SessionManager sessionManager;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private FirebaseFirestore mDatabaseRef;

    private StorageTask mUploadTask;
private View view;

    private static final String TAG = "UploadActivity";

    public Upload_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload_, container, false);

        sessionManager= new SessionManager(getActivity());

        initialize();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        return view;
    }

    private void uploadFile() {

        if (mImageUri != null) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    final String sdownload_url = String.valueOf(downloadUrl);
                    Log.d(TAG,"Price is :"+mEditTextPrice.getText().toString());

                    DocumentReference documentRef=mDatabaseRef.collection("uploads").document();
                    String uploadId = documentRef.getId();


                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                            sdownload_url,new Date().getTime() / 1000l, mEditTextPrice.getText().toString(),
                            sessionManager.getUserDetails().get("contact"),uploadId) ;

                    documentRef.set(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(getActivity(), Farmer_Activity.class));

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }

    }


    private String getFileExtension(Uri uri) {

        ContentResolver cR =getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getActivity()).load(mImageUri).into(mImageView);
        }
    }

    private void initialize(){

        mButtonChooseImage = view.findViewById(R.id.button_choose_image);
        mButtonUpload = view.findViewById(R.id.button_upload);
        mEditTextFileName =view. findViewById(R.id.edit_text_file_name);
        mEditTextPrice =view. findViewById(R.id.edit_text_file_price);
        mImageView =view. findViewById(R.id.image_view);
        mProgressBar =view. findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseFirestore.getInstance();
    }


}
