package com.tayur.suktas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tayur.suktas.data.DataProvider;
import com.tayur.suktas.data.Language;
import com.tayur.suktas.data.YesNo;
import com.tayur.suktas.detail.common.CustomAdapter;
import com.tayur.suktas.detail.settings.SettingsActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "HomeActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading_please_wait), true);

        DataProvider.init(getAssets());
        initPreferences();
        initLayout();

        progressDialog.dismiss();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

                TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
                int defaultColor = textView.getTextColors().getDefaultColor();
                textView.setTextColor(defaultColor);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.suktas);
                builder.setTitle(R.string.app_name);
                builder.setView(messageView);
                builder.create();
                builder.show();

                return true;
            case R.id.settings:

                Intent intent = new Intent(this, SettingsActivity.class);
                Log.d(TAG, "Launching Settings Activity");
                startActivity(intent);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchMadhvanama(View v) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=com.vtayur.madhvanama"));
        startActivity(intent);
    }

    private void initLayout() {
        final List<String> sectionNames = DataProvider.getMenuNames();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position > 0)
                    return 1;
                else
                    return 2;
            }
        });
        CustomAdapter customAdapter = new CustomAdapter(this, sectionNames);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        recyclerView.setLayoutManager(layoutManager);
    }

    private void initPreferences() {
        SharedPreferences settings = getSharedPreferences(DataProvider.PREFS_NAME, 0);
        String isLocalLangAlreadySaved = settings.getString(DataProvider.SHLOKA_DISP_LANGUAGE, "");
        String isLearningModeSaved = settings.getString(DataProvider.LEARNING_MODE, "");
        if (isLocalLangAlreadySaved.isEmpty()) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(DataProvider.SHLOKA_DISP_LANGUAGE, Language.san.toString());

            editor.commit();

            Log.d(TAG, "Setting the default launch language preference to Sanskrit at startup - " + settings.getString(DataProvider.SHLOKA_DISP_LANGUAGE, ""));
        }

        if (isLearningModeSaved.isEmpty()) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(DataProvider.LEARNING_MODE, YesNo.yes.toString());

            editor.commit();

            Log.d(TAG, "Setting the default learn mode preference to 'Yes' - " + settings.getString(DataProvider.LEARNING_MODE, ""));
        }
    }
}
