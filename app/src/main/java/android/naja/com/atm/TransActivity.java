package android.naja.com.atm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {

    private ListView listView;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        findViews();
        String url = "http://atm201605.appspot.com/h";
//        new TransTask().execute(url);
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("JSON", json);
                parseJSON(json);
            }
        });


    }

    private void parseJSON(String json) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i<array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String account = obj.getString("account");
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");
                Log.d("DATA", account +"/" + date +"/" + amount +"/" + type);
                Map<String, String> row = new HashMap<>();
                row.put("account", account);
                row.put("date", date);
                row.put("amount", amount+"");
                row.put("type", type+"");
                data.add(row);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final SimpleAdapter adapter = new SimpleAdapter(
                TransActivity.this,
                data,
                R.layout.trans_row,
                new String[] {"account", "date", "amount", "type"},
                new int[] {R.id.account, R.id.date, R.id.amount, R.id.type}
        );
        //使用runOnUiThread方法，使資料可以顯示到屬於主UI執行緒上的listview
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             listView.setAdapter(adapter);
            }
        });
    }

    private void findViews() {
        listView = (ListView) findViewById(R.id.listView);
    }

    //以下為使用AsyncTask將連網功能切出主UI執行緒的程式碼
    class TransTask extends AsyncTask <String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while (line != null) {
                    sb.append(line);
                    line = in.readLine();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        //連線後進行資料處裡使用onPostExecute方法，以便能將資料解析於主UI執行緒
        @Override
        protected void onPostExecute(String s) {
            Log.d("DATA", s);
            List<Map<String, String>> data = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i<array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String account = obj.getString("account");
                    String date = obj.getString("date");
                    int amount = obj.getInt("amount");
                    int type = obj.getInt("type");
                    Log.d("DATA", account +"/" + date +"/" + amount +"/" + type);
                    Map<String, String> row = new HashMap<>();
                    row.put("account", account);
                    row.put("date", date);
                    row.put("amount", amount+"");
                    row.put("type", type+"");
                    data.add(row);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            SimpleAdapter adapter = new SimpleAdapter(
                    TransActivity.this,
                    data,
                    R.layout.trans_row,
                    new String[] {"account", "date", "amount", "type"},
                    new int[] {R.id.account, R.id.date, R.id.amount, R.id.type}
            );
            listView.setAdapter(adapter);
        }
    }
}



