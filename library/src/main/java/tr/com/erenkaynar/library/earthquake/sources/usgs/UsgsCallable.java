package tr.com.erenkaynar.library.earthquake.sources.usgs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.Constants;
import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;
import tr.com.erenkaynar.library.earthquake.sources.EarthquakeAPICallable;

public class UsgsCallable extends EarthquakeAPICallable {

    @Override
    protected ArrayList<Earthquake> call() throws Exception {
        URLConnection connection = getUrl().openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) lines.add(line);
        reader.close();
        lines.remove(0);

        return parse(lines);
    }

    private ArrayList<Earthquake> parse(ArrayList<String> lines) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        for (String line : lines) {
            Earthquake earthquake = parseLine(line);
            earthquakes.add(earthquake);
        }

        return earthquakes;
    }

    private Earthquake parseLine(String line) {
        final Earthquake earthquake = new Earthquake(Source.USGS);

        String[] values = line.split("\\|");

        earthquake.setId(values[0]);
        earthquake.setDepth(Double.parseDouble(values[4]));

        LatLong latLong = new LatLong();
        latLong.setLatitude(Double.parseDouble(values[2]));
        latLong.setLongitude(Double.parseDouble(values[3]));
        earthquake.setCoordinates(latLong);

        earthquake.setMagnitude(Double.parseDouble(values[10]));
        earthquake.setLocation(values[12]);

        earthquake.setDatetime(Instant.parse(values[1] + "Z"));

        earthquake.setRevised(null);
        return earthquake;
    }

    @Override
    public URL getUrl() throws Exception {
        return new URL(Constants.USGS_URL);
    }
}
