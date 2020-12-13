package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.ProductItem;
import Adapters.Supplier;

public class SearchResults extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> keyList,showList ;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setUIViews();
        setAdapter();
        showResults();
    }
    private void setAdapter() {
        showList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchResults.this, GmachDetails.class);
                i.putExtra("key", keyList.get(position));
                startActivity(i);
            }
        });
    }//end set adapter

    private void showResults() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //for on the suppliers
                for (String key: keyList) {
                    for(DataSnapshot d: snapshot.getChildren()){
                        Supplier s = d.child("details").getValue(Supplier.class);
                        if (key.equals(s.getId())){
                            showList.add("Name: "+s.getName()+" Address: "+s.getAddress());
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
                if (showList.isEmpty()) {
                    showList.add("No results");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); //end listener
    } //end showResults

    private void setUIViews() {
        //set text
        listView = (ListView) findViewById(R.id.listView);
        //set database
        firebaseAuth=FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Suppliers");
        //set list results
        Intent intent = getIntent();
        keyList = intent.getStringArrayListExtra("list");
    }

    //**************************Logout & Search & personal profile buttons***************************************
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this , loginActivity.class));
    }

    public void openPrivateZone(){
        Intent intent = new Intent(this, PrivateZone.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setQueryHint("Type here to search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_logoutMenu){
            Logout();
        }
        if(item.getItemId() == R.id.personal_profile){
            openPrivateZone();
        }
        return super.onOptionsItemSelected(item);
    }
}