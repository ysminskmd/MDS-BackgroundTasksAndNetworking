package com.example.shad.datasaver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager mConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        findViewById(R.id.getDataSaverDataBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBgStatus();

                boolean isNetworkMetered = mConnectivityManager.isActiveNetworkMetered();
                Log.i("Shad", String.format("Is network metered? %b", isNetworkMetered));

                NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(mConnectivityManager.getActiveNetwork());
                if (networkInfo != null) {
                    Log.i("Shad", String.format("Is network roaming? %b", networkInfo.isRoaming()));
                }
            }
        });

        findViewById(R.id.requestWhitelistBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWhitelist();
            }
        });
    }

    private void getBgStatus() {
        int bgStatus = mConnectivityManager.getRestrictBackgroundStatus();
        String status = "";
        switch (bgStatus) {
            case ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED:
                status = "disabled";
                break;
            case ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED:
                status = "enabled";
                break;
            case ConnectivityManager.RESTRICT_BACKGROUND_STATUS_WHITELISTED:
                status = "whitelisted";
                break;
        }
        Log.i("Shad", String.format("DataSaver status: %s", status));
    }

    private void requestWhitelist() {
        Intent intent = new Intent(Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }
}
