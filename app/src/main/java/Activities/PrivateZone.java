package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewDatabase;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.Client;

public class PrivateZone extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "PrivateZone";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;
    private TextView myProfile;
    private Button update, myProtucts;
    private ArrayList<String> showList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_zone);
        setUIViews();
        setAdapter();
        showDetails();
    }

    private void setUIViews() {
        //set text
        mListView = (ListView) findViewById(R.id.listView);
        myProfile = (TextView) findViewById(R.id.MyProfile);
        //set buttons
        update = (Button) findViewById(R.id.UpdateButton);
        myProtucts = (Button) findViewById(R.id.MyProductsButton);
        update.setOnClickListener((View.OnClickListener)this);
        myProtucts.setOnClickListener((View.OnClickListener)this);
        //set database
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Clients").child(firebaseAuth.getUid());
    }//end setUIViews

    private void setAdapter() {
        showList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showList);
        mListView.setAdapter(arrayAdapter);
    }//end setAdapter

    private void showDetails() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    //user is sign-in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    //user is sign-out
                    Log.d(TAG, "onAuthStateChanged:signed_out: ");
                }
            }//end onAutoStateChanged
        };//end listener

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild("details")) {
                    toastMessage("There is no details");
                } else {
                    Client c = snapshot.child("details").getValue(Client.class);
                    //display all the information
                    Log.d(TAG, "Name: " + c.getName());
                    Log.d(TAG, "Email: " + c.getEmail());
                    Log.d(TAG, "Phone: " + c.getPhone());
                    showList.add("Name: " + c.getName());
                    showList.add("Email: " + c.getEmail());
                    showList.add("Phone: " + c.getPhone());
                    arrayAdapter.notifyDataSetChanged();
                }
            }//end onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end myRef
    }//end showDetails

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.UpdateButton){
            startActivity(new Intent(PrivateZone.this, UpdateDetailsClient.class));
        }
        if(v.getId() == R.id.MyProductsButton){
            goToProtucts();
        }
    }
    private void goToProtucts(){
        Intent i = new Intent(PrivateZone.this, SearchHistory.class);
        startActivity(i);
    }

    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }//end onStart

    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }//end onStop

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }//end toast

    //***********menu bar**************
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this , loginActivity.class));
    }//end logout

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }//end menu

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_logoutMenu){
            Logout();
        }
        if(item.getItemId() == R.id.Home){
            openMain();
        }
        return super.onOptionsItemSelected(item);
    }
}