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

public class GmachStockClient extends AppCompatActivity{
    private ListView listView;
    private DatabaseReference myRef;
    private String supp_key;
    private ArrayList<String> prodItemName;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmach_stock_client);
        setUIViews();
        setAdapter();
        showProducts();
    }

    private void setUIViews(){
        //set key
        Intent intent = getIntent();
        supp_key = intent.getStringExtra("key");
        //set text
        listView = (ListView) findViewById(R.id.listViewItems);
        //set database
        myRef = FirebaseDatabase.getInstance().getReference()
                .child("Suppliers").child(supp_key);
    }

    private void setAdapter() {
        prodItemName = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prodItemName);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(GmachStockClient.this, ProductDetailsClient.class);
                String key = supp_key+"pKey:"+prodItemName.get(position);
                i.putExtra("key", key);
                startActivity(i);
            }
        });
    }//end set adapter
    private void showProducts() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("products")) {
                    prodItemName.add("This supplier doesn't have products yet");
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
            public void onCancelled(@NonNull DatabaseError error) { }
        }); //end listener
    }
}