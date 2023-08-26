package tr.com.erenkaynar.library.earthquake.sources.parsers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;

public class TextParser extends Parser {

    private Source source;

    public TextParser(Source source) {
        this.source = source;
    }

    @Override
    public ArrayList<Earthquake> parse(String data) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        final String[] linesArr = data.split("\n");
        final ArrayList<String> lines = new ArrayList<>(Arrays.asList(linesArr));
        lines.remove(0);

        for (String line : lines) {
            Earthquake earthquake = parseLine(line);
            earthquakes.add(earthquake);
        }

        return earthquakes;
    }

    private Earthquake parseLine(String line) {
        final Earthquake earthquake = new Earthquake(source);

        String[] values = line.split("\\|");

        earthquake.setId(values[0]);
        earthquake.setDepth(Double.parseDouble(values[4]));

        LatLong latLong = new LatLong();
        latLong.setLatitude(Double.parseDouble(values[2]));
        latLong.setLongitude(Double.parseDouble(values[3]));
        earthquake.setCoordinates(latLong);

        earthquake.setMagnitude(Double.parseDouble(values[10]));
        earthquake.setLocation(values[12]);

        earthquake.setDatetime(Instant.parse(values[1] + (values[1].contains("Z") ? "" : "Z")));

        earthquake.setRevised(null);
        return earthquake;
    }
}