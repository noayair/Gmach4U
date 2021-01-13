//package Activities;
//
///**
//        * Copyright 2010-present Facebook.
//        *
//        * Licensed under the Apache License, Version 2.0 (the "License");
//        * you may not use this file except in compliance with the License.
//        * You may obtain a copy of the License at
//        *
//        *    http://www.apache.org/licenses/LICENSE-2.0
//        *
//        * Unless required by applicable law or agreed to in writing, software
//        * distributed under the License is distributed on an "AS IS" BASIS,
//        * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        * See the License for the specific language governing permissions and
//        * limitations under the License.
//        */
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//public class PopupDemoActivity extends Activity implements OnClickListener {
//    LinearLayout layoutOfPopup;
//    PopupWindow popupMessage;
//    Button popupButton, insidePopupButton;
//    TextView popupText;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        init();
//        popupInit();
//    }
//    public void init() {
//        popupButton = (Button) findViewById(R.id.popupbutton);
//        popupText = new TextView(this);
//        insidePopupButton = new Button(this);
//        layoutOfPopup = new LinearLayout(this);
//        insidePopupButton.setText("OK");
//        popupText.setText("This is Popup Window.press OK to dismiss it.");
//        popupText.setPadding(0, 0, 0, 20);
//        layoutOfPopup.setOrientation(1);
//        layoutOfPopup.addView(popupText);
//        layoutOfPopup.addView(insidePopupButton);
//    }
//    public void popupInit() {
//        popupButton.setOnClickListener(this);
//        insidePopupButton.setOnClickListener(this);
//        View parent = findViewById(R.layout.main);
//        popupMessage = new PopupWindow(layoutOfPopup, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        popupMessage.setContentView(layoutOfPopup);
//        popupMessage.showAtLocation(parent, Gravity.CENTER, 0, 0);
//    }
//    @Override public void onClick(View v) {
//        if (v.getId() == R.id.popupbutton) {
//            popupMessage.showAsDropDown(popupButton, 0, 0);
//
//        }
//        else {
//            popupMessage.dismiss();
//        }
//    }
//}
