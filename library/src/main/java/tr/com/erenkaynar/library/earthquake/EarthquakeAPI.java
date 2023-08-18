package tr.com.erenkaynar.library.earthquake;

import tr.com.erenkaynar.library.earthquake.enums.Source;

public class EarthquakeAPI {

    private Source source = Source.KANDILLI;

    public void setSource(Source source) {
        this.source = source;
    }
}
