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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import Adapters.Supplier;

import android.widget.Spinner;

public class SupplierRegister extends AppCompatActivity{
    private  EditText userName, userPassword, userEmail,userPhone, userAddress, userOpeningTime;;
    private Button regButton;
    private Spinner userCategory;
    FirebaseAuth firebaseAuth;
    String name, email, password, phone, address, openingTime;
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_register);
        setUIViews();
        setButton();
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
        //set spinner
        userCategory = (Spinner) findViewById(R.id.CategoryInput);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        userCategory.setAdapter(categoriesAdapter);

    }//end setUIViews

    private boolean validate(){ return true; }

    private void setButton(){
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    email=userEmail.getText().toString().trim();
                    password=userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        sendUserData();
                                        Toast.makeText(SupplierRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SupplierRegister.this,MainActivity.class));
                                    } else{
                                        Toast.makeText(SupplierRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        task.getException();
                                    }//end else
                                }//end Oncomplete
                            });//end email and password
                }//end if
            }//end on click
        });//end click listener

    }//end setButton

    private void sendUserData() {
        name = userName.getText().toString();
        phone = userPhone.getText().toString();
        address = userAddress.getText().toString();
        openingTime = userOpeningTime.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        Supplier userProfile = new Supplier(name, email, phone,address,openingTime);
        myRef.child("Suppliers").push().child("details").setValue(userProfile);
    }//end sendUserData
}//end class