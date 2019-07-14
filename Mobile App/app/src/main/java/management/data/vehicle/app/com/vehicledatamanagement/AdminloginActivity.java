package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AdminloginActivity extends AppCompatActivity {
String usertype;
    Button login;
    Boolean valid;
    EditText username,password;
    ProgressBar prg;
    TextView pagetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        usertype = getIntent().getExtras().getString("logintype");
        login = (Button) findViewById(R.id.userlogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        prg = (ProgressBar) findViewById(R.id.progressBar);
        pagetitle = (TextView) findViewById(R.id.pagetitle);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminloginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        username.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if(usertype.indexOf("admin")>-1) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setHint("Password");
            username.setHint("Username");
            pagetitle.setText("Administrator Login");
            TextView textv = (TextView) view.findViewById(R.id.actiontitle);
            textv.setText("Admin Login");
        }
        else
        {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            password.setHint("Registered Email");
            password.setVisibility(View.GONE);
            username.setHint("Vehicle Number");
            login.setText("Check");
            username.setImeOptions(EditorInfo.IME_ACTION_DONE);
            pagetitle.setText("Check Fine");
            username.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            username.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            TextView textv = (TextView) view.findViewById(R.id.actiontitle);
            textv.setText("Check Fine");
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(usertype.indexOf("admin")>-1) {

                }
                else {
                    Boolean validatevehi = s.toString().matches("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
                    if (validatevehi == true) {
                        username.setBackgroundResource(R.drawable.edittext);
                        valid = true;
                    } else {
                        username.setBackgroundResource(R.drawable.edittexterror);
                        valid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(usertype.indexOf("admin")>-1) {

                }
                else
                {
                    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    Boolean validemail = s.toString().matches(EMAIL_PATTERN);
                    if (validemail == true) {
                        password.setBackgroundResource(R.drawable.edittext);
                        valid = true;
                    } else {
                        password.setBackgroundResource(R.drawable.edittexterror);
                        valid = true;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

             /*   if(!username.getText().toString().equals("")&&!password.getText().toString().equals(""))
                {*/

                    if(usertype.equals("admin")){
                        if(!username.getText().toString().equals("")&&!password.getText().toString().equals("")) {
                            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("loggedin", "admin");
                                editor.commit();
                                startActivity(new Intent(AdminloginActivity.this, AdmindashboardActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong Admin Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if(!username.getText().toString().equals("")) {
                            if (valid == true) {
                                if ((new ConnectionDetector(AdminloginActivity.this)).isConnectingToInternet()) {
                                    prg.setVisibility(View.VISIBLE);
                                    sendPostRequest(username.getText().toString());
                                } else {

                                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_SHORT).show();
                        }
                        }
             /*   }
                else
                {
                    Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void sendPostRequest(String username) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String vehicle_num = params[0];
                //String email = params[1];
                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

                HttpClient httpClient = new DefaultHttpClient();

                String url = getResources().getString(R.string.app_url)+"/userdetails.php";
                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost(url);


                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
//





                String veh = "'"+vehicle_num+"'";

             //   String em = "'"+email+"'";
                System.out.println("vehicle_num "+veh);
                BasicNameValuePair vehicleNumBasicNameValuePair = new BasicNameValuePair("vehicle_num",veh);
              //  BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair("email_id",em);
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(vehicleNumBasicNameValuePair);
                //nameValuePairList.add(emailBasicNameValuePair);
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

                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();




                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    Boolean status= false;
                    String message="";



                    if(json.getString("id").indexOf("null")>-1) {
                        prg.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Vehicle Number not Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {




                        prg.setVisibility(View.GONE);
                        Intent intent = new Intent(AdminloginActivity.this,UserActivity.class);
                        intent.putExtra("result",result);
                        startActivity(intent);
                        finish();



                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(username);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code here
            Intent intent = new Intent(AdminloginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
