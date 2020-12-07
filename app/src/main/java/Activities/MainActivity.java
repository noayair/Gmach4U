package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private Spinner categorySelectSpinner;
    private Spinner locationSelectSpinner;
    private Button search_by_category_Button;
    private Button search_by_location_Button;
    private Button search_by_name_Button;
    private TextView textBox1;
    private TextView textBox2;
    private Button Logout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            }
        });


        //************************SEARCH BY NAME**************************************
        search_by_name_Button = (Button)findViewById(R.id.search_by_name_button);
        search_by_name_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


    //**************************Logout button***************************************
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this,loginActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}