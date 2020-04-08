package pam.bookshelf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener
, BookDetailsFragment.OnFragmentInteractionListener {
   public static String screen="";
    public static  FrameLayout frame_details;
    public static   double diagonalInches;
    public static List<Book> booksArray=new ArrayList<Book>();
    String book_id,author,title,cover_url;
    public static  MyListAdapter adapter;
    Book book=new Book();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame_details=findViewById(R.id.frame_details);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
         diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, new BookListFragment());
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onBackPressed() {
//        if (screen.equals("ph")) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_details);
        if (diagonalInches>=6.5 || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            super.onBackPressed();
        }
        else {
            if (f instanceof BookDetailsFragment) {
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }   else{
                super.onBackPressed();
            }

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

}
