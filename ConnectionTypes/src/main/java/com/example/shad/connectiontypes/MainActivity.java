package com.example.shad.connectiontypes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager mConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        findViewById(R.id.getNetworkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    getNetworkInfo();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getNetworkInfo() {
        Network currentNetwork = mConnectivityManager.getActiveNetwork();
        NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(currentNetwork);
        if (networkInfo != null) {
            Log.i("Shad", String.format("Is connected? : %b, type name: %s",
                    networkInfo.isConnected(), networkInfo.getTypeName()));
        } else {
            Log.i("Shad", "No active network");
        }
    }
}
