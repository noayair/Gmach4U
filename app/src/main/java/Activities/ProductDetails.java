package Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.gmach4u.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.ProductAdapter;
import Adapters.ProductItem;

public class ProductDetails extends AppCompatActivity {
    ImageView img;
    TextView name, units, desc;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
    String pId;

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
                //go on the products
                for(DataSnapshot d: snapshot.getChildren()){
                    ProductItem p = d.getValue(ProductItem.class);
                    if(Integer.toString(p.getId()) == pId){
                        name.setText(p.getName());
                        units.setText(p.getUnitsInStock());
                        desc.setText(p.getDescription());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end listener
    }//end update

    private void setUIViews() {
        //set img
        img = (ImageView) findViewById(R.id.Image);
        //set text
        name = (TextView) findViewById(R.id.pname);
        units = (TextView) findViewById(R.id.punits);
        desc = (TextView) findViewById(R.id.pdesc);
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(firebaseAuth.getUid()).child("products");
        //set string
        Intent intent = getIntent();
        pId = intent.getStringExtra("key");
    }
}