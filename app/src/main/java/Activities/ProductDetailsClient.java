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
import android.widget.TextView;

import Adapters.ProductItem;

public class ProductDetailsClient extends AppCompatActivity implements View.OnClickListener{
    TextView name, units, desc, burrow;
    private DatabaseReference userRef;
    String suppId, pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setUIViews();
        updateDetails();
    }

    private void updateDetails() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ProductItem p = snapshot.getValue(ProductItem.class);
                name.setText(p.getName());
                units.setText(p.getUnitsInStock());
                desc.setText(p.getDescription());
                burrow.setText(p.getBurrowTime());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end listener
    }//end update

    private void setUIViews() {
        //set text
        name = (TextView) findViewById(R.id.pcname);
        units = (TextView) findViewById(R.id.pcunits);
        desc = (TextView) findViewById(R.id.pcdesc);
        burrow = (TextView) findViewById(R.id.pcburrowTime);
        //set string
        Intent intent = getIntent();
        String sp[] = intent.getStringExtra("key").split("pKey:");
        suppId = sp[0];
        pId = sp[1];
        //set firebase
        userRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(suppId).child("products").child(pId);
    }

    @Override
    public void onClick(View v) {
    //reserve!!
    }
}