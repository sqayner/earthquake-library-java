package tr.com.erenkaynar.library.earthquake.sources.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;
import tr.com.erenkaynar.library.earthquake.models.Revised;

public class KandilliParser extends Parser {
    @Override
    public ArrayList<Earthquake> parse(String data) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        Document document = Jsoup.parse(data);
        Elements pre = document.getElementsByTag("pre");
        String[] lines = pre.html().split("\n");

        for (int i = 6; i < lines.length; i++) {
            String earthquakeString = lines[i];

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

    private int arrayFirstIndexOf(String[] array, String index) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].contains(index))
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
