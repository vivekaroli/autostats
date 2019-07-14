package management.data.vehicle.app.com.vehicledatamanagement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionDetector {
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        if (networkConnectivity()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL(
                        "http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000);
                urlc.setReadTimeout(4000);

                    urlc.connect();

                // networkcode2 = urlc.getResponseCode();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                System.out.println("io");
                return (false);
            }
        } else
            return false;

    }

    private boolean networkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
