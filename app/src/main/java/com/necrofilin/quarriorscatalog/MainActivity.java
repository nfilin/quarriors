package com.necrofilin.quarriorscatalog;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.necrofilin.quarriorscatalog.record.PackageList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chewy.ttf");
        RelativeLayout costL = (RelativeLayout) findViewById(R.id.costLayout);
        TextView costTV = (TextView) costL.getChildAt(0);
        costTV.setTypeface(face);
        long startNanoTime, endNanoTime;
        startNanoTime= System.nanoTime();
        PackageList list = (PackageList) ResourceXmlParser.parse(getResources(), R.xml.package_list);
        endNanoTime = System.nanoTime();

        Log.e("CREATE.spent", String.valueOf(endNanoTime - startNanoTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
