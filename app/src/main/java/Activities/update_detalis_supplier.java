package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
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


public class update_detalis_supplier extends AppCompatActivity {
    EditText sup_name, sup_open, sup_phone, sup_address;
    Button btnUpdate;
    String id, name, phone, address, open;
    FirebaseAuth firebaseAuth;
   // private Spinner sup_category, userLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detalis_supplier);

        sup_name = findViewById(R.id.sup_name);
        sup_phone = findViewById(R.id.sup_phone);
        sup_address = findViewById(R.id.sup_address);
       sup_open = findViewById(R.id.OpeningTimeInput);


        btnUpdate =  findViewById(R.id.btnUpdate);

        Intent intent= getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
      //  email = intent.getStringExtra("email");
        address = intent.getStringExtra("address");
        open = intent.getStringExtra("openingTime");




        firebaseAuth= FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Suppliers").child(firebaseAuth.getUid());
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

btnUpdate.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        firebaseAuth= FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
        .child("Suppliers").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
@Override
public void onDataChange(DataSnapshot snapshot) {
        //snapshot is the supplier
        if (snapshot.exists()) {
            Supplier s = snapshot.child("details").getValue(Supplier.class);
            String uName, uPhone, uAddress,uOpen, uEmail;

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



});
    }
}

