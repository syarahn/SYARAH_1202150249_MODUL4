package com.example.gl552vw.syarah_1202150249_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class CariGambarActivity extends AppCompatActivity {
    private EditText inputUrl;
    private Button btn_gambar;
    private ImageView imgView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        //reference
        inputUrl = (EditText) findViewById(R.id.urlGambar);
        btn_gambar = (Button) findViewById(R.id.btn_gambar);
        imgView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        btn_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = inputUrl.getText().toString();
                new asyncGambar(CariGambarActivity.this).execute(url);
            }
        });
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

    public class asyncGambar extends AsyncTask<String, String, Bitmap>{

        ProgressDialog dialog;

        public asyncGambar(CariGambarActivity activity){
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
                public void onClick(DialogInterface dialogInterface, int i) {
                    asyncGambar.this.cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try{
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            }catch (IOException e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgView.setImageBitmap(bitmap);
            dialog.dismiss();

        }
    }
}
