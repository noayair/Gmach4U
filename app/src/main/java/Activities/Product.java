package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Adapters.Client;
import Adapters.ProductItem;
import Adapters.Supplier;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Product extends AppCompatActivity implements View.OnClickListener{
    private EditText prod_name, prod_description, prod_units, prod_burrowTime;
    private String name, description, units, burrowTime;
    private Button add;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
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
        add.setOnClickListener((View.OnClickListener) this);
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();
    }
    private void addTheProduct(){
        name = prod_name.getText().toString();
        description = prod_description.getText().toString();
        units = prod_units.getText().toString();
        burrowTime = prod_burrowTime.getText().toString();
        if (validate()){
            ProductItem productItem = new ProductItem(name, description, units,burrowTime);
            String id = Integer.toString(productItem.getId());
            userRef.child("Suppliers").child(firebaseAuth.getUid()).child("products").child(id).setValue(productItem);
        }
    }

    boolean result;
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
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.AddTheProduct){
            addTheProduct();
            startActivity(new Intent(Product.this,GmachStockSupplier.class));
            finish();
        }
    }
}