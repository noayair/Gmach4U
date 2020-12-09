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



//    private TextView returnBack, profileName, profileEmail, profilePhone;
//    private Button profileUpdate, changePassword;
//
//    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_zone);

        mListView = (ListView) findViewById(R.id.listView);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
        myRef = mFirebaseDatabase.getReference()
                .child("Clients").child(firebaseAuth.getUid());
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
                toastMessage("" + snapshot.toString());
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showData(DataSnapshot snapshot) {

        //youtube

//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            UserInformation uInfo = new UserInformation();
//            if(ds.getChildren() != null) {
//                uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName()); // set the name
//                uInfo.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail()); // set the email
//                uInfo.setPhone(ds.child(userID).getValue(UserInformation.class).getPhone()); // set the phone
//                uInfo.setUserID(ds.child(userID).getValue(UserInformation.class).getUserID()); // set the userID
//
//                //display all the information
//                Log.d(TAG, "show data: name: " + uInfo.getName());
//                Log.d(TAG, "show data: email: " + uInfo.getEmail());
//                Log.d(TAG, "show data: phone: " + uInfo.getPhone());
//                Log.d(TAG, "show data: userID: " + uInfo.getUserID());
//
//                ArrayList<String> array = new ArrayList<>();
//                array.add(uInfo.getName());
//                array.add(uInfo.getEmail());
//                array.add(uInfo.getPhone());
//                array.add(uInfo.getUserID());
//                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
//                mListView.setAdapter(adapter);

        //avital github

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////        PrivateZone userProfile = snapshot.getValue(PrivateZone.class);
//        if(user != null) {
//            profileName.setText("Name: " + user.getDisplayName());
//            profileEmail.setText("Email: " + user.getEmail());
//            profilePhone.setText("Phone Number: " + user.getPhoneNumber());
//        }
//            }
//        }



        if (!snapshot.hasChild("details")) {
            toastMessage("There is no details");
        } else {
            Client c = snapshot.child("details").getValue(Client.class);

//            for(DataSnapshot ds: snapshot.child("details").getChildren()){
//                Client uInfo = new Client();
//            if(ds != null) {
//                uInfo.setName(ds.getValue(UserInformation.class).getName()); // set the name
//                uInfo.setEmail(ds.getValue(UserInformation.class).getEmail()); // set the email
//                uInfo.setPhone(ds.getValue(UserInformation.class).getPhone()); // set the phone
//                uInfo.setUserID(ds.getValue(UserInformation.class).getUserID()); // set the userID
//
                //display all the information
                Log.d(TAG, "show data: name: " + c.getName());
                Log.d(TAG, "show data: email: " + c.getEmail());
                Log.d(TAG, "show data: phone: " + c.getPhone());
//                Log.d(TAG, "show data: userID: " + c.getUserID());

                ArrayList<String> array = new ArrayList<>();
                array.add(c.getName());
                array.add(c.getEmail());
                array.add(c.getPhone());
//                array.add(c.getUserID());
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
                mListView.setAdapter(adapter);
//            }
        }
    }

//    }

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











//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user != null){
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//        }



//        setupUI();
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("User details");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                PrivateZone userProfile = snapshot.getValue(PrivateZone.class);
//                profileName.setText("Name: " + user.getDisplayName());
//                profileEmail.setText("Email: " + user.getEmail());
//                profilePhone.setText("Phone Number: " + userProfile.getUserPhone());
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(PrivateZone.this, error.getCode(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        profileUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(PrivateZone.this, UpdateProfile.class));
//            }
//        });
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(myProfile.this, UpdatePassword.class));
//            }
//        });
//
//        returnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(myProfile.this, LoggedInProfile.class));
//            }
//        });
//    }
//
//    private void setupUI() {
//
////        returnBack = (TextView) findViewById(R.id.returnTextView);
//        profileName = (TextView) findViewById(R.id.tvProfileName);
//        profileEmail = (TextView) findViewById(R.id.tvProfileEmail);
////        profilePhone = (TextView) findViewById(R.id.tvProfilePhone);
//        profileUpdate = (Button) findViewById(R.id.btnProfileUpdate);
//        changePassword = (Button) findViewById(R.id.btnChangePass);
//    }


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