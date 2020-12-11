package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.Client;
import Adapters.Supplier;


public class MainSupplier extends AppCompatActivity  {

    private Button update, chat, stock, client;
   private TextView titel;
 //   private String email, pass;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;

    private static final String TAG = "Suppliers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_supplier);
        setViews();
    }
    private void setViews(){
        //   name = (TextView)findViewById(R.id.name_textview);
        update=(Button) findViewById(R.id.update);
        client=(Button) findViewById(R.id.customers);
        stock=(Button) findViewById(R.id.stock);
        chat=(Button) findViewById(R.id.chat);
        mListView = (ListView) findViewById(R.id.listView);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
        myRef = mFirebaseDatabase.getReference()
                .child("Suppliers").child(firebaseAuth.getUid());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainSupplier.this,update_detalis_supplier.class));
            }
        });
        client.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                startActivity(new Intent(MainSupplier.this,GmachCustomers.class));
            }
        });
        stock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                startActivity(new Intent(MainSupplier.this,GmachStockSupplier.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                startActivity(new Intent(MainSupplier.this,Chat.class));
            }
        });
    };
    private void showData(DataSnapshot snapshot) {
        if (!snapshot.hasChild("details")) {
            toastMessage("There is no details");
        } else {
            Supplier s = snapshot.child("details").getValue(Supplier.class);

            //display all the information
            Log.d(TAG, "show data: name: " + s.getName());

            ArrayList<String> array = new ArrayList<>();
            array.add(s.getName());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            mListView.setAdapter(adapter);
        }
    }




    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}