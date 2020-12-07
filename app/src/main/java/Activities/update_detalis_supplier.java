package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class update_detalis_supplier extends AppCompatActivity {
    private EditText supName, supPassword, supEmail, supPhone, supAddress, supTime;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detalis_supplier);
     //   setUIViews();
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserProfile userProfile = snapshot.getValue(UserProfile.class);
//                newUserName.setText(userProfile.getUserName());
//                newUserEmail.setText(userProfile.getUserEmail());
//                newUserPhone.setText(userProfile.getUserPhone());
//            }

//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateProfile.this, error.getCode(), Toast.LENGTH_SHORT).show();
//
//            }
        //  });
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
  //      public void onClick (View v){
//                String name = newUserName.getText().toString();
//                String email = newUserEmail.getText().toString();
//                String phone = newUserPhone.getText().toString();
//
//                UserProfile userProfile = new UserProfile(name, email, phone);
//
//                databaseReference.child("User details").setValue(userProfile);
//
//                startActivity(new Intent(UpdateProfile.this, myProfile.class));
//                Toast.makeText(UpdateProfile.this, "Changed Successfully!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


//            private void setUIViews () {
//                supName = (EditText) findViewById(R.id.update_name);
//                supPassword = (EditText) findViewById(R.id.updae_password);
//                supEmail = (EditText) findViewById(R.id.update_email);
//                supPhone = (EditText) findViewById(R.id.update_phone);
//                supAddress = (EditText) findViewById(R.id.update_addres);
//                supTime = (EditText) findViewById(R.id.update_open);
//                save = (Button) findViewById(R.id.update_ok);
//
//            }

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
    }