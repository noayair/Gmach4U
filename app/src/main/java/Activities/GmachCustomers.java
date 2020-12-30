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
import android.widget.SimpleAdapter;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Adapters.Client;
import Adapters.ProductItem;
import Adapters.ReserveProduct;

public class GmachCustomers extends AppCompatActivity {
    private ListView listView;
    private HashMap<String, String> reserveDetails;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter adapter;
    private Iterator it;
    private DatabaseReference prodRef, reserveRef, clientRef;
    private FirebaseAuth fireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmach_customers);
        setUIViews();
        setList();
    }

    private void setUIViews(){
        //set text
        listView = (ListView) findViewById(R.id.reservelist);
        //set list
        reserveDetails = new HashMap<>();
        listItems = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItems, R.layout.list_2_items,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});
        //set database ref
        fireBaseAuth = FirebaseAuth.getInstance();
        reserveRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(fireBaseAuth.getUid()).child("reserves");
        prodRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(fireBaseAuth.getUid()).child("products");
        clientRef = FirebaseDatabase.getInstance().getReference("Clients");
        //
        it = reserveDetails.entrySet().iterator();
        listView.setAdapter(adapter);
    }

    private void setList() {
        reserveRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot reserve: snapshot.getChildren()){
                    ReserveProduct res = reserve.getValue(ReserveProduct.class);
                    //get prod name
                    prodRef.child(res.getProductId()).addValueEventListener(new ValueEventListener() {
                        @Override public void onDataChange(DataSnapshot snapshot) {
                           String pName = snapshot.getValue(ProductItem.class).getName();
                            clientRef.child(res.getClientId()).child("details").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override public void onDataChange(DataSnapshot snapshot) {
                                    String cName = snapshot.getValue(Client.class).getName();
                                    reserveDetails.put("product name: "+pName,"client name: "+ cName);
                                    //
                                    HashMap<String, String> resultsMap = new HashMap<>();
                                    resultsMap.put("First Line", "product name: "+pName);
                                    resultsMap.put("Second Line", "client name: "+ cName);
                                    listItems.add(resultsMap);
                                    adapter.notifyDataSetChanged();
                                }
                                @Override public void onCancelled(@NonNull DatabaseError error) { }
                            }); //end listener client ref
                        }
                        @Override public void onCancelled(@NonNull DatabaseError error) { }
                    }); //end listener prod ref
                    //get client name
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        }); //end listener reserve ref
    }

}