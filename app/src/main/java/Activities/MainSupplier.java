package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.gmach4u.R;


public class MainSupplier extends AppCompatActivity  {

    private Button update, chat, stock, client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_supplier);
        setViews();
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


    private void setViews(){
        update=(Button) findViewById(R.id.update);
        client=(Button) findViewById(R.id.customers);
        stock=(Button) findViewById(R.id.stock);
        chat=(Button) findViewById(R.id.chat);


    }



}