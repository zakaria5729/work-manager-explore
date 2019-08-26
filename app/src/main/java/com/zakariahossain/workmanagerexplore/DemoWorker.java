package com.zakariahossain.workmanagerexplore;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.zakariahossain.workmanagerexplore.MainActivity.KEY_COUNT_VALUE;

public class DemoWorker extends Worker {

    public static final String KEY_WORKER_VALUE = "key_worker_count";

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        int inputData = getInputData().getInt(KEY_COUNT_VALUE, 0);

        for (int i = 0; i < inputData; i++) {
            Log.i("MyTag", "Count is = " + i);
        }

        Data dataToSend = new Data.Builder()
                .putString(KEY_WORKER_VALUE, "Task done successfully " + inputData)
                .build();

        return Result.success(dataToSend);
    }
}
