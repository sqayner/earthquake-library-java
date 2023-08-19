package tr.com.erenkaynar.library.earthquake.utils;

public abstract class Callable<R> {

    protected final String TAG = this.getClass().getName();
    private float progressMaxValue;
    private float progressValue;
    private ProgressListener progressListener;

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    protected abstract R call() throws Exception;

    protected void incrementProgress(float increment) {
        progressValue += increment;
        progressListener.onProgress(progressValue / progressMaxValue);
    }

    protected void setProgressMaxValue(float maxValue) {
        progressMaxValue = maxValue;
    }
}
