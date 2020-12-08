package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

public class SupplierRegister extends AppCompatActivity implements View.OnClickListener{
    private  EditText userName, userPassword, userEmail,userPhone, userAddress, userOpeningTime;;
    private Button signUpButton,LogInButton;
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
    }//end onCreate

    private void setUIViews(){
        //set text
        userName= (EditText) findViewById(R.id.GmachNameInput);
        userEmail= (EditText) findViewById(R.id.EmailInput);
        userPassword= (EditText) findViewById(R.id.PasswordInput);
        userPhone= (EditText) findViewById(R.id.PhoneNumberInput);
        userAddress= (EditText) findViewById(R.id.AddressInput);
        userOpeningTime= (EditText) findViewById(R.id.OpeningTimeInput);
        //set button
        signUpButton=(Button) findViewById(R.id.signUpSupplier);
        LogInButton=(Button) findViewById(R.id.LoginRegist);
        signUpButton.setOnClickListener((View.OnClickListener) this);
        LogInButton.setOnClickListener((View.OnClickListener) this);
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        //set spinner
        userCategory = (Spinner) findViewById(R.id.CategoryInput);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoriesArray));
        userCategory.setAdapter(categoriesAdapter);

    }//end setUIViews

    private void signUp(){

        name = userName.getText().toString();
        phone = userPhone.getText().toString();
        address = userAddress.getText().toString();
        openingTime = userOpeningTime.getText().toString();
        email=userEmail.getText().toString().trim();
        password=userPassword.getText().toString().trim();

        if(!validate()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    writeNewUser(task.getResult().getUser().getUid());
                    Toast.makeText(SupplierRegister.this, "Registration success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SupplierRegister.this,MainSupplier.class));
                    finish();
                } else {
                    Toast.makeText(SupplierRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }//end else
            }//end On complete
        });//end create user
    }//end setButton

    private void writeNewUser(String userId) {
        Supplier user = new Supplier(name, email, phone,address,openingTime,userId);
        myRef.child("Suppliers").child(Integer.toString(usersCounter++)).child("details").setValue(user);
    }//end write new user

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signUpSupplier) {
            signUp();
        }
        else if(v.getId() == R.id.LoginRegist){
            startActivity(new Intent(SupplierRegister.this,loginActivity.class));
        }
    }//end onClick

    private boolean validate (){
        Boolean result=false;

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()){
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")){
            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 6){
            Toast.makeText(this, "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }
        else if(!phone.matches("^[0-9]+$") || phone.length() != 10){
            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();

        }
        else if(!address.matches("^[a-zA-Z]+\\s[a-zA-Z\\s]+$")){
            Toast.makeText(this, "Please enter city and street", Toast.LENGTH_SHORT).show();
        }
        else if(userCategory.getSelectedItem().toString().equals("Search by category")){
            //Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please select catagory", Toast.LENGTH_SHORT).show();
        }

        else{
            result=true;
        }
        return result;
    }
}//end class