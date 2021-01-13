package Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.gmach4u.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Adapters.Client;
import Adapters.Supplier;

public class UpdateDetailsClient extends AppCompatActivity implements View.OnClickListener{
    EditText sup_name, sup_phone;
    Button btnUpdate;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_client);
        setUIViews();
        setDetails();
    }

    private void setUIViews() {
        //set edit text
        sup_name = findViewById(R.id.nameClient);
        sup_phone = findViewById(R.id.phoneClient);
        //set button
        btnUpdate =  findViewById(R.id.updateClient);
        btnUpdate.setOnClickListener((View.OnClickListener)this);
        //set DB ref
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clients").child(firebaseAuth.getUid());
    }

    private void setDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //snapshot is the supplier
                Client s = snapshot.child("details").getValue(Client.class);
                sup_name.setText(s.getName());
                sup_phone.setText(s.getPhone());
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.updateClient){
            updateDetails();
        }
    }

    private void updateDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //snapshot is the client
                if (snapshot.exists()) {
                    Client s = snapshot.child("details").getValue(Client.class);
                    String uName, uPhone;
                    uName = sup_name.getText().toString();
                    uPhone = sup_phone.getText().toString();
                    s.setName(uName);
                    s.setPhone(uPhone);
                    databaseReference.child("details").setValue(s);

                    Toast.makeText(UpdateDetailsClient.this, "Update", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UpdateDetailsClient.this,MainActivity.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    //************menu bar************

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(UpdateDetailsClient.this,loginActivity.class));
    }

    public void openMain(){
        Intent intent = new Intent(this, MainSupplier.class);
        startActivity(intent);
    }

    public void openPrivateZone(){
        Intent intent = new Intent(this, update_detalis_supplier.class);
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