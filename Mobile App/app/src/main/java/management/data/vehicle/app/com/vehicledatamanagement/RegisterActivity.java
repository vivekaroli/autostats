package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity  {
    EditText vehicle_num,rc_num,rc_name,rc_addr1,rc_addr2,rc_dist,rc_state,rc_country,rc_pin,rc_phone,rc_email;
    Button register,reset;
    String statevalue,vehicletype;
    Boolean valid=false;
    ProgressBar progress;
     Handler handler;
    private TaskCanceler taskCanceler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customregisteraction);
        View view =getSupportActionBar().getCustomView();

        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton clear= (ImageButton)view.findViewById(R.id.action_bar_clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vehicle_num.setText("");
                rc_email.setText("");
                rc_name.setText("");
                rc_phone.setText("");
                rc_pin.setText("");
                rc_addr1.setText("");
                rc_addr2.setText("");
                rc_dist.setText("");

                vehicle_num.setBackgroundResource(R.drawable.edittext);
                rc_email.setBackgroundResource(R.drawable.edittext);
                rc_name.setBackgroundResource(R.drawable.edittext);
                rc_phone.setBackgroundResource(R.drawable.edittext);
                rc_pin.setBackgroundResource(R.drawable.edittext);
                rc_addr1.setBackgroundResource(R.drawable.edittext);
                rc_addr2.setBackgroundResource(R.drawable.edittext);
                rc_dist.setBackgroundResource(R.drawable.edittext);



            }
        });

        ImageButton save= (ImageButton)view.findViewById(R.id.action_bar_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


               valid=  validate();
                System.out.println("valid "+valid);
                if(valid==false)
                {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please check entered datas", Toast.LENGTH_LONG).show();

                }
                else
                {
                    if((new ConnectionDetector(RegisterActivity.this)).isConnectingToInternet()) {

                        sendPostRequest(vehicletype, vehicle_num.getText().toString(), rc_name.getText().toString(), rc_addr1.getText().toString(), rc_addr2.getText().toString(), rc_dist.getText().toString(), statevalue, "India", rc_pin.getText().toString(), rc_phone.getText().toString(), rc_email.getText().toString());
                    }
                    else
                    {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        progress = (ProgressBar) findViewById(R.id.progressBar);
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


        Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.india_states, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       statevalue =  parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        vehicle_num = (EditText) findViewById(R.id.vehiclenum);
        rc_name = (EditText) findViewById(R.id.rcname);
        rc_addr1 = (EditText) findViewById(R.id.addr1);
        rc_addr2 = (EditText) findViewById(R.id.addr2);
        rc_dist = (EditText) findViewById(R.id.distname);

        rc_pin = (EditText) findViewById(R.id.pinnum);
        rc_phone = (EditText) findViewById(R.id.phnenum);
        rc_email = (EditText) findViewById(R.id.emailaddress);

        vehicle_num.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        vehicle_num.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        vehicle_num.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_addr1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_addr2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_dist.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_pin.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_phone.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        rc_email.setImeOptions(EditorInfo.IME_ACTION_DONE);


        rc_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(rc_name.getText().toString().trim().isEmpty())
                {
                    valid=false;
                    rc_name.setBackgroundResource(R.drawable.edittexterror);
                }
                else
                {
                    valid=true;
                    rc_name.setBackgroundResource(R.drawable.edittext);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rc_addr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(rc_addr1.getText().toString().trim().isEmpty())
                {
                    valid=false;
                    rc_addr1.setBackgroundResource(R.drawable.edittexterror);
                }
                else
                {
                    valid=true;
                    rc_addr1.setBackgroundResource(R.drawable.edittext);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rc_dist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(rc_dist.getText().toString().trim().isEmpty())
                {
                    valid=false;
                    rc_dist.setBackgroundResource(R.drawable.edittexterror);
                }
                else
                {
                    valid=true;
                    rc_dist.setBackgroundResource(R.drawable.edittext);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        vehicle_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            Boolean validatevehi = s.toString().matches("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
                if(validatevehi==true)
                {
                    vehicle_num.setBackgroundResource(R.drawable.edittext);
                    valid = true;
                }
                else
                {
                    vehicle_num.setBackgroundResource(R.drawable.edittexterror);
                    valid=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rc_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
Boolean validpin  = s.toString().matches("^[1-9][0-9]{5}$");
                if(validpin==true)
                {
                    rc_pin.setBackgroundResource(R.drawable.edittext);
                    valid = true;
                }
                else
                {
                    rc_pin.setBackgroundResource(R.drawable.edittexterror);
                    valid=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rc_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Boolean validphone  = s.toString().matches("((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}");
                if(validphone==true)
                {
                    rc_phone.setBackgroundResource(R.drawable.edittext);
                    valid = true;
                }
                else
                {
                    rc_phone.setBackgroundResource(R.drawable.edittexterror);
                    valid=false;
                }            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rc_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String EMAIL_PATTERN =                        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                Boolean validemail  = s.toString().matches(EMAIL_PATTERN);
                if(validemail==true)
                {
                    rc_email.setBackgroundResource(R.drawable.edittext);
                    valid = true;
                }
                else
                {
                    rc_email.setBackgroundResource(R.drawable.edittexterror);
                    valid=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vehicle_num.setText("");
                rc_email.setText("");
                rc_name.setText("");
                rc_phone.setText("");
                rc_pin.setText("");
                rc_addr1.setText("");
                rc_addr2.setText("");
                rc_dist.setText("");
            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                progress.setVisibility(View.VISIBLE);
               valid = validate();
                System.out.println("ssjsjs "+valid);
                if(valid==false)
                {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please check entered datas", Toast.LENGTH_LONG).show();

                }
                else
                {
                    if((new ConnectionDetector(RegisterActivity.this)).isConnectingToInternet()) {

                        sendPostRequest(vehicletype, vehicle_num.getText().toString(), rc_name.getText().toString(), rc_addr1.getText().toString(), rc_addr2.getText().toString(), rc_dist.getText().toString(), statevalue, "India", rc_pin.getText().toString(), rc_phone.getText().toString(), rc_email.getText().toString());
                    }
                    else
                    {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public Boolean validate(){
        if(vehicle_num.getText().toString().trim().isEmpty()||!vehicle_num.getText().toString().matches("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$"))
        {
            valid=false;
            vehicle_num.setBackgroundResource(R.drawable.edittexterror);
        }
        else
        {
            valid=true;
            vehicle_num.setBackgroundResource(R.drawable.edittext);
            if(rc_name.getText().toString().trim().isEmpty())
            {
                valid=false;
                rc_name.setBackgroundResource(R.drawable.edittexterror);
            }
            else
            {
                valid=true;
                rc_name.setBackgroundResource(R.drawable.edittext);
                if(rc_addr1.getText().toString().trim().isEmpty())
                {
                    valid=false;
                    rc_addr1.setBackgroundResource(R.drawable.edittexterror);
                }
                else
                {
                    valid=true;
                    rc_addr1.setBackgroundResource(R.drawable.edittext);
                    if(rc_dist.getText().toString().trim().isEmpty())
                    {
                        valid=false;
                        rc_dist.setBackgroundResource(R.drawable.edittexterror);
                    }
                    else
                    {
                        valid=true;
                        rc_dist.setBackgroundResource(R.drawable.edittext);
                        if(rc_pin.getText().toString().trim().isEmpty()||!rc_pin.getText().toString().matches("^[1-9][0-9]{5}$"))
                        {
                            valid=false;
                            rc_pin.setBackgroundResource(R.drawable.edittexterror);
                        }
                        else
                        {
                            valid=true;
                            rc_pin.setBackgroundResource(R.drawable.edittext);
                            if(rc_email.getText().toString().trim().isEmpty()||!rc_email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
                            {
                                valid=false;
                                rc_email.setBackgroundResource(R.drawable.edittexterror);
                            }
                            else
                            {
                                valid=true;
                                rc_email.setBackgroundResource(R.drawable.edittext);

                                if(rc_phone.getText().toString().trim().isEmpty()||!rc_phone.getText().toString().matches("((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}"))
                                {
                                    valid=false;
                                    rc_phone.setBackgroundResource(R.drawable.edittexterror);
                                }
                                else
                                {
                                    valid=true;
                                    rc_phone.setBackgroundResource(R.drawable.edittext);
                                }
                            }
                        }
                    }
                }
            }
        }


        return valid;
    }

    SendPostReqAsyncTask sendPostReqAsyncTask;
    private void sendPostRequest(String vehicletype, String vehicle_num, String rc_name, String rc_addr1, String rc_addr2, String rc_dist, String rc_state, String rc_country, String rc_pin, String rc_phone, String rc_email) {



         sendPostReqAsyncTask = new SendPostReqAsyncTask();
        taskCanceler = new TaskCanceler(sendPostReqAsyncTask,RegisterActivity.this);
        handler = new Handler();
        handler.postDelayed(taskCanceler, 40*1000);
        sendPostReqAsyncTask.execute(vehicletype, vehicle_num, rc_name, rc_addr1, rc_addr2, rc_dist, rc_state, rc_country, rc_pin, rc_phone, rc_email);

    }
    private BroadcastReceiver networkStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            doSomethingOnNetworkChange(ni);
        }

        public void doSomethingOnNetworkChange(NetworkInfo ni) {
            if(sendPostReqAsyncTask!=null && sendPostReqAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING))
            {
                Toast.makeText(RegisterActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                sendPostReqAsyncTask.cancel(true);
                progress.setVisibility(View.GONE);
            }
        }
    };
    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String vehicle_type = params[0];
            String vehicle_num = params[1];
            String rc_name = params[2];
            String rc_addr1 = params[3];
            String rc_addr2 = params[4];
            String rc_dist = params[5];
            String rc_state = params[6];
            String rc_country = params[7];
            String rc_pin = params[8];
            String rc_phone = params[9];
            String rc_email = params[10];
            //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

            HttpClient httpClient = new DefaultHttpClient();

            String url = getResources().getString(R.string.app_url)+"/insert.php";
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
            BasicNameValuePair vehicleTypeBasicNameValuePair = new BasicNameValuePair("vehicle_type", "'"+vehicle_type+"'");
            BasicNameValuePair vehicleNumBasicNameValuePair = new BasicNameValuePair("vehicle_num", "'"+vehicle_num+"'");
            BasicNameValuePair rcnameBasicNameValuePAir = new BasicNameValuePair("rc_name", "'"+rc_name+"'");
            BasicNameValuePair addr1BasicNameValuePair = new BasicNameValuePair("address_1", "'"+rc_addr1+"'");
            BasicNameValuePair addr2BasicNameValuePAir = new BasicNameValuePair("address_2", "'"+rc_addr2+"'");
            BasicNameValuePair distBasicNameValuePair = new BasicNameValuePair("district", "'"+rc_dist+"'");
            BasicNameValuePair stateBasicNameValuePAir = new BasicNameValuePair("state", "'"+rc_state+"'");
            BasicNameValuePair countryBasicNameValuePair = new BasicNameValuePair("country", "'"+rc_country+"'");
            BasicNameValuePair phoneBasicNameValuePAir = new BasicNameValuePair("phone_num", "'"+rc_phone+"'");
            BasicNameValuePair pinBasicNameValuePAir = new BasicNameValuePair("pin_code", "'"+rc_pin+"'");
            BasicNameValuePair emailBasicNameValuePAir = new BasicNameValuePair("email_id", "'"+rc_email+"'");
            // We add the content that we want to pass with the POST request to as name-value pairs
            //Now we put those sending details to an ArrayList with type safe of NameValuePair
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(vehicleTypeBasicNameValuePair);
            nameValuePairList.add(vehicleNumBasicNameValuePair);
            nameValuePairList.add(rcnameBasicNameValuePAir);
            nameValuePairList.add(addr1BasicNameValuePair);
            nameValuePairList.add(addr2BasicNameValuePAir);
            nameValuePairList.add(distBasicNameValuePair);
            nameValuePairList.add(stateBasicNameValuePAir);
            nameValuePairList.add(countryBasicNameValuePair);
            nameValuePairList.add(phoneBasicNameValuePAir);
            nameValuePairList.add(pinBasicNameValuePAir);
            nameValuePairList.add(emailBasicNameValuePAir);
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
            progress.setVisibility(View.GONE);

            EditText vehicle_num = (EditText) findViewById(R.id.vehiclenum);

            EditText rc_name = (EditText) findViewById(R.id.rcname);
            EditText rc_addr1 = (EditText) findViewById(R.id.addr1);
            EditText rc_addr2 = (EditText) findViewById(R.id.addr2);
            EditText rc_dist = (EditText) findViewById(R.id.distname);


            EditText rc_pin = (EditText) findViewById(R.id.pinnum);
            EditText rc_phone = (EditText) findViewById(R.id.phnenum);
            EditText rc_email = (EditText) findViewById(R.id.emailaddress);



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
                    vehicle_num.setText("");

                    rc_name.setText("");
                    rc_addr1.setText("");
                    rc_addr2.setText("");
                    rc_dist.setText("");

                    rc_pin.setText("");
                    rc_phone.setText("");
                    rc_email.setText("");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,AdmindashboardActivity.class));
    finish();

                }else{
                    if(message.indexOf("Vehicle Number already registered")>-1)
                    {
                        vehicle_num.setText("");
                        vehicle_num.getParent().requestChildFocus(vehicle_num,vehicle_num);
                    }
                    if(message.indexOf("RC Number already registered")>-1)
                    {
                        rc_num.setText("");
                        rc_num.getParent().requestChildFocus(rc_num,rc_num);
                    }
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
