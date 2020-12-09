package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class GmachDetails extends AppCompatActivity {
    private EditText GmachAdress, GmachEmail, GmachOpeningHours,GmachPhone;
    private Button viewPrudocts;
    private Button chat;
    DatabaseReference ref;
    RatingBar ratingBar;
    private ImageButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmach_details);
        setUIViews();
//        Intent intent=getIntent();
//        String key=intent.getStringExtra("key");
//        ref= FirebaseDatabase.getInstance().getReference("suppliers");
//        Query query=ref.orderByKey().equalTo(key);
//        ValueEventListener eventListener=new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if(snapshot.exists()){
//                for(DataSnapshot ds: snapshot.getChildren()) {
//                    GmachAdress.setText(ds.child("address").getValue(String.class));
//                    GmachEmail.setText(ds.child("email").getValue(String.class));
//                    GmachOpeningHours.setText(ds.child("opening Time").getValue(String.class));
//                    GmachPhone.setText(ds.child("phone").getValue(String.class));
//                }
//            }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        query.addListenerForSingleValueEvent(eventListener);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                int rating=(int) v;
                String messege =null;
                switch (rating){
                    case 1:
                        messege="sorry to hear that:(";
                        break;
                    case 2:
                        messege="you can do better then this";
                        break;
                    case 3:
                        messege="good enough!";
                        break;
                    case 4:
                        messege="Great! Thank you";
                        break;
                    case 5:
                        messege="Awesome! you are the best";
                        break;
                }
                Toast.makeText(GmachDetails.this,messege,Toast.LENGTH_SHORT).show();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GmachDetails.this,Chat.class));
            }
        });
        viewPrudocts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GmachDetails.this,Chat.class));
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= "tel:" + GmachPhone.getText().toString();
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0304958756"));
                startActivity(intent);
            }
        });
    }//end on create


    private void setUIViews(){
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        GmachAdress= (EditText) findViewById(R.id.gAdress);
        GmachEmail= (EditText) findViewById(R.id.gopeninghours);
        GmachOpeningHours= (EditText) findViewById(R.id.gEmail1);
        GmachPhone= (EditText) findViewById(R.id.gPhone);
        viewPrudocts=(Button) findViewById(R.id.chat1);
        chat=(Button) findViewById(R.id.viewprudocts);
        call=(ImageButton) findViewById(R.id.callbtn);
    }
}