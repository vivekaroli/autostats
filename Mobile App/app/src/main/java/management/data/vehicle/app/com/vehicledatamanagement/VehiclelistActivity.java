package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class VehiclelistActivity extends AppCompatActivity {
String vehicletype;
    Button search;
    ListView lst;
    ArrayList<HashMap<String,String>> arraylst;
    ProgressBar progress;
    TextView dateview;
    LinearLayout tabletopbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclelist);
        arraylst = new ArrayList<HashMap<String,String>>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view =getSupportActionBar().getCustomView();

        TextView textv = (TextView) view.findViewById(R.id.actiontitle);
        textv.setText("Search Fine");
        progress = (ProgressBar) findViewById(R.id.progressBar);
        tabletopbar = (LinearLayout) findViewById(R.id.tabletopbar) ;
tabletopbar.setVisibility(View.GONE);
        dateview  =(TextView) findViewById(R.id.finedate);
        dateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descendingsort();
                lst = (ListView) findViewById(R.id.list);
                ListAdapter lstadapt = new SimpleAdapter(VehiclelistActivity.this,arraylst,R.layout.custom,new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                lst.setAdapter(lstadapt);
            }
        });
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Spinner spinnertype = (Spinner) findViewById(R.id.vehicletype_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adaptertype = ArrayAdapter.createFromResource(this,
                R.array.vehicletype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnertype.setAdapter(adaptertype);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicletype = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((new ConnectionDetector(VehiclelistActivity.this)).isConnectingToInternet()) {
progress.setVisibility(View.VISIBLE);
                    arraylst.clear();
                    sendPostRequest(vehicletype);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
    private void sendPostRequest(String vehicletype) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String vehicle_type = params[0];

                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();

                String url = getResources().getString(R.string.app_url)+"/Select_FineList_PHP.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);

                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//


                String veh = "'"+vehicle_type+"'";
                System.out.println("vehicle_num "+veh);
                BasicNameValuePair vehicleNumBasicNameValuePair = new BasicNameValuePair("vehicle_type",veh);
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(vehicleNumBasicNameValuePair);



                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);


                    try {
                        // HttpResponse is an interface just like HttpPost.
                        //Therefore we can't initialize them
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        // According to the JAVA API, InputStream constructor do nothing.
                        //So we can't initialize InputStream although it is not an interface
                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk = null;

                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {
                        System.out.println("First Exception caz of HttpResponese :" + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out.println("Second Exception caz of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }
                }
                catch (UnsupportedEncodingException uee) {
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progress.setVisibility(View.GONE);

                JSONObject jsn = null;
                JSONArray json = null;

                try {
                    jsn = new JSONObject(result);
                  Boolean status= false;
                    String message="";



                    if(jsn.getString("status").indexOf("success")<=-1) {
                        Toast.makeText(getApplicationContext(), "No Fine details found", Toast.LENGTH_SHORT).show();
                        lst = (ListView) findViewById(R.id.list);
                        ListAdapter lstadapt = new SimpleAdapter(VehiclelistActivity.this,arraylst,R.layout.custom,new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                        lst.setAdapter(lstadapt);
                        tabletopbar.setVisibility(View.GONE);
                    }
                    else
                    {
                        tabletopbar.setVisibility(View.VISIBLE);
                        json = jsn.getJSONArray("array");

                        for(int i=0;i<json.length();i++){
    HashMap<String,String> hp = new HashMap<String,String>();
    hp.put("vehicle_num",json.getJSONObject(i).getString("vehicle_num"));

    hp.put("fine_type",json.getJSONObject(i).getString("fine_type"));
    hp.put("fine_charge",json.getJSONObject(i).getString("fine_charge"));

                            if(json.getJSONObject(i).getString("datetime") != null && !json.getJSONObject(i).getString("datetime").isEmpty())if(json.getJSONObject(i).getString("datetime")!=null || json.getJSONObject(i).getString("datetime")!="") {
                                Date date = new Date(Long.valueOf(json.getJSONObject(i).getString("datetime")));
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                                String dateText = df2.format(date);

                                hp.put("fine_date", dateText);
                            }
                            else {
                                hp.put("fine_date", "");
                            }
    arraylst.add(hp);
}
                        descendingsort();
                        lst = (ListView) findViewById(R.id.list);
                        ListAdapter lstadapt = new SimpleAdapter(VehiclelistActivity.this,arraylst,R.layout.custom,new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
lst.setAdapter(lstadapt);










                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehicletype);
    }
    public void descendingsort()
    {
        ArrayList<HashMap<String,String>> temparray = new ArrayList<HashMap<String,String>>();
        int k = 0;
        for(int j=arraylst.size()-1;j>=0;j--)
        {
            temparray.add(arraylst.get(j));
        }
        arraylst = temparray;
    }
}
