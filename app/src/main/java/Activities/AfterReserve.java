package Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.gmach4u.R;

public class AfterReserve extends AppCompatActivity implements View.OnClickListener{
    private Button resList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_reserve);
//        resList = (Button) findViewById(R.id.goToReserves);
//        resList.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.goToReserves){
//            Intent i = new Intent(AfterReserve.this, SearchHistory.class);
//            startActivity(i);
//        }
    }
}