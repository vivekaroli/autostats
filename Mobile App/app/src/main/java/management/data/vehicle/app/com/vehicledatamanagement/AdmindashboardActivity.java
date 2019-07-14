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

public class AdmindashboardActivity extends AppCompatActivity {
    Button register,search, searchfine, editfine,logot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        register = (Button) findViewById(R.id.registernew);
        search = (Button) findViewById(R.id.searchvehicle);
        searchfine = (Button) findViewById(R.id.searchfine);
        editfine = (Button) findViewById(R.id.editfine);
      //  logot = (Button) findViewById(R.id.logout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customdashaction);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton logout2= (ImageButton)view.findViewById(R.id.action_bar_logout);

        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(AdmindashboardActivity.this, AdminloginActivity.class);
                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("loggedin", "no");
                editor.commit();
                admin.putExtra("logintype","admin");
                startActivity(admin);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this,RegisterActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this,SearchActivity.class));
            }
        });

        searchfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this,VehiclelistActivity.class));
            }
        });

        editfine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this,FinerateActivity.class));
            }
        });

   /*     logot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent admin = new Intent(AdmindashboardActivity.this, AdminloginActivity.class);
                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("loggedin", "no");
                editor.commit();
                admin.putExtra("logintype","admin");
                startActivity(admin);
                finish();
            }
        });*/
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

