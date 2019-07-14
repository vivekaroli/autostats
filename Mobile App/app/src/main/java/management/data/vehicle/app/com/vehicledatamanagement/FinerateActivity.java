package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

public class FinerateActivity extends AppCompatActivity {
Button save;
    String vehicletype,finetyp,action,id;
    EditText charge;
    ProgressBar progress;
    Spinner finetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finerate);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view =getSupportActionBar().getCustomView();
        TextView textv = (TextView) view.findViewById(R.id.actiontitle);
        textv.setText("Edit Fine");
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Spinner spinnertype = (Spinner) findViewById(R.id.vehicletype_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adaptertype = ArrayAdapter.createFromResource(this,
                R.array.vehicletype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnertype.setAdapter(adaptertype);

         finetype = (Spinner) findViewById(R.id.finetype_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(this,
                R.array.finetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        finetype.setAdapter(adptrtype);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progress.setVisibility(View.VISIBLE);
                vehicletype = parent.getItemAtPosition(position).toString();
                if(vehicletype.indexOf("2 Wheeler")>-1)
                {
                    ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(FinerateActivity.this,
                            R.array.twofinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    finetype.setAdapter(adptrtype);
                }
                else  if(vehicletype.indexOf("3 Wheeler")>-1)
                {
                    ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(FinerateActivity.this,
                            R.array.threefinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    finetype.setAdapter(adptrtype);
                }
                else if(vehicletype.indexOf("Light Motor Vehicle")>-1)
                {
                    ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(FinerateActivity.this,
                            R.array.lmvfinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    finetype.setAdapter(adptrtype);
                }
                else
                {
                    ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(FinerateActivity.this,
                            R.array.hmvfinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    finetype.setAdapter(adptrtype);
                }
                if((new ConnectionDetector(FinerateActivity.this)).isConnectingToInternet()) {
                sendPostRequestforcharge(vehicletype,finetyp);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        finetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                finetyp = parent.getItemAtPosition(position).toString();

                if((new ConnectionDetector(FinerateActivity.this)).isConnectingToInternet()) {
                    progress.setVisibility(View.VISIBLE);
                    sendPostRequestforcharge(vehicletype,finetyp);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


  charge= (EditText) findViewById(R.id.finerate);
        save = (Button) findViewById(R.id.savefine);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charge.getText().toString().replaceAll(" ","").isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please fill the field", Toast.LENGTH_SHORT).show();
                }
                else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    if ((new ConnectionDetector(FinerateActivity.this)).isConnectingToInternet()) {
                        progress.setVisibility(View.VISIBLE);
                        sendPostRequest(charge.getText().toString(), vehicletype, finetyp, action);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendPostRequest(String rate, String vehicletype , String finetype, String action) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String rate = params[0];
                String vehicletype = params[1];
                String finetype = params[2];
                String actio = params[3];
                int a = Integer.parseInt(rate);
                rate = String.valueOf(a);
                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();


                String url = getResources().getString(R.string.app_url)+"/insertfine.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);
                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//
                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
                BasicNameValuePair fineBasicNameValuePair = new BasicNameValuePair("charge", "'"+rate+"'");
                BasicNameValuePair vehicleBasicNameValuePair = new BasicNameValuePair("vehicle_type", "'"+vehicletype+"'");
                BasicNameValuePair finetypeBasicNameValuePair = new BasicNameValuePair("fine_type", "'"+finetype+"'");
                BasicNameValuePair actioneBasicNameValuePair = new BasicNameValuePair("action", "'"+actio+"'");

                // We add the content that we want to pass with the POST request to as name-value pairs
                //Now we put those sending details to an ArrayList with type safe of NameValuePair
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(fineBasicNameValuePair);
                nameValuePairList.add(vehicleBasicNameValuePair);
                nameValuePairList.add(finetypeBasicNameValuePair);
                nameValuePairList.add(actioneBasicNameValuePair);
                if(actio=="update")
                {
                    BasicNameValuePair idBasicNameValuePair = new BasicNameValuePair("id", id);
                    nameValuePairList.add(idBasicNameValuePair);
                }
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




                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    Boolean status= false;
                    String message="";
                    try {
                        status = json.getBoolean("success");
                        message = json.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(status==true){

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();



                    }else{

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(rate,vehicletype, finetype,action);
    }

    private void sendPostRequestforcharge(String vehicletype , String finetype) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {


                String vehicletype = params[0];
                String finetype = params[1];
                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();

                String url = getResources().getString(R.string.app_url)+"/selectfinerate.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);


                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//
                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request

                BasicNameValuePair vehicleBasicNameValuePair = new BasicNameValuePair("vehicle_type", "'"+vehicletype+"'");
                BasicNameValuePair finetypeBasicNameValuePair = new BasicNameValuePair("fine_type", "'"+finetype+"'");

                // We add the content that we want to pass with the POST request to as name-value pairs
                //Now we put those sending details to an ArrayList with type safe of NameValuePair
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

                nameValuePairList.add(vehicleBasicNameValuePair);
                nameValuePairList.add(finetypeBasicNameValuePair);
                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    System.out.println("First :");
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
                        System.out.println("ppp :" + bufferedStrChunk);
                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            System.out.println("hhh :" + bufferedStrChunk);
                            stringBuilder.append(bufferedStrChunk);
                        }
                        System.out.println("First :" + stringBuilder.toString());
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

                System.out.println("result "+result);
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();




                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    String charg= "";
                    String message="";
                    try {
                        charg = json.getString("Charge");
                        if(charg.equals(""))
                        {
                            action = "insert";
charg="0";
                        }
                        else
                        {
                            action="update";
                            id=json.getString("id");
                        }
                        charge= (EditText) findViewById(R.id.finerate);
                        charge.setText(charg);
                        progress.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehicletype, finetype);
    }

}
