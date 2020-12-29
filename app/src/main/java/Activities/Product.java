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
import java.util.HashMap;

public class Product extends AppCompatActivity implements View.OnClickListener{
    private EditText prod_name, prod_description, prod_units, prod_burrowTime;
    private ImageView img;
    private String name, description, units, burrowTime;
    private Button add, upload;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
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
        userRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference("Images");
    }
    private void addTheProduct(){
        name = prod_name.getText().toString();
        description = prod_description.getText().toString();
        units = prod_units.getText().toString();
        burrowTime = prod_burrowTime.getText().toString();
        if (validate()){
            ProductItem productItem = new ProductItem(name, description, units,burrowTime);
            userRef.child("Suppliers").child(firebaseAuth.getUid()).child("products").setValue(productItem);
        }
    }

    boolean result;

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
                String path = firebaseAuth.getUid()+"/"+(ProductItem.counter+1);
                uploadTask = storageRef.child(path).putBytes(byteData);
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }// end on act result


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.AddTheProduct){
            addTheProduct();
            startActivity(new Intent(Product.this,GmachStockSupplier.class));
            finish();
        }
        else if(v.getId() == R.id.productUpImg){
            if (uploadTask != null && uploadTask.isInProgress()){
                makeToast("upload in progress");
            } else {
                chooseUploadImg();
            }
        }
    }
    private String getExtension (Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void makeToast(String m){
        Toast.makeText(Product.this, m, Toast.LENGTH_SHORT).show();
    }
    private boolean validate(){
        result = true;
//        if (!units.matches("^[0-9]+$")) result = false;
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                DataSnapshot prodList = snapshot.child("Suppliers").child(firebaseAuth.getUid()).child("products");
//                for(DataSnapshot prod: prodList.getChildren()){
//                    if(prod.getValue(ProductItem.class).getName()== name){
//                        result = false;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        }); //end listener
        return result;
    }
}