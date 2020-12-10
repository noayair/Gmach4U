package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmach4u.R;

import java.util.ArrayList;

import Activities.GmachStockSupplier;
import Activities.Product;
import Activities.ProductDetails;

public class ProductAdapter extends ArrayAdapter<ProductItem>{

    public ProductAdapter(Context context, ArrayList<ProductItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductItem user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }
        // Lookup view for data population
//        ImageView pImg = (ImageView) convertView.findViewById(R.id.pImg);
        TextView pName = (TextView) convertView.findViewById(R.id.pName);
        TextView pUnits = (TextView) convertView.findViewById(R.id.pUnits);
//        Button goToDetails =  (Button) convertView.findViewById(R.id.goToDetails);
//        goToDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(v.getId() == R.id.goToDetails){
//
//                }
//            }
//
//        });
        // Populate the data into the template view using the data object
        pName.setText("name:" +user.getName());
        pUnits.setText("units in stock: "+user.getUnitsInStock());
        // Return the completed view to render on screen
        return convertView;
    }
}