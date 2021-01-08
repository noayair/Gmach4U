package Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import Adapters.ProductItem;
import Adapters.ReserveProduct;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProductDetailsClient extends AppCompatActivity implements View.OnClickListener{
    private TextView name, units, desc, burrow;
    private Button reserve;
    private ImageView img;
    private DatabaseReference suppRef, mainRef;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageRef;
    private String suppId, prodId;
    private ProductItem pi;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_client);
        setUIViews();
        updateDetails();
    }

    private void setUIViews() {
        //set text
        name = (TextView) findViewById(R.id.pcname);
        units = (TextView) findViewById(R.id.pcunits);
        desc = (TextView) findViewById(R.id.pcdesc);
        burrow = (TextView) findViewById(R.id.pcburrowTime);
        //set img
        img = (ImageView) findViewById(R.id.pcImg);
        //set button
        reserve = (Button) findViewById(R.id.pcreserve);
        reserve.setOnClickListener((View.OnClickListener) this);
        //set id
        Intent intent = getIntent();
        String sp[] = intent.getStringExtra("key").split("pKey:");
        suppId = sp[0];
        prodId = sp[1];
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        mainRef = FirebaseDatabase.getInstance().getReference();
        suppRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(suppId).child("products").child(prodId);
        storageRef = FirebaseStorage.getInstance().getReference();

    }// end set view

    private void updateDetails() {
        suppRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    pi = snapshot.getValue(ProductItem.class);
                    name.setText(pi.getName());
                    units.setText(pi.getUnitsInStock());
                    desc.setText(pi.getDescription());
                    burrow.setText(pi.getBurrowTime());
                }
                //set img
                String path = "Images/" + suppId + "/" + prodId;
                final long ONE_MEGABYTE = (long) Math.pow(1024, 10);
                storageRef.child(path).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) { }
                });
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });//end listener
    }//end update

    private void reserveProduct(){
        int unitsOfProd = Integer.parseInt(pi.getUnitsInStock());
        if(unitsOfProd > 0) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String resDate = dateFormat.format(cal.getTime());
            int burrowDays = Integer.parseInt(burrow.getText().toString());
            cal.add(Calendar.DAY_OF_YEAR,burrowDays);
            String returnDate = dateFormat.format(new Date(cal.getTimeInMillis()));
            ReserveProduct rp = new ReserveProduct(prodId, suppId, firebaseAuth.getUid(), resDate,returnDate);
            String id = rp.getId();
            //add product to client list
            mainRef.child("Clients").child(firebaseAuth.getUid()).child("reserves").child(id).setValue(rp);
            //add product to supplier list
            mainRef.child("Suppliers").child(suppId).child("reserves").child(id).setValue(rp);
            //update the units in stock
            pi.setUnitsInStock(Integer.toString(--unitsOfProd));
            mainRef.child("Suppliers").child(suppId).child("products").child(prodId).setValue(pi);

            Dialog dialog=new Dialog();
            dialog.show(getSupportFragmentManager(),"Dialog");

        }
        else {
            makeToast("there is no enough units in stock!");
        }
    }//end reserve product
    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.pcreserve) {
            reserveProduct();
//        }
    }

    private void makeToast(String m){
        Toast.makeText(ProductDetailsClient.this, m, Toast.LENGTH_SHORT).show();
    }
}