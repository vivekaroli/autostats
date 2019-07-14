package management.data.vehicle.app.com.vehicledatamanagement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button userlogin,rtologin;
    Intent user,admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view =getSupportActionBar().getCustomView();
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        String channel = sharedpreferences.getString("loggedin", "");
        if(channel.indexOf("admin")>-1)
        {
            startActivity(new Intent(MainActivity.this,AdmindashboardActivity.class));
            finish();
        }
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setVisibility(View.INVISIBLE);
        userlogin = (Button) findViewById(R.id.userlogin);
        rtologin = (Button) findViewById(R.id.rtologin);
        user = new Intent(MainActivity.this, AdminloginActivity.class);

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.putExtra("logintype","user");
                startActivity(user);
                finish();
            }
        });

        rtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin = new Intent(MainActivity.this, AdminloginActivity.class);
                admin.putExtra("logintype","admin");
                startActivity(admin);
                finish();
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
