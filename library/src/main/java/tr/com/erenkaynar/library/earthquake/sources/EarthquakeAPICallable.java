package tr.com.erenkaynar.library.earthquake.sources;

import java.net.URL;
import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.utils.Callable;

public abstract class EarthquakeAPICallable extends Callable<ArrayList<Earthquake>> {

    public abstract URL getUrl() throws Exception;
}
