package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.gmach4u.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import Adapters.Supplier;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Spinner;

public class SupplierRegister extends AppCompatActivity implements View.OnClickListener{
    private  EditText userName, userPassword, userEmail,userPhone, userAddress, userOpeningTime;;
    private Button regButton;
    private Spinner userCategory;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    String name, email, password, phone, address, openingTime;
    static int usersCounter =1;
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_register);
        setUIViews();
        signUp();
    }//end onCreate

    private void setUIViews(){
        userName= (EditText) findViewById(R.id.GmachNameInput);
        userEmail= (EditText) findViewById(R.id.EmailInput);
        userPassword= (EditText) findViewById(R.id.PasswordInput);
        userPhone= (EditText) findViewById(R.id.PhoneNumberInput);
        regButton=(Button) findViewById(R.id.signUpSupplier);
        userAddress= (EditText) findViewById(R.id.AddressInput);
        userOpeningTime= (EditText) findViewById(R.id.OpeningTimeInput);
        firebaseAuth= FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        regButton.setOnClickListener((View.OnClickListener) this);
        //set spinner
        userCategory = (Spinner) findViewById(R.id.CategoryInput);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        userCategory.setAdapter(categoriesAdapter);

    }//end setUIViews

    private boolean validate(){ return true; }

    private void signUp(){
                if(!validate()) {
                    return;
                }
                email=userEmail.getText().toString().trim();
                password=userPassword.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            writeNewUser(task.getResult().getUser().getUid());
                            Toast.makeText(SupplierRegister.this, "Registration success", Toast.LENGTH_SHORT).show();
                            // Go to MainActivity
                            startActivity(new Intent(SupplierRegister.this,MainSupplier.class));
                            finish();
                        } else {
                            Toast.makeText(SupplierRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }//end else
                    }//end On complete
                });//end create user
    }//end setButton

    private void writeNewUser(String userId) {
        name = userName.getText().toString();
        phone = userPhone.getText().toString();
        address = userAddress.getText().toString();
        openingTime = userOpeningTime.getText().toString();
        Supplier user = new Supplier(name, email, phone,address,openingTime,userId);

        myRef.child("Suppliers").child(Integer.toString(usersCounter)).child("details").setValue(user);
    }//end write new user

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signUpSupplier) {
            signUp();
        }
    }//end onClick
}//end class