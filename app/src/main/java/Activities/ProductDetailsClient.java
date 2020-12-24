package Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.ProductItem;
import Adapters.ReserveProduct;

public class ProductDetailsClient extends AppCompatActivity implements View.OnClickListener{
    private TextView name, units, desc, burrow;
    private Button reserve;
    private DatabaseReference suppRef, mainRef;
    private FirebaseAuth firebaseAuth;
    private String suppId, prodId;
    private ProductItem pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_client);
        setUIViews();
        updateDetails();
    }

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
                    burrow.setText(pi.getBurrowTime());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end listener
    }//end update

    private void reserveProduct(){
        int unitsOfProd = Integer.parseInt(pi.getUnitsInStock());
        if(unitsOfProd > 0) {
            ReserveProduct rp = new ReserveProduct(prodId, suppId, firebaseAuth.getUid());
            String id = Integer.toString(rp.getId());
            //add product to client list
            mainRef.child("Clients").child(firebaseAuth.getUid()).child("reserves").child(id).setValue(rp);
            //add product to supplier list
            mainRef.child("Suppliers").child(suppId).child("reserves").child(id).setValue(rp);
            //update the units in stock
            pi.setUnitsInStock(Integer.toString(--unitsOfProd));
            mainRef.child("Suppliers").child(suppId).child("products").child(prodId).setValue(pi);
            makeToast("reserved!");
        }
        else {
            makeToast("there is no enough units in stock!");
        }
    }//end reserve product

    private void setUIViews() {
        //set text
        name = (TextView) findViewById(R.id.pcname);
        units = (TextView) findViewById(R.id.pcunits);
        desc = (TextView) findViewById(R.id.pcdesc);
        burrow = (TextView) findViewById(R.id.pcburrowTime);
        //set button
        reserve = (Button) findViewById(R.id.pcreserve);
        reserve.setOnClickListener((View.OnClickListener) this);
        //set string
        Intent intent = getIntent();
        String sp[] = intent.getStringExtra("key").split("pKey:");
        suppId = sp[0];
        prodId = sp[1];
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        mainRef = FirebaseDatabase.getInstance().getReference();
        suppRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(suppId).child("products").child(prodId);
    }// end set view

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pcreserve) {
            reserveProduct();
        }
    }

    private void makeToast(String m){
        Toast.makeText(ProductDetailsClient.this, m, Toast.LENGTH_SHORT).show();
    }
}