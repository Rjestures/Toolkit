package com.rjesture.startupkit.internetchecker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.rjesture.startupkit.R;

public class InternetStateChecker implements ConnectivityReceiverListener {

    private final Context context;
    //For Alert Dialog
    private final boolean cancelable;
    private final String title;
    private final String message;
    private final int color;
    private final int icon;
    private final int textColor;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    private ConnectivityReceiver receiver;
    private boolean isAlreadyVisible;

    @SuppressLint("ResourceType")
    private InternetStateChecker(Context context, String title, String message, int color, int textColor, int icon, boolean cancelable) {
        this.context = context;
        this.title = title == "" ? "UH-SNAP!" : title;
        this.message = message == "" ? "You are not connected to Internet." : message;
        this.color = color == 0 ? ContextCompat.getColor(context, Color.parseColor("#FD1504")) : ContextCompat.getColor(context, color);
        this.textColor = textColor == 0 ? ContextCompat.getColor(context, Color.parseColor("#FFFFFF")) : ContextCompat.getColor(context, textColor);
        this.icon = icon == 0 ? R.drawable.ic_no_wifi : icon;
        this.cancelable = cancelable;
        initReciever(context);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            hideAlertDialog();
            isAlreadyVisible = false;
        } else {
            if (!isAlreadyVisible) {
                showAlertDialog();
                isAlreadyVisible = true;
            }
        }
    }

    private void showSnack(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    private void initReciever(Context context) {
        receiver = new ConnectivityReceiver();
        context.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        receiver.setConnectionCallback(this);
    }

    private void showAlertDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.custom_dialog, null);
        TextView txtTitle = layoutView.findViewById(R.id.txtTitle);
        TextView txtMessage = layoutView.findViewById(R.id.txtMessage);
        ImageView imageView = layoutView.findViewById(R.id.imageView);
        CardView myCardView = layoutView.findViewById(R.id.myCardView);
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.round_corner);
        mDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        // myCardView.setBackgroundColor(color);
        myCardView.setBackground(mDrawable);
        myCardView.setRadius(30);
        txtTitle.setText(title);
        txtMessage.setText(message);
        imageView.setImageResource(icon);
        txtMessage.setTextColor(textColor);
        txtTitle.setTextColor(textColor);

        dialogBuilder.setCancelable(cancelable);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlideInOutAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void hideAlertDialog() {
        if (isAlreadyVisible) {
            alertDialog.dismiss();
        }
    }


    public void stop() {
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
        }
    }

    public static class Builder {
        private final Context context;
        private boolean cancelable;
        private String title = "";
        private String message = "";
        private int color;
        private int textColor;
        private int icon;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setDialogTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDialogMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDialogBgColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setDialogIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setDialogTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public InternetStateChecker build() {
            InternetStateChecker checker = new InternetStateChecker(context, title, message, color, textColor, icon, cancelable);

            return checker;
        }
    }
}
