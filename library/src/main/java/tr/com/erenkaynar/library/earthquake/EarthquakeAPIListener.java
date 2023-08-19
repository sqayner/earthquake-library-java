package tr.com.erenkaynar.library.earthquake;

import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.models.Earthquake;

public interface EarthquakeAPIListener {

    void onLoaded(ArrayList<Earthquake> earthquakes);

    void onError(Exception e);
}
