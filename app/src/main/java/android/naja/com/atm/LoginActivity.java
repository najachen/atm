package android.naja.com.atm;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasswd;
    private String uid;
    private String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        SharedPreferences pref = getSharedPreferences("atm", MODE_PRIVATE);
        edUserid.setText(pref.getString("PREF_USERID", ""));
    }

    private void findViews() {
        edUserid = (EditText) findViewById(R.id.userid);
        edPasswd = (EditText) findViewById(R.id.password);
    }

    public void login(View v) {

        uid = edUserid.getText().toString();
        pw = edPasswd.getText().toString();
        String s = "http://j.snpy.org/atm/login?userid=" + uid + "&pw=" + pw;
        new LoginTask().execute(s);
    }

    public void cancel(View v) {

    }

    class LoginTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            int data = 0;
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                data = is.read();
                Log.d("RESULT", data + "");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(Integer data) {
            super.onPostExecute(data);
            if (data == 49) {
                SharedPreferences pref = getSharedPreferences("atm", MODE_PRIVATE);
                pref.edit()
                        .putString("PREF_USERID", uid)
                        .commit();
                Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_LONG).show();
                getIntent().putExtra("LOGIN_USERID", uid);
                getIntent().putExtra("LOGIN_PASSWD", pw);
                setResult(RESULT_OK, getIntent());
                finish();
            } else {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK", null)
                        .show();

            }
        }
    }
}