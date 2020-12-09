package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import Adapters.Supplier;


public class update_detalis_supplier extends AppCompatActivity {
    private EditText supName, supEmail, supPhone, supAddress, supTime;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detalis_supplier);
        setUIViews();
       firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =
                firebaseDatabase.getReference();


       databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

//                for (DataSnapshot child : dataSnapshot.getChildren())
//                {
//                    for(DataSnapshot grandChild: child.getChildren()){
//                        Toast.makeText(getApplicationContext(), grandChild.getKey() ,Toast.LENGTH_LONG).show();
//
//                    }

               //     Toast.makeText(getApplicationContext(), child.getValue(String.class), Toast.LENGTH_LONG).show();
   //             }


           //     Supplier supplier = dataSnapshot.child("Suppliers").child("details").getValue(Supplier.class);
//                Toast.makeText(update_detalis_supplier.this,dataSnapshot.getValue(Supplier.class).toString(), Toast.LENGTH_SHORT).show();

//              supName.setText(supplier.getName());
//              supEmail.setText(supplier.getEmail());
//               supPhone.setText(supplier.getPhone());
            }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(update_detalis_supplier.this, error.getCode(), Toast.LENGTH_SHORT).show();

           }
       });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick (View v){
                String name = supName.getText().toString();
                String email = supEmail.getText().toString();
                String phone = supPhone.getText().toString();
                String address = supAddress.getText().toString();
                String time = supTime.getText().toString();


          //      Supplier supplier = new Supplier(name, email, phone,address,time);

          //      databaseReference.child("User details").setValue(supplier);

                startActivity(new Intent(update_detalis_supplier.this, MainSupplier.class));
                Toast.makeText(update_detalis_supplier.this, "Changed Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


            private void setUIViews() {
                supName = (EditText) findViewById(R.id.update_name);
               // supPassword = (EditText) findViewById(R.id.updae_password);
                supEmail = (EditText) findViewById(R.id.update_email);
                supPhone = (EditText) findViewById(R.id.update_phone);
                supAddress = (EditText) findViewById(R.id.update_addres);
                supTime = (EditText) findViewById(R.id.update_open);
                save = (Button) findViewById(R.id.update_ok);

            }

//
//    private boolean validate(){
//        Boolean result=false;
//
//        String name= supName.getText().toString();
//        String password= supPassword.getText().toString();
//        String email= supEmail.getText().toString();
//        String phone= supPhone.getText().toString();
//
//        if(name.isEmpty() && password.isEmpty() && email.isEmpty()){
//            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
//        }
//        else if(!name.matches("^[a-zA-Z]+\\s[a-zA-Z\\s]+$")) {
//            Toast.makeText(this, "Please enter your full name (in English)", Toast.LENGTH_SHORT).show();
//        }
//        else if(!email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")){
//            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
//        }
//        else if(password.length() < 6){
//            Toast.makeText(this, "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
//        }
//        else if(!phone.matches("^[0-9]+$") || phone.length() != 10){
//            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            result=true;
//        }
//        return result;
//    }

    }