package tr.com.erenkaynar.library.earthquake.utils;

public interface Callback<R> {

    void onLoaded(R data);

    void onError(Exception e);

    void onProgress(float progress);
}
