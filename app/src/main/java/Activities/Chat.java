package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gmach4u.R;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Chat extends AppCompatActivity {

    private EditText email, message, subject;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
}