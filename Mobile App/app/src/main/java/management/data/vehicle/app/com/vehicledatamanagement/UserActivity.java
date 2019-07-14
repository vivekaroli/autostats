package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
String details;
    String Vehicletype,vehiclenumbr,name,address1,address2,district,state,country,pinc,email,phone;
    TextView vehicletyp,vehiclenum,nam,addr1,addr2,dist,stat,coun,pin,emai,phne,dateview;
    RelativeLayout searchresult,personalbutton,fineshow;
    ListView lst;
    ArrayList<HashMap<String,String>> arraylst;
    ProgressBar prg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        details = getIntent().getStringExtra("result");
        arraylst = new ArrayList<HashMap<String,String>>();
        prg = (ProgressBar) findViewById(R.id.progressBar);
        prg.setVisibility(View.VISIBLE);
       vehicletyp = (TextView) findViewById(R.id.vehtyp);
        vehiclenum = (TextView) findViewById(R.id.vehnum);


       /*  nam = (TextView) findViewById(R.id.rcname);
        addr1 = (TextView) findViewById(R.id.addr1);
        addr2 = (TextView) findViewById(R.id.addr2);
        dist = (TextView) findViewById(R.id.distname);
        stat = (TextView) findViewById(R.id.statename);
        coun = (TextView) findViewById(R.id.countryname);
        pin = (TextView) findViewById(R.id.pinnum);
        phne = (TextView) findViewById(R.id.phnenum);
        emai = (TextView) findViewById(R.id.emailid);*/
        dateview  =(TextView) findViewById(R.id.finedate);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view =getSupportActionBar().getCustomView();
        TextView textv = (TextView) view.findViewById(R.id.actiontitle);
        textv.setText("Fine List");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
       // searchresult = (RelativeLayout) findViewById(R.id.searchresult);
        //fineshow =  (RelativeLayout) findViewById(R.id.addfinebutton);
        //personalbutton = (RelativeLayout) findViewById(R.id.personalbutton);


        JSONObject json =null;
        try {
            json = new JSONObject(details);
           vehicletyp.setText(json.getString("vehicle_type"));
            vehiclenum.setText(json.getString("vehicle_num"));
          /*   nam.setText(json.getString("rc_name"));
            addr1.setText(json.getString("address_1"));
            addr2.setText(json.getString("address_2"));
            dist.setText(json.getString("district"));
            stat.setText(json.getString("state"));
            coun.setText(json.getString("country"));
            pin.setText(json.getString("pin_code"));
            phne.setText(json.getString("phone_num"));
            emai.setText(json.getString("email_id"));

            searchresult.setVisibility(View.VISIBLE);
            personalbutton.setVisibility(View.VISIBLE);
            fineshow.setVisibility(View.VISIBLE);*/

            if((new ConnectionDetector(UserActivity.this)).isConnectingToInternet()) {
                sendPostRequest(json.getString("vehicle_num"));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please Check Internett Connection", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            prg.setVisibility(View.GONE);
        }

        dateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descendingsort();
                lst = (ListView) findViewById(R.id.list);
                ListAdapter lstadapt = new SimpleAdapter(UserActivity.this, arraylst, R.layout.custom, new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                lst.setAdapter(lstadapt);
            }
        });
    }

    private void sendPostRequest(String vehcilenum) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String vehicle_num = params[0];

                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();
                String url = getResources().getString(R.string.app_url)+"/finedetails.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);

                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//





                String veh = "'"+vehicle_num+"'";


                System.out.println("vehicle_num "+veh);
                BasicNameValuePair vehicleNumBasicNameValuePair = new BasicNameValuePair("vehicle_num",veh);

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
//  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();


                JSONObject jsn = null;

                JSONArray json = null;

                    try {
                        jsn = new JSONObject(result);
                        String status= "";
                        String message="";




                        if(jsn.getString("status").indexOf("success")<=-1) {
                            Toast.makeText(getApplicationContext(), "No Fine details found", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            json = jsn.getJSONArray("array");

                            for (int i = 0; i < json.length(); i++) {
                                HashMap<String, String> hpj = new HashMap<String, String>();
                                hpj.put("vehicle_num", json.getJSONObject(i).getString("vehicle_num"));
                                hpj.put("fine_type", json.getJSONObject(i).getString("fine_type"));
                                hpj.put("fine_charge", json.getJSONObject(i).getString("fine_charge"));
                                if(json.getJSONObject(i).getString("datetime") != null && !json.getJSONObject(i).getString("datetime").isEmpty())if(json.getJSONObject(i).getString("datetime")!=null || json.getJSONObject(i).getString("datetime")!="") {
                                    Date date = new Date(Long.valueOf(json.getJSONObject(i).getString("datetime")));
                                    SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                                    String dateText = df2.format(date);

                                    hpj.put("fine_date", dateText);
                                }
                                else {
                                    hpj.put("fine_date", "");
                                }
                                arraylst.add(hpj);

                            }
                            descendingsort();
                            lst = (ListView) findViewById(R.id.list);
                            ListAdapter lstadapt = new SimpleAdapter(UserActivity.this, arraylst, R.layout.custom, new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                            lst.setAdapter(lstadapt);
                            prg.setVisibility(View.GONE);
                        }
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }




            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehcilenum);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code here
            Intent intent = new Intent(UserActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
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

 /*   public void ascendingsort()
    {
        ArrayList<HashMap<String,String>> temparray = new ArrayList<HashMap<String,String>>();
        int k = 0;
        for(int j=0;j<arraylst.size();j++)
        {
            temparray.add(arraylst.get(j));
        }
        arraylst = temparray;
    }*/
}




