package Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.ProductItem;

public class ProductDetails extends AppCompatActivity {
    private TextView name, units, desc, burrow;
    private ImageView img;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
    StorageReference storageRef;
    private String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setUIViews();
        updateDetails();
    }

    private void setUIViews() {
        //set text
        name = (TextView) findViewById(R.id.pname);
        units = (TextView) findViewById(R.id.punits);
        desc = (TextView) findViewById(R.id.pdesc);
        burrow =  (TextView) findViewById(R.id.pburrowTime);
        //set img
        img = (ImageView) findViewById(R.id.pImg);
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(firebaseAuth.getUid()).child("products");
        storageRef = FirebaseStorage.getInstance().getReference();
        //set string
        Intent intent = getIntent();
        pId = intent.getStringExtra("key");
    }

    private void updateDetails() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //go on the products
                for(DataSnapshot d: snapshot.getChildren()){
                    ProductItem p = d.getValue(ProductItem.class);
                    if(p.getName().equals(pId)){
                        //set text
                        name.setText(p.getName());
                        units.setText(p.getUnitsInStock());
                        desc.setText(p.getDescription());
                        burrow.setText(p.getBurrowTime());
                        //set img
                        String path = "Images/"+firebaseAuth.getUid()+"/"+pId;
                        storageRef.child("Images/vZZQMEyyGHYRx3ORzIv1XtRL4jl2/ran");
                        final long ONE_MEGABYTE = 1024 * 1024;
                        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override public void onSuccess(byte[] bytes) {
                                Toast.makeText(ProductDetails.this, "yes", Toast.LENGTH_SHORT).show();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
                                img.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override public void onFailure(@NonNull Exception exception) { Toast.makeText(ProductDetails.this, "no", Toast.LENGTH_SHORT).show();}
                        });
                    } }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end listener
    }//end update

}