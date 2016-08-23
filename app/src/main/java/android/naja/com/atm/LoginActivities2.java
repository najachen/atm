package android.naja.com.atm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/8/16.
 */
public class LoginActivities2 extends AppCompatActivity {


    private EditText edId;
    private EditText edPw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        SharedPreferences pref = getSharedPreferences("atm", MODE_PRIVATE);
        edId.setText(pref.getString("PREF_UID", ""));

    }

    private void findViews() {
        edId = (EditText) findViewById(R.id.userid);
        edPw = (EditText) findViewById(R.id.password);
    }


    public void Login (View v){


    }
    public void cancel (View v){

    }

}
