package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.ProductItem;

public class GmachStockSupplier extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private Button addProd;
    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> prodItemName;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmach_stock_supplier);
        setUIViews();
        setAdapter();
        showProducts();
    }
    private void setAdapter() {
        prodItemName = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prodItemName);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position); //name of product
                Intent i = new Intent(GmachStockSupplier.this, ProductDetails.class);
                i.putExtra("key", clickedItem);
                startActivity(i);
            }
        });
    }//end set adapter
    private void showProducts() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("products")) {
                    prodItemName.add("You don't have products yet");
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    for(DataSnapshot product: snapshot.child("products").getChildren()){
                        ProductItem p = product.getValue(ProductItem.class);
                        prodItemName.add(p.getName());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); //end listener
    }
    private void setUIViews(){
        //set text
        listView = (ListView) findViewById(R.id.ProductsList);
        //set button
        addProd = (Button) findViewById(R.id.AddProduct);
        addProd.setOnClickListener((View.OnClickListener) this);
        //set database
        firebaseAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Suppliers").child(firebaseAuth.getUid());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.AddProduct){
            //go to Product
            startActivity(new Intent(GmachStockSupplier.this,Product.class));
        }
    }
}