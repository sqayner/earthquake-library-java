package tr.com.erenkaynar.library.earthquake;

import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.sources.afad.AfadCallable;
import tr.com.erenkaynar.library.earthquake.sources.kandilli.KandilliCallable;
import tr.com.erenkaynar.library.earthquake.sources.usgs.UsgsCallable;
import tr.com.erenkaynar.library.earthquake.utils.Callback;
import tr.com.erenkaynar.library.earthquake.utils.TaskRunner;

public class EarthquakeAPI implements Callback<ArrayList<Earthquake>> {

    private Source source = Source.KANDILLI;
    private EarthquakeAPIListener earthquakeAPIListener;
    private TaskRunner taskRunner;

    public EarthquakeAPI() {
        taskRunner = new TaskRunner();
    }

    public static EarthquakeAPI initialize() {
        return new EarthquakeAPI();
    }

    public EarthquakeAPI setEarthquakeAPIListener(EarthquakeAPIListener earthquakeAPIListener) {
        this.earthquakeAPIListener = earthquakeAPIListener;
        return this;
    }

    public EarthquakeAPI setSource(Source source) {
        this.source = source;
        return this;
    }

    public void load() {
        switch (source) {
            case KANDILLI:
                taskRunner.executeAsync(new KandilliCallable(), this);
                break;
            case AFAD:
                taskRunner.executeAsync(new AfadCallable(), this);
                break;
            case USGS:
                taskRunner.executeAsync(new UsgsCallable(), this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaded(ArrayList<Earthquake> data) {
        if (earthquakeAPIListener != null)
            earthquakeAPIListener.onLoaded(data);
    }

    @Override
    public void onError(Exception e) {
        if (earthquakeAPIListener != null)
            earthquakeAPIListener.onError(e);
    }

    @Override
    public void onProgress(float progress) {

    }
}
