package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class SearchActivity extends AppCompatActivity {
    EditText searchvalue;
    Button searchbtn,personaldetails,addfine,submitfine,showfine;
    public TextView vehicle_numr,rc_num,rc_name,rc_addr1,rc_addr2,rc_dist,rc_state,rc_country,rc_pin,rc_phone,rc_email,vehicle_types,dateview;
    public RelativeLayout searchresult,addfinelayout,addfinebtn, fimedetailslayout;
    Handler handler;
    private TaskCanceler taskCanceler;
    LinearLayout personalbutton;
    String finetyp,vehicleid;
    ListView lst;
    ArrayList<HashMap<String,String>> arraylst;
    Boolean resultfound=false,valid=false;
    ProgressBar progress;
    Spinner finetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view =getSupportActionBar().getCustomView();
        arraylst = new ArrayList<HashMap<String,String>>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textv = (TextView) view.findViewById(R.id.actiontitle);
        textv.setText("Search Vehicle");
        progress = (ProgressBar) findViewById(R.id.progressBar);
        searchresult = (RelativeLayout) findViewById(R.id.searchresult);
        addfinelayout =  (RelativeLayout) findViewById(R.id.addfinelayout);
        addfinebtn =  (RelativeLayout) findViewById(R.id.addfinebutton);
        fimedetailslayout = (RelativeLayout) findViewById(R.id.finedetabutton);
        personalbutton = (LinearLayout) findViewById(R.id.personalbutton);
        vehicle_types = (TextView) findViewById(R.id.vehicletypeshow);
        vehicle_numr = (TextView) findViewById(R.id.vehiclenumshow);

        rc_name = (TextView) findViewById(R.id.rcname);
        rc_addr1 = (TextView) findViewById(R.id.addr1);
       /* rc_addr2 = (TextView) findViewById(R.id.addr2);
        rc_dist = (TextView) findViewById(R.id.distname);
        rc_state = (TextView) findViewById(R.id.statename);
        rc_country = (TextView) findViewById(R.id.countryname);
        rc_pin = (TextView) findViewById(R.id.pinnum);*/
        rc_phone = (TextView) findViewById(R.id.phnenum);
        rc_email = (TextView) findViewById(R.id.emailid);
        searchvalue = (EditText) findViewById(R.id.vehiclenum);

        searchvalue.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        searchvalue.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Boolean validatevehi = s.toString().matches("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
                if(validatevehi==true)
                {
                    searchvalue.setBackgroundResource(R.drawable.edittext);
                    valid = true;

                }
                else
                {
                    searchvalue.setBackgroundResource(R.drawable.edittexterror);
                    valid=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        personalbutton.setVisibility(View.GONE);
        searchbtn = (Button) findViewById(R.id.search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
if(valid==true) {
    if((new ConnectionDetector(SearchActivity.this)).isConnectingToInternet()) {
        progress.setVisibility(View.VISIBLE);


        sendPostRequest(searchvalue.getText().toString());
    }
    else
    {
        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }
}
else
{
    Toast.makeText(getApplicationContext(), "Vehicle number is not valid", Toast.LENGTH_SHORT).show();
}
            }
        });

        personaldetails = (Button) findViewById(R.id.personaldetails);
        personaldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personaldetails.setBackgroundResource(R.drawable.accordionbutton);
                addfine.setBackgroundResource(R.drawable.inactive);
                showfine.setBackgroundResource(R.drawable.inactive);
                    searchresult.setVisibility(View.VISIBLE);
                    addfinebtn.setVisibility(View.GONE);
                fimedetailslayout.setVisibility(View.GONE);

            }
        });

        addfine = (Button) findViewById(R.id.addfine);
        addfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personaldetails.setBackgroundResource(R.drawable.inactive);
                addfine.setBackgroundResource(R.drawable.accordionbutton);
                showfine.setBackgroundResource(R.drawable.inactive);
                    addfinebtn.setVisibility(View.VISIBLE);
                fimedetailslayout.setVisibility(View.GONE);
                    searchresult.setVisibility(View.GONE);

            }
        });
        showfine = (Button) findViewById(R.id.finedetails);
        showfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personaldetails.setBackgroundResource(R.drawable.inactive);
                addfine.setBackgroundResource(R.drawable.inactive);
                showfine.setBackgroundResource(R.drawable.accordionbutton);
                arraylst.clear();
                addfinebtn.setVisibility(View.GONE);
                searchresult.setVisibility(View.GONE);
                fimedetailslayout.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                sendfinePostRequest(searchvalue.getText().toString());
            }
        });
        submitfine = (Button) findViewById(R.id.addf);
        submitfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                submitPostRequest(vehicleid,finetyp);

            }
        });

         finetype = (Spinner) findViewById(R.id.finetype_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(this,
                R.array.finetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        finetype.setAdapter(adptrtype);

        finetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finetyp = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateview  =(TextView) findViewById(R.id.finedate);
        dateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descendingsort();
                lst = (ListView) findViewById(R.id.list);
                ListAdapter lstadapt = new SimpleAdapter(SearchActivity.this,arraylst,R.layout.custom,new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                lst.setAdapter(lstadapt);
            }
        });
    }
    private void sendPostRequest(String vehicle_num) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String vehicle_num = params[0];

                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();
                String url = getResources().getString(R.string.app_url)+"/Select_Vehicle_PHP.php";

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
                System.out.println("vehicle_num "+nameValuePairList);


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



                    if(json.getString("id").indexOf("null")>-1) {
                        Toast.makeText(getApplicationContext(), "Vehicle number not found", Toast.LENGTH_SHORT).show();
                        searchresult.setVisibility(View.GONE);
                        personalbutton.setVisibility(View.GONE);
                        addfinebtn.setVisibility(View.GONE);
                        addfinelayout.setVisibility(View.GONE);
                    }
                    else
                    {

                        vehicleid = json.getString("id");
                        vehicle_types.setText(json.getString("vehicle_type"));
                        if(json.getString("vehicle_type").indexOf("2 Wheeler")>-1)
                        {
                            ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(SearchActivity.this,
                                    R.array.twofinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                            adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                            finetype.setAdapter(adptrtype);
                        }
                        else  if(json.getString("vehicle_type").indexOf("3 Wheeler")>-1)
                        {
                            ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(SearchActivity.this,
                                    R.array.threefinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                            adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                            finetype.setAdapter(adptrtype);
                        }
                        else if(json.getString("vehicle_type").indexOf("Light Motor Vehicle")>-1)
                        {
                            ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(SearchActivity.this,
                                    R.array.lmvfinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                            adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                            finetype.setAdapter(adptrtype);
                        }
                        else
                        {
                            ArrayAdapter<CharSequence> adptrtype = ArrayAdapter.createFromResource(SearchActivity.this,
                                    R.array.hmvfinetype_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                            adptrtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                            finetype.setAdapter(adptrtype);
                        }
                        vehicle_numr.setText(json.getString("vehicle_num"));
                        rc_name.setText(json.getString("rc_name").substring(0, 1).toUpperCase()+json.getString("rc_name").substring(1).toLowerCase());
                        String newline = System.getProperty("line.separator");
                        String str3 = "First Line \n this is Second Line";
                        System.out.println("address_2"+json.getString("address_2"));
                        String str = json.getString("address_1").substring(0, 1).toUpperCase()+json.getString("address_1").substring(1).toLowerCase()+" "+json.getString("address_2")+'\n'+json.getString("district").substring(0, 1).toUpperCase()+json.getString("district").substring(1).toLowerCase()+"\n"+json.getString("state").substring(0, 1).toUpperCase()+json.getString("state").substring(1).toLowerCase()+"\n"+json.getString("country").substring(0, 1).toUpperCase()+json.getString("country").substring(1).toLowerCase()+"-"+json.getString("pin_code");
                        rc_addr1.setSingleLine(false);
                        rc_addr1.setText(str);
                 /*       rc_addr2.setText(json.getString("address_2"));
                        rc_dist.setText(json.getString("district"));
                        rc_state.setText(json.getString("state"));
                        rc_country.setText(json.getString("country"));
                        rc_pin.setText(json.getString("pin_code"));*/
                        rc_phone.setText(json.getString("phone_num"));
                        rc_email.setText(json.getString("email_id"));


                        personalbutton.setVisibility(View.VISIBLE);

                        searchresult.setVisibility(View.VISIBLE);
                        addfinebtn.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehicle_num);
    }

    private void submitPostRequest(String vehicle_id,String finetype) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String vehicle_id = params[0];
                String finetype = params[1];

                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();

                String url = getResources().getString(R.string.app_url)+"/finedatainsert.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);

                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//


                String veh = vehicle_id;
                Date date = new Date();
                BasicNameValuePair vehicleNumBasicNameValuePair = new BasicNameValuePair("vehicle_id",veh);
                BasicNameValuePair finetypeBasicNameValuePair = new BasicNameValuePair("fine_type","'"+finetype+"'");
                BasicNameValuePair dateBasicNameValuePair = new BasicNameValuePair("dateTime", new Long(date.getTime()).toString());
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(vehicleNumBasicNameValuePair);
                nameValuePairList.add(finetypeBasicNameValuePair);
                nameValuePairList.add(dateBasicNameValuePair);

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

                    if(status==true){
                        message = json.get("message").toString();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        message = json.get("message").toString();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehicle_id,finetype);
    }

    private void sendfinePostRequest(String vehcilenum) {

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

                progress.setVisibility(View.GONE);
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
                        ListAdapter lstadapt = new SimpleAdapter(SearchActivity.this, arraylst, R.layout.custom, new String[]{"vehicle_num","fine_type","fine_charge","fine_date"},new int[]{R.id.vehiclenumdata,R.id.finetypedata,R.id.finechargelabel,R.id.finedate});
                        lst.setAdapter(lstadapt);
                        progress.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(vehcilenum);
    }

    public void descendingsort()
    {
        ArrayList<HashMap<String,String>> temparray = new ArrayList<HashMap<String,String>>();
        int k = 0;
        for(int j=arraylst.size()-1;j>=0;j--)
        {
            temparray.add(arraylst.get(j));
        }
        arraylst.clear();
        arraylst = temparray;
    }
}
