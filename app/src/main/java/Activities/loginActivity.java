package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login,clientRegist,supplierRegist,mainSupplier;
    TextView info;
    private EditText userEmail,userPassword;
    FirebaseDatabase mDatabase ;
    DatabaseReference dbRootRef;
    FirebaseAuth fireBaseAuth;
    private ProgressDialog progressDialog;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userPassword= (EditText) findViewById(R.id.password);
        userEmail= (EditText) findViewById(R.id.Email);

        mainSupplier=(Button)findViewById(R.id.MainSupplier);
        mainSupplier.setOnClickListener(this);

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(this);

        clientRegist=(Button)findViewById(R.id.clientRegist);
        clientRegist.setOnClickListener(this);

        supplierRegist=(Button)findViewById(R.id.supplierRegist);
        supplierRegist.setOnClickListener(this);

         fireBaseAuth= FirebaseAuth.getInstance();
         progressDialog=new ProgressDialog(this);
        FirebaseUser user=fireBaseAuth.getCurrentUser();


//        if(user!=null){
//            finish();
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//        }
    }
    private void validate(String username , String userPassword) {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        fireBaseAuth.signInWithEmailAndPassword(username,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(loginActivity.this, "Sign successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loginActivity.this,MainActivity.class));
                } else{
                    Toast.makeText(loginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    progressDialog.dismiss();
                    if(counter==0){
                        login.setEnabled(false);
                    }
                    task.getException();
                }//end else
            }
        });

    }
    @Override
    public void onClick(View v) {
        if(login==v){
            openLoginClient();
        }
        if(clientRegist==v){
            openClientRegister();
        }
        if(supplierRegist==v){
            openSupplierRegister();
        }
        if(mainSupplier==v){
            Intent intent = new Intent(this, MainSupplier.class);
            startActivity(intent);
        }
    }
    public void openLoginClient(){
        validate(userEmail.getText().toString(),userPassword.getText().toString());
    }

    public void openClientRegister(){
        Intent intent = new Intent(this, ClientRegister.class);
        startActivity(intent);
    }
    public void openSupplierRegister(){
        Intent intent = new Intent(this, SupplierRegister.class);
        startActivity(intent);
    }
}