package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;

public class SearchResults extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        firebaseAuth=FirebaseAuth.getInstance();
    }


    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this , loginActivity.class));
    }

    public void openPrivateZone(){
        Intent intent = new Intent(this, PrivateZone.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Type here to search");
        return true;
    }

//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    public boolean onQueryTextChange(String newText) {
//        adapter1.getFilter().filter(newText);
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.main_logoutMenu:
//                Logout();
////            case R.id.personal_profile:
////                openPrivateZone();
////            default:
////                return super.onOptionsItemSelected(item);
//        }
        if(item.getItemId() == R.id.main_logoutMenu){
            Logout();
        }
        if(item.getItemId() == R.id.personal_profile){
            openPrivateZone();
        }
        return super.onOptionsItemSelected(item);
    }
}