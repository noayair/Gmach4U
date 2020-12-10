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

public class PrivateZone extends AppCompatActivity {
    private static final String TAG = "PrivateZone";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_zone);

        //connect to the client's details from the firebase
        mListView = (ListView) findViewById(R.id.listView);
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Clients").child(firebaseAuth.getUid());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    //user is sign-in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                    toastMessage("successfully signed_in with: " + user.getEmail());
                } else {
                    //user is sign-out
                    Log.d(TAG, "onAuthStateChanged:signed_out: ");
                    toastMessage("successfully signed_out");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //take the details from the firebase and put them in the listView
    private void showData(DataSnapshot snapshot) {
        if (!snapshot.hasChild("details")) {
            toastMessage("There is no details");
        } else {
            Client c = snapshot.child("details").getValue(Client.class);

                //display all the information
                Log.d(TAG, "show data: name: " + c.getName());
                Log.d(TAG, "show data: email: " + c.getEmail());
                Log.d(TAG, "show data: phone: " + c.getPhone());

                ArrayList<String> array = new ArrayList<>();
                array.add(c.getName());
                array.add(c.getEmail());
                array.add(c.getPhone());
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
                mListView.setAdapter(adapter);
        }
    }

    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //menu bar
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this , loginActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Type here to search");
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_logoutMenu){
            Logout();
        }
        return super.onOptionsItemSelected(item);
    }
}