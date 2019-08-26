package com.zakariahossain.workmanagerexplore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static com.zakariahossain.workmanagerexplore.DemoWorker.KEY_WORKER_VALUE;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_COUNT_VALUE = "key_count";
    private OneTimeWorkRequest oneTimeWorkRequest;
    private PeriodicWorkRequest periodicWorkRequest;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        oneTimeWorkRequestInit();
        periodicWorkRequestInit();
    }

    private void oneTimeWorkRequestInit() {
        Data inputData = new Data.Builder()
                .putInt(KEY_COUNT_VALUE, 1800)
                .build();

        Constraints constraints = new Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(DemoWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if (workInfo != null) {
                    textView.setText(workInfo.getState().name());

                    if (workInfo.getState().isFinished()) {
                        String outputData = workInfo.getOutputData().getString(KEY_WORKER_VALUE);
                        Toast.makeText(MainActivity.this, outputData, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void fabClickedOnTimeWork(View view) {
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    public void fabClickedPeriodicWork(View view) {
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    private void periodicWorkRequestInit() {
        Data inputData = new Data.Builder()
                .putInt(KEY_COUNT_VALUE, 2000)
                .build();

        Constraints constraints = new Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        String u1 = new String();

        periodicWorkRequest = new PeriodicWorkRequest
                .Builder(DemoWorker.class, 15, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if (workInfo != null) {
                    textView.setText(workInfo.getState().name());

                    if (workInfo.getState().isFinished()) {
                        String outputData = workInfo.getOutputData().getString(KEY_WORKER_VALUE);
                        Toast.makeText(MainActivity.this, outputData, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void my(String sm) {
    }
}
