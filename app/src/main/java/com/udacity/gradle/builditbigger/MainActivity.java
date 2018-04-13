package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.karim.androidlibrary.JokeApp;

import java.io.IOException;




class Task extends AsyncTask<String, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    Listener listener = null;

    public interface Listener {
        void onReturn(String joke);
    }

    public Task(Context context) {

        this.context = context;
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            try {
                throw new Exception("error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }


        String name = params[0];

        try {
            return myApiService.sayHi(name).execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        listener.onReturn(joke);
    }
}

public class MainActivity extends AppCompatActivity implements Task.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonclick(View view) {

        new Task(this).execute("joke");

    }


    @Override
    public void onReturn(String joke) {
        Intent intent = new Intent(this, JokeApp.class);
        intent.putExtra("joke", joke);
        startActivity(intent);
    }


}
