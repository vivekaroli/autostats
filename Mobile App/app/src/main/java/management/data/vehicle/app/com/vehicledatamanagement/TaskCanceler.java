package management.data.vehicle.app.com.vehicledatamanagement;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskCanceler implements Runnable{
    private AsyncTask task;
    private Context context;

    public TaskCanceler(AsyncTask task,Context context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void run() {
        if (task.getStatus() == AsyncTask.Status.RUNNING )
            task.cancel(true);
        Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
    }
}