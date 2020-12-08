package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private Spinner categorySelectSpinner;
    private Spinner locationSelectSpinner;
    private Spinner nameSelectSpinner;
    private Button search_by_category_Button;
    private Button search_by_location_Button;
    private Button search_by_name_Button;
    private Button Logout;
    FirebaseAuth firebaseAuth;
    ListView listView;
    String[] nameList = {};
    ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listView = (ListView)findViewById(R.id.myListView);
//
//        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
//        listView.setAdapter(adapter1);


//        Logout = (Button)findViewById(R.id.mainActivity_btnLogout);
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logout();
//            }
//        });
        firebaseAuth=FirebaseAuth.getInstance();

//      ***************************SEARCH BY CATEGORY*************************************
        categorySelectSpinner = (Spinner) findViewById(R.id.mainActivity_category_spinner);
//        textBox1 = (TextView)findViewById(R.id.output_box);
        search_by_category_Button = (Button)findViewById(R.id.search_by_category_button);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        categoriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySelectSpinner.setAdapter(categoriesAdapter);
        search_by_category_Button.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){
                String selectedGmach = getResources().getStringArray(R.array.categoriesArray)[categorySelectSpinner.getSelectedItemPosition()];
//                textBox1.setText("Selected category is: " + selectedGmach);
                openCategorySearch();
            }
        });






        //************************SEARCH BY LOCATION**************************************
        locationSelectSpinner = (Spinner) findViewById(R.id.mainActivity_location_spinner);
//        textBox2 = (TextView)findViewById(R.id.output_box2);
        search_by_location_Button = (Button)findViewById(R.id.search_by_location_button);
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.locationsArray));
        locationsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSelectSpinner.setAdapter(locationsAdapter);
        search_by_location_Button.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){
                String selectedLocation = getResources().getStringArray(R.array.locationsArray)[locationSelectSpinner.getSelectedItemPosition()];
//                textBox1.setText("Selected category is: " + selectedLocation);
                openLocationSearch();
            }
        });


        //************************SEARCH BY NAME**************************************
        nameSelectSpinner = (Spinner) findViewById(R.id.mainActivity_name_spinner);
//        textBox1 = (TextView)findViewById(R.id.output_box);
        search_by_name_Button = (Button)findViewById(R.id.search_by_name_button);
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.namesArray));
        namesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        nameSelectSpinner.setAdapter(namesAdapter);
        search_by_name_Button.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){
                String selectedName = getResources().getStringArray(R.array.namesArray)[nameSelectSpinner.getSelectedItemPosition()];
//                textBox1.setText("Selected category is: " + selectedGmach);
                openNameSearch();
            }
        });



    }

    public void openCategorySearch(){
        Intent intent = new Intent(this, SearchResults.class);
        startActivity(intent);
    }

    public void openLocationSearch(){
        Intent intent = new Intent(this, SearchResults.class);
        startActivity(intent);
    }

    public void openNameSearch(){
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

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter1.getFilter().filter(newText);
//                return true;
//            }
//        });

//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                Toast.makeText(MainActivity.this, "Search is expanded", Toast.LENGTH_SHORT);
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                Toast.makeText(MainActivity.this, "Search is collapse", Toast.LENGTH_SHORT);
//                return false;
//            }
//        };
//        menu.findItem(R.id.main_searchMenu).setOnActionExpandListener(onActionExpandListener);
//        SearchView searchView = (SearchView) menu.findItem(R.id.main_searchMenu).getActionView();
//        searchView.setQueryHint("Search Gnach here");
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
//        switch (item.getItemId()){
//            case R.id.main_logoutMenu:
//                Logout();
////            case R.id.personal_profile:
////                openPrivateZone();
////            default:
////                return super.onOptionsItemSelected(item);
//            }
        if(item.getItemId() == R.id.main_logoutMenu){
            Logout();
        }
        if(item.getItemId() == R.id.personal_profile){
            openPrivateZone();
        }
        return super.onOptionsItemSelected(item);
        }
}

