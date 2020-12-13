package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import Adapters.ProductItem;
import Adapters.Supplier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner categorySelectSpinner;
    private Spinner locationSelectSpinner;
    private Button search_Button;
    FirebaseAuth firebaseAuth;
    ArrayAdapter<String> categoriesAdapter,locationsAdapter;

    DatabaseReference myRef;
    private AutoCompleteTextView txtSearch;
    private ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

    }

    private void setViews() {
        //set button
        search_Button = (Button)findViewById(R.id.search_button);
        search_Button.setOnClickListener((View.OnClickListener)this);
        //set firebase
        firebaseAuth=FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Suppliers");
        //set sppiner
        categorySelectSpinner = (Spinner) findViewById(R.id.mainActivity_category_spinner);
        categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        categoriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySelectSpinner.setAdapter(categoriesAdapter);

        locationSelectSpinner = (Spinner) findViewById(R.id.mainActivity_location_spinner);
        locationsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.locationsArray));
        locationsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSelectSpinner.setAdapter(locationsAdapter);
    }

    private void gmachSearch() {
        String category, location;
        category = categorySelectSpinner.getSelectedItem().toString();
        location = locationSelectSpinner.getSelectedItem().toString();
        ArrayList<String> list_supp = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //for on the suppliers
                    if (!category.equals("Search by category")){
                        if (!location.equals("Search by location")){
                            //search both
                            for(DataSnapshot sup: snapshot.getChildren()) {
                                Supplier s = sup.child("details").getValue(Supplier.class);
                                if (s.getCategory().equals(category) && s.getLocation().equals(location)){
                                    list_supp.add(s.getId());
                                }
                            }
                        }
                        else {
                            //search category
                            for(DataSnapshot sup: snapshot.getChildren()) {
                                Supplier s = sup.child("details").getValue(Supplier.class);
                                if (s.getCategory().equals(category)){
                                    list_supp.add(s.getId());
                                }
                            }
                        }
                        //move to results
                        Intent i = new Intent(MainActivity.this, SearchResults.class);
                        i.putExtra("list", list_supp);
                        startActivity(i);
                    }
                    else if (!location.equals("Search by location")){
                        //search locaction
                        for(DataSnapshot sup: snapshot.getChildren()) {
                            Supplier s = sup.child("details").getValue(Supplier.class);
                            if (s.getLocation().equals(location)){
                                list_supp.add(s.getId());
                            }
                        }
                        //move to results
                        Intent i = new Intent(MainActivity.this, SearchResults.class);
                        i.putExtra("list", list_supp);
                        startActivity(i);
                    }
                    else {
                        //error!!!
                        Toast.makeText(MainActivity.this, "Please select something!!", Toast.LENGTH_SHORT).show();
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); //end listener
    }

    //**************************Logout & Search & personal profile buttons***************************************
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this,loginActivity.class));
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_button){
            //start the search
            gmachSearch();
        }
    }
}