package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class SearchHistory extends AppCompatActivity {
    private static final String TAG = "SearchHistory";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private ListView mListView;
    private TextView myProduts;
    private ArrayList<String> showList;
    private ArrayAdapter<String> arrayAdapter;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        setUIViews();
        setAdapter();
        showProducts();
    }//end onCreate

    private void showProducts() {
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
                if (!snapshot.hasChild("reserves")) {
                    showList.add("There is no products yet");
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    showList.add(snapshot.toString());
                    arrayAdapter.notifyDataSetChanged();
                }
            }//end onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//end myRef
    }//end showProducts

    private void setAdapter() {
        showList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showList);
        mListView.setAdapter(arrayAdapter);
    }//end setAdapter

    private void setUIViews() {
        //set text
        mListView = (ListView) findViewById(R.id.MyProdutsList);
        myProduts = (TextView) findViewById(R.id.MyProductsHistory);
        //set database
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Clients").child("reserves");
    }//end setUIViews




//********menu bar************

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SearchHistory.this,loginActivity.class));
    }

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openPrivateZone(){
        Intent intent = new Intent(this, PrivateZone.class);
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