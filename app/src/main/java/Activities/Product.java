package Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Adapters.Client;
import Adapters.ProductItem;
import Adapters.Supplier;

import com.example.gmach4u.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Product extends AppCompatActivity implements View.OnClickListener{
    private EditText prod_name, prod_description, prod_units, prod_burrowTime;
    private ImageView img;
    private String name, description, units, burrowTime;
    private Button add, upload;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference suppRef;
    private Uri imguri;
    private StorageTask uploadTask;
    private StorageReference storageRef;
    private static final int GET_FROM_GALLERY = 3;
    byte[] byteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setUIViews();
    }
    private void setUIViews() {
        //set edit text
        prod_name = (EditText) findViewById(R.id.ProductName);
        prod_description = (EditText) findViewById(R.id.ProductDescription);
        prod_units = (EditText) findViewById(R.id.UnitsInStock);
        prod_burrowTime = (EditText) findViewById(R.id.DaysToBurrow);
        //set button
        add = (Button) findViewById(R.id.AddTheProduct);
        upload = (Button) findViewById(R.id.productUpImg);
        add.setOnClickListener((View.OnClickListener) this);
        upload.setOnClickListener((View.OnClickListener) this);
        //set img
        img = (ImageView) findViewById(R.id.ProductImage);
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        suppRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(firebaseAuth.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("Images");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.productUpImg){
            if (uploadTask != null && uploadTask.isInProgress()) makeToast("upload in progress");
            else chooseUploadImg();
        }
        if(v.getId() == R.id.AddTheProduct){
            addTheProduct();
        }
    }

    private void chooseUploadImg() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            imguri = data.getData();
            img.setImageURI(imguri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imguri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteData = baos.toByteArray();
            }
            catch (FileNotFoundException e) { e.printStackTrace();}
            catch (IOException e) { e.printStackTrace();}
        }
    }// end on act result

    private void addTheProduct(){
        //set string
        name = prod_name.getText().toString();
        description = prod_description.getText().toString();
        units = prod_units.getText().toString();
        burrowTime = prod_burrowTime.getText().toString();
        if(validate()) {
            // add obj to DB
            ProductItem productItem = new ProductItem(name, description, units, burrowTime);
            DatabaseReference prodRef = suppRef.child("products").child(name);
            prodRef.setValue(productItem);
            // add img to DB
            if(byteData != null){
                String path = firebaseAuth.getUid() + "/" + name;
                uploadTask = storageRef.child(path).putBytes(byteData);
            }
            //back to stock
            startActivity(new Intent(Product.this,GmachStockSupplier.class));
            finish();
        }
    }

    private void makeToast(String m){
        Toast.makeText(Product.this, m, Toast.LENGTH_SHORT).show();
    }

    private boolean validate(){
        boolean isValid = true;
        suppRef.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // for on the products
                for(DataSnapshot prod: snapshot.getChildren()){
                    if(prod.getValue(ProductItem.class).getName().equals(name)){
                        makeToast("this name is already exist! the old product will delete!");
                    }
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        }); //end listener

        if (units.equals("Units in stock")) {
            makeToast("please enter units in stock");
            isValid = false;
        } else if (burrowTime.equals("Burrow time(in days)")) {
            makeToast("please enter burrow time");
            isValid = false;
        }
        return isValid;
    }
}