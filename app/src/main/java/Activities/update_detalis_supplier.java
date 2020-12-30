package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.ProductItem;
import Adapters.Supplier;


public class update_detalis_supplier extends AppCompatActivity implements View.OnClickListener{
    EditText sup_name, sup_open, sup_phone, sup_address;
    Button btnUpdate;
    String id, name, phone, address, open;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detalis_supplier);
        setUIViews();
        setDetails();
    }

    private void setUIViews() {
        //set edit text
        sup_name = findViewById(R.id.sup_name);
        sup_phone = findViewById(R.id.sup_phone);
        sup_address = findViewById(R.id.sup_address);
        sup_open = findViewById(R.id.OpeningTimeInput);
        //set button
        btnUpdate =  findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener((View.OnClickListener)this);
        //set DB ref
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Suppliers").child(firebaseAuth.getUid());
    }

    private void setDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //snapshot is the supplier
                Supplier s = snapshot.child("details").getValue(Supplier.class);
                sup_name.setText(s.getName());
                sup_address.setText(s.getAddress());
                sup_phone.setText(s.getPhone());
                sup_open.setText(s.getOpeningTime());
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdate){
            updateDetails();
        }
    }

    private void updateDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //snapshot is the supplier
                if (snapshot.exists()) {
                    Supplier s = snapshot.child("details").getValue(Supplier.class);
                    String uName, uPhone, uAddress,uOpen;

                    uName = sup_name.getText().toString();
                    uPhone = sup_phone.getText().toString();
                    uAddress = sup_address.getText().toString();
                    uOpen = sup_open.getText().toString();

                    s.setName(uName);
                    s.setPhone(uPhone);
                    s.setAddress(uAddress);
                    s.setOpeningTime(uOpen);
                    databaseReference.child("details").setValue(s);

                    Toast.makeText(update_detalis_supplier.this, "Update", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(update_detalis_supplier.this,MainSupplier.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    //************menu bar************

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(update_detalis_supplier.this,loginActivity.class));
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

