package com.example.senku;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class GameEndPopup extends PopupWindow {
    private Context context;
    private StylizedTextView title;
    private Button mainMenuButton;
    private LinearLayout layout;

    public GameEndPopup(Context context, String titleString) {
        super(context);
        this.context = context;
        addComponentsToLayout(titleString);
    }

    public void addComponentsToLayout(String titleString) {
        // Add Layout
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        int padding = (int) (20 * context.getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        // Add Title
        title = new StylizedTextView(context, titleString, 40, ContextCompat.getColor(context, R.color.defaultText), ContextCompat.getColor(context, R.color.defaultBackgroundText));
        title.setGravity(Gravity.CENTER);

        // Add Button
        mainMenuButton = new Button(context);
        mainMenuButton.setText("Main Menu");
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MainActivity.class));
                dismiss(); // Close the popup
            }
        });

        layout.addView(title);
        layout.addView(mainMenuButton);

        setContentView(layout);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);    }

    public StylizedTextView getGameEndTitle() {
        return title;
    }

    public void setGameEndTitle(StylizedTextView title) {
        this.title = title;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public void setMainMenuButton(Button mainMenuButton) {
        this.mainMenuButton = mainMenuButton;
    }
}
