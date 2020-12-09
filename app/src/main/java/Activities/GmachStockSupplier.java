package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Adapters.ProductItem;

public class GmachStockSupplier extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
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
        //set text
        listView = (ListView) findViewById(R.id.ProductsList);
        //set button
        addProd = (Button) findViewById(R.id.AddProduct);
        addProd.setOnClickListener((View.OnClickListener) this);
        //set database
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference()
                .child("Suppliers").child(firebaseAuth.getUid());
        //set adapter
//        String[] sports = {"soccer","football"};
//        ArrayAdapter<String> SA = new ArrayAdapter<String>(
//                GmachStockSupplier.this,
//                android.R.layout.simple_list_item_1,
//                sports);
//        setContentView(listView);
//        listView.setAdapter(SA);

    }
    public void onClick(View v) {
        if(v.getId() == R.id.AddProduct) {
            startActivity(new Intent(GmachStockSupplier.this,Product.class));
        }
    }//end onClick

}