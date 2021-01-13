package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Chat extends AppCompatActivity {

    private EditText email, message, subject;
    private Button send;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        firebaseAuth = FirebaseAuth.getInstance();
//        setupUI();
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str_subject = subject.getText().toString().trim();
//                String str_message = message.getText().toString().trim();
//                String email = "oriyakron100@gmail.com";
//
//                if (str_message.isEmpty() || str_subject.isEmpty()) {
//                    Toast.makeText(Chat.this, "Please enter subject and message", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    String mail = "mailto:" + email + "?&subject=" + Uri.encode(str_subject) + "&body" + Uri.encode(str_message);
//
//                    Intent intent = new Intent(Intent.ACTION_SENDTO);
//                    intent.setData(Uri.parse(mail));
//
//                    try {
//                        startActivity(Intent.createChooser(intent, "Send Email.."));
//                    } catch (Exception e) {
//                        Toast.makeText(Chat.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//            }
//        });
//
    }
//
//    private void setupUI() {
//        email = (EditText) findViewById(R.id.email_chat);
//        message = (EditText) findViewById(R.id.message);
//        subject = (EditText) findViewById(R.id.etSubject);
//        send = (Button) findViewById(R.id.btnSend);
//    }
    //************menu bar************

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Chat.this,loginActivity.class));
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