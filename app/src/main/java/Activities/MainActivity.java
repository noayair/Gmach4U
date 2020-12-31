package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapters.Client;
import Adapters.ProductItem;
import Adapters.Supplier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private TextView displayName;
    private Spinner categorySelectSpinner, locationSelectSpinner;
    private Button search_Button;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter<String> categoriesAdapter,locationsAdapter;
    private DatabaseReference suppRef,clientRef;
    private EditText searchByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        //set text
        searchByName = (EditText) findViewById(R.id.editTextTextPersonName);
        displayName = (TextView) findViewById(R.id.cName);
        //set button
        search_Button = (Button)findViewById(R.id.search_button);
        search_Button.setOnClickListener((View.OnClickListener)this);
        //set firebase
        firebaseAuth=FirebaseAuth.getInstance();
        suppRef = FirebaseDatabase.getInstance().getReference("Suppliers");
        //set sppiner
        categorySelectSpinner = (Spinner) findViewById(R.id.mainActivity_category_spinner);
        categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        categoriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySelectSpinner.setAdapter(categoriesAdapter);

        locationSelectSpinner = (Spinner) findViewById(R.id.mainActivity_location_spinner);
        locationsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.locationsArray));
        locationsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSelectSpinner.setAdapter(locationsAdapter);
        //set hello name
        clientRef = FirebaseDatabase.getInstance().getReference("Clients").child(firebaseAuth.getUid()).child("details");
        clientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name =snapshot.getValue(Client.class).getName();
                displayName.setText("Hello "+name+"!");
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });//end listener
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_button){
            //start the search
            gmachSearch();
        }
    }

    private void gmachSearch() {
        String category, location, name;
        category = categorySelectSpinner.getSelectedItem().toString();
        location = locationSelectSpinner.getSelectedItem().toString();
        name = searchByName.getText().toString();
        ArrayList<String> list_supp = new ArrayList<>();
        HashMap<String, String> searchHash = new HashMap<>();
        if(!category.equals("Search by category")){
            searchHash.put("category", category);
        }
        if(!location.equals("Search by location")){
            searchHash.put("location", location);
        }
        if(!name.equals("Search by name") && !name.isEmpty()){
            searchHash.put("name", name);
        }
        if(!searchHash.isEmpty()){
            suppRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //for on the suppliers
                    for(DataSnapshot sup: snapshot.getChildren()) {
                        Supplier supplier = sup.child("details").getValue(Supplier.class);
                        boolean isGood = true;
                        for(String s: searchHash.keySet()){
                            String value = searchHash.get(s);
                            if(s.equals("category") && !supplier.getCategory().equals(value)){ //if the category is not equals to the category of the supplier
                                isGood = false;
                            }
                            if(s.equals("location") && !supplier.getLocation().equals(value)){ //if the location is not equals to the location of the supplier
                                isGood = false;
                            }
                            if(s.equals("name") && !supplier.getName().equals(value)){ //if the name is not equals to the name of the supplier
                                isGood = false;
                            }
                        }
                        if(isGood){
                            list_supp.add(supplier.getId());
                        }
                    }
                    Intent i = new Intent(MainActivity.this, SearchResults.class);
                    i.putExtra("list", list_supp);
                    startActivity(i);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }); //end listener
        }else{
            //error!!!
            Toast.makeText(MainActivity.this, "Please select something!!", Toast.LENGTH_SHORT).show();
        }
    }

    //*********menu bar**************
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this,loginActivity.class));
    }

    public void openPrivateZone(){
        Intent intent = new Intent(this, PrivateZone.class);
        startActivity(intent);
    }

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
        if(item.getItemId() == R.id.Home){
            openMain();
        }
        return super.onOptionsItemSelected(item);
    }
}