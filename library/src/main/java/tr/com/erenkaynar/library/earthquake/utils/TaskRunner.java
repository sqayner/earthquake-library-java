package tr.com.erenkaynar.library.earthquake.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {

    private static final String TAG = "TaskRunner";
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        callable.setProgressListener(new ProgressListener() {
            @Override
            public void onProgress(float progress) {
                handler.post(() -> {
                    callback.onProgress(progress);
                });
            }
        });
        executor.execute(() -> {
            R result = null;
            try {
                result = callable.call();
            } catch (Exception e) {
                Log.e(TAG, "executeAsync: ", e);
            }
            R finalResult = result;
            handler.post(() -> {
                callback.onLoaded(finalResult);
            });
        });
    }
}