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

public class MainActivity extends AppCompatActivity {

    private Spinner categorySelectSpinner;
    private Spinner locationSelectSpinner;
    private Button search_by_location_Button;
    FirebaseAuth firebaseAuth;
    ListView listView;
    ArrayAdapter<String> adapter1;

    DatabaseReference myRef;
    private AutoCompleteTextView txtSearch;
    private ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

//      ***************************SEARCH BY CATEGORY*************************************
        categorySelectSpinner = (Spinner) findViewById(R.id.mainActivity_category_spinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        categoriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySelectSpinner.setAdapter(categoriesAdapter);






        //************************SEARCH BY LOCATION**************************************
        locationSelectSpinner = (Spinner) findViewById(R.id.mainActivity_location_spinner);
        search_by_location_Button = (Button)findViewById(R.id.search_button);
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.locationsArray));
        locationsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSelectSpinner.setAdapter(locationsAdapter);
        search_by_location_Button.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){
                String selectedLocation = getResources().getStringArray(R.array.locationsArray)[locationSelectSpinner.getSelectedItemPosition()];
                openSearchButton();
            }
        });
    }



    //from youtube

//    private void populateSearch() {
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    ArrayList<String> names = new ArrayList<>();
//                    for(DataSnapshot ds : snapshot.getChildren()){
//                        String n = ds.child("name").getValue(String.class);
//                        names.add(n);
//                    }
//                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, names);
//                    txtSearch.setAdapter(adapter);
//                    txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            String selection = parent.getItemAtPosition(position).toString();
//                            getUsers(position);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        myRef.addListenerForSingleValueEvent(eventListener);
//    }

//    private void getUsers(int selection) {
//        Query query = myRef.orderByChild("name").equalTo(selection);
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    ArrayList<UserInfo> userInfos = new ArrayList<>();
//                    for(DataSnapshot ds : snapshot.getChildren()){
//                        UserInfo userInfo = new UserInfo(ds.child("name").getValue(String.class)
//                        , ds.child("category").getValue(String.class)
//                        , ds.child("location").getValue(String.class));
//                        userInfos.add(userInfo);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        query.addListenerForSingleValueEvent(eventListener);
//    }
//
//    class UserInfo{
//        public String name;
//        public String category;
//        public String location;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getCategory() {
//            return category;
//        }
//
//        public void setCategory(String category) {
//            this.category = category;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//
//        public UserInfo(String name, String category, String location) {
//            this.name = name;
//            this.category = category;
//            this.location = location;
//        }
//    }
//
//
//    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
//
//        private ArrayList<UserInfo> localDataSet;
//
//        /**
//         * Provide a reference to the type of views that you are using
//         * (custom ViewHolder).
//         */
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            private final TextView textView;
//
//            public ViewHolder(View view) {
//                super(view);
//                // Define click listener for the ViewHolder's View
//
//                textView = (TextView) view.findViewById(R.id.textView);
//            }
//
//            public TextView getTextView() {
//                return textView;
//            }
//        }
//
//        /**
//         * Initialize the dataset of the Adapter.
//         *
//         * @param dataSet String[] containing the data to populate views to be used
//         * by RecyclerView.
//         */
//        public CustomAdapter(ArrayList<UserInfo> dataSet) {
//            this.localDataSet = dataSet;
//        }
//
//        // Create new views (invoked by the layout manager)
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getParent().getBaseContext());
//            View view = LayoutInflater.inflate(R.layout.row_style, getParent(), false);
//        }
//
//        // Replace the contents of a view (invoked by the layout manager)
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//
//            // Get element from your dataset at this position and replace the
//            // contents of the view with that element
//            viewHolder.getTextView().setText(localDataSet[position]);
//        }
//
//        // Return the size of your dataset (invoked by the layout manager)
//        @Override
//        public int getItemCount() {
//            return localDataSet.length;
//        }
//    }



    public void openSearchButton(){
        Intent intent = new Intent(this, SearchResults.class);
        startActivity(intent);
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

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Type here to search");
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        adapter1.getFilter().filter(newText);
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