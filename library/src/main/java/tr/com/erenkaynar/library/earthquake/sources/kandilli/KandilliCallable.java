package tr.com.erenkaynar.library.earthquake.sources.kandilli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import tr.com.erenkaynar.library.earthquake.Constants;
import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;
import tr.com.erenkaynar.library.earthquake.models.Revised;
import tr.com.erenkaynar.library.earthquake.sources.EarthquakeAPICallable;

public class KandilliCallable extends EarthquakeAPICallable {
    @Override
    public URL getUrl() throws Exception {
        return new URL(Constants.KANDILLI_URL);
    }

    @Override
    protected ArrayList<Earthquake> call() throws Exception {
        URLConnection connection = getUrl().openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("windows-1254")));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null)
            lines.add(line);
        reader.close();

        return parse(lines);
    }

    private ArrayList<Earthquake> parse(ArrayList<String> lines) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        for (int i = arrayFirstIndexOf(lines, "<pre>") + 7; i < arrayFirstIndexOf(lines, "</pre>") - 1; i++) {
            String earthquakeString = lines.get(i);

            Earthquake earthquake = new Earthquake(Source.KANDILLI);

            earthquake.setRevised(getRevised(earthquakeString));
            earthquake.setDatetime(getDateTime(earthquakeString));
            earthquake.setDepth(Double.parseDouble(earthquakeString.substring(41, 49).trim()));
            earthquake.setMagnitude(getMagnitude(earthquakeString));
            earthquake.setLocation(earthquakeString.substring(71, 121).trim());
            earthquake.setCoordinates(new LatLong(
                    Double.parseDouble(earthquakeString.substring(21, 28).trim()),
                    Double.parseDouble(earthquakeString.substring(31, 38).trim())));

            earthquakes.add(earthquake);
        }
        return earthquakes;
    }

    private Revised getRevised(String data) {
        String revised = data.substring(121).trim();

        if (revised.contains("İlksel"))
            return null;

        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
        date.set(Calendar.MONTH, Integer.parseInt(data.substring(5, 7)) - 1);
        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.substring(8, 10)));
        date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(data.substring(11, 13)));
        date.set(Calendar.MINUTE, Integer.parseInt(data.substring(14, 16)));
        date.set(Calendar.SECOND, Integer.parseInt(data.substring(17, 19)));
        date.add(Calendar.HOUR, -3);
        return new Revised(Integer.parseInt(revised.substring(7, 8)), date.toInstant());
    }

    private int arrayFirstIndexOf(List<String> array, String index) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).contains(index))
                return i;
        }
        return -1;
    }

    private Instant getDateTime(String data) {
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
        date.set(Calendar.MONTH, Integer.parseInt(data.substring(5, 7)) - 1);
        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.substring(8, 10)));
        date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(data.substring(11, 13)));
        date.set(Calendar.MINUTE, Integer.parseInt(data.substring(14, 16)));
        date.set(Calendar.SECOND, Integer.parseInt(data.substring(17, 19)));
        date.add(Calendar.HOUR, -3);
        return date.toInstant();
    }

    private Double getMagnitude(String data) {
        String MD = data.substring(55, 58);
        String ML = data.substring(60, 63);
        String MW = data.substring(65, 68);
        if (!MW.equals("-.-"))
            return Double.parseDouble(MW);
        if (!ML.equals("-.-"))
            return Double.parseDouble(ML);
        if (!MD.equals("-.-"))
            return Double.parseDouble(MD);
        return null;
    }
}
