package com.example.gl552vw.syarah_1202150249_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] item;
    private ListView listView;
    private Button btn_async;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference list dan button
        listView = (ListView) findViewById(R.id.listViewNama);
        btn_async = (Button) findViewById(R.id.btn_async);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        //button onClick
        btn_async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start class Async Task
                new  MyTask(MainActivity.this).execute();
            }
        });

        //get resource string
        item = getResources().getStringArray(R.array.listNama);

        //setting adapter
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        //inflate menu
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ListNama:
                Intent iNama = new Intent(this, MainActivity.class);
                startActivity(iNama);
                finish();
                return true;
            case R.id.CariGambar:
                Intent iGambar = new Intent(this, CariGambarActivity.class);
                startActivity(iGambar);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyTask extends AsyncTask<Void,String,Void>{
        ArrayAdapter<String> adapter;
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        private int counter=1;

        public MyTask(MainActivity activity){
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading Data");
            dialog.setCancelable(false);
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Progress", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyTask.this.cancel(true);
                    adapter.clear();
                    dialog.dismiss();
                }
            });
            dialog.show();
            adapter = (ArrayAdapter<String>)listView.getAdapter();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String name: item){
                publishProgress(name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            Integer current_status = (int) ((counter/(float)item.length)*100);
            mProgressBar.setProgress(current_status);

            //set progress only for horizontal loading
            dialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            dialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

        }
    }
}
