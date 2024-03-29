package com.anshulkhantwal.assignment5;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainActivity extends AppCompatActivity {

    TextView webcontent;
    AppCompatTextView title;
    String content_data,title_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webcontent = (TextView) findViewById(R.id.web_content);
        title = (AppCompatTextView)findViewById(R.id.title);
        //webview = (WebView)findViewById(R.id.webview);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(savedInstanceState==null){
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadFromWeb().execute(new String[]{
                        "https://www.iiitd.ac.in/about"
                });
            } else {
                title_data="Couldn't Download the data";
                content_data="Please make sure that you are connected to Internet.";
                title.setText("Couldn't Download the data");
                webcontent.setText("Please make sure that you are connected to Internet.");
            }
        }
    }

    private class DownloadFromWeb extends AsyncTask<String, Void, String>{

        ProgressDialog progressdialog;
        Document page;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(MainActivity.this);
            progressdialog.setMessage("Downloading the content....");
            progressdialog.show();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected String doInBackground(String... urls) {
            try {
                page = Jsoup.connect(urls[0]).get();
                title_data = page.title();
                content_data = Html.fromHtml(page.select("p").eq(6).html()).toString() + "\n\n" + Html.fromHtml(page.select("p").eq(7).html()).toString();
                Thread.sleep(2000);
                return content_data;

            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            title.setText(title_data);
            webcontent.setText(result);
            Log.i("Anshul_Assignment5",result);
            System.out.print(page.html()+"\n</html>");
            progressdialog.dismiss();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title_data = savedInstanceState.getString("Title");
        content_data = savedInstanceState.getString("Content");
        title.setText(title_data);
        webcontent.setText(content_data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",title_data);
        outState.putString("Content",content_data);
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
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}