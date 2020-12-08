package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GmachStockSupplier extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_POST_KEY = "post_key";
    private Button addProd;
    private DatabaseReference userRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmach_stock_supplier);
        setUIViews();
        showProducts();
    }

    private void showProducts() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("products")) {
                    Toast.makeText(GmachStockSupplier.this, "You dont have a products yet!", Toast.LENGTH_SHORT).show();
                } else {
                    for(DataSnapshot product: snapshot.child("products").getChildren()){
                        //show products
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUIViews(){
        //set button
        addProd = (Button) findViewById(R.id.AddProduct);
        addProd.setOnClickListener((View.OnClickListener) this);
        //set database
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference()
                .child("Suppliers").child(firebaseAuth.getUid());

    }
    public void onClick(View v) {
        if(v.getId() == R.id.AddProduct) {
            startActivity(new Intent(GmachStockSupplier.this,Product.class));
        }
    }//end onClick

}