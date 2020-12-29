package Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.gmach4u.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import Adapters.ProductItem;
import Adapters.ReserveProduct;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class ProductDetailsClient extends AppCompatActivity implements View.OnClickListener{
    private TextView name, units, desc, burrow;
//    private Button reserve;
    private DatabaseReference suppRef, mainRef;
    private FirebaseAuth firebaseAuth;
    private String suppId, prodId;
    private ProductItem pi;
    //
    private Context mContext;
    private Activity mActivity;
    private LinearLayout mLayout;
    private Button mButton;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_client);
        setUIViews();
        updateDetails();
        //
        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = ProductDetailsClient.this;

        // Get the widgets reference from XML layout
        mLayout = (LinearLayout) findViewById(R.id.rl);
        mButton = (Button) findViewById(R.id.pcreserve);

        // Set a click listener for the text view
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.activity_after_reserve,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mLayout, Gravity.CENTER,0,0);
            }
        });
    }

    private void setUIViews() {
        //set text
        name = (TextView) findViewById(R.id.pcname);
        units = (TextView) findViewById(R.id.pcunits);
        desc = (TextView) findViewById(R.id.pcdesc);
        burrow = (TextView) findViewById(R.id.pcburrowTime);
        //set button
//        reserve = (Button) findViewById(R.id.pcreserve);
//        reserve.setOnClickListener((View.OnClickListener) this);
        //set string
        Intent intent = getIntent();
        String sp[] = intent.getStringExtra("key").split("pKey:");
        suppId = sp[0];
        prodId = sp[1];
        //set firebase
        firebaseAuth= FirebaseAuth.getInstance();
        mainRef = FirebaseDatabase.getInstance().getReference();
        suppRef = FirebaseDatabase.getInstance().getReference("Suppliers").child(suppId).child("products").child(prodId);
        //set popup
//        @SuppressLint("ResourceType") View parent = findViewById(R.layout.activity_product_details_client);
//        popupMessage = new PopupWindow(new LinearLayout(this), LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        popupMessage.setContentView(new LinearLayout(this));
//        popupMessage.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }// end set view


    private void updateDetails() {
        suppRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    pi = snapshot.getValue(ProductItem.class);
                    name.setText(pi.getName());
                    units.setText(pi.getUnitsInStock());
                    desc.setText(pi.getDescription());
                    burrow.setText(pi.getBurrowTime());
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });//end listener
    }//end update

//    @SuppressLint("ResourceType")
    private void reserveProduct(){
        //int unitsOfProd = Integer.parseInt(pi.getUnitsInStock());
        //if(unitsOfProd > 0) {
            ReserveProduct rp = new ReserveProduct(prodId, suppId, firebaseAuth.getUid());
            String id = Integer.toString(rp.getId());
            //add product to client list
            mainRef.child("Clients").child(firebaseAuth.getUid()).child("reserves").child(id).setValue(rp);
            //add product to supplier list
            mainRef.child("Suppliers").child(suppId).child("reserves").child(id).setValue(rp);
            //update the units in stock
            //pi.setUnitsInStock(Integer.toString(--unitsOfProd));
            mainRef.child("Suppliers").child(suppId).child("products").child(prodId).setValue(pi);
//            Intent i = new Intent(ProductDetailsClient.this, AfterReserve.class);
//            startActivity(i);
        //}
//        else {
//            makeToast("there is no enough units in stock!");
//        }
    }//end reserve product
    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.pcreserve) {
//            reserveProduct();
//        }
    }

    private void makeToast(String m){
        Toast.makeText(ProductDetailsClient.this, m, Toast.LENGTH_SHORT).show();
    }
}