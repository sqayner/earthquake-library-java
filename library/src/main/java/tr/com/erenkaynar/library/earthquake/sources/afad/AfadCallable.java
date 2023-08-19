package tr.com.erenkaynar.library.earthquake.sources.afad;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.Constants;
import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;
import tr.com.erenkaynar.library.earthquake.sources.EarthquakeAPICallable;

public class AfadCallable extends EarthquakeAPICallable {
    @Override
    public URL getUrl() throws Exception {
        return new URL(Constants.AFAD_URL);
    }

    @Override
    protected ArrayList<Earthquake> call() throws Exception {
        URLConnection connection = getUrl().openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder data = new StringBuilder();
        while ((line = reader.readLine()) != null) data.append(line);
        reader.close();

        return parse(data.toString());
    }

    private ArrayList<Earthquake> parse(String data) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        Document html = Jsoup.parse(data);
        Elements tbody = html.getElementsByTag("tbody");
        if (tbody.size() == 1) {
            Elements rows = tbody.get(0).getElementsByTag("tr");

            for (Element row : rows) {
                Elements datas = row.getElementsByTag("td");

                Earthquake earthquake = new Earthquake(Source.AFAD);
                earthquake.setId(datas.get(7).html());
                earthquake.setLocation(datas.get(6).html());
                earthquake.setMagnitude(Double.parseDouble(datas.get(5).html()));
                earthquake.setDepth(Double.parseDouble(datas.get(3).html()));

                LatLong latLong = new LatLong();
                latLong.setLatitude(Double.parseDouble(datas.get(1).html()));
                latLong.setLongitude(Double.parseDouble(datas.get(2).html()));
                earthquake.setCoordinates(latLong);

                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime ldt = LocalDateTime.parse(datas.get(0).html(), f);
                earthquake.setDatetime(ldt.toInstant(ZoneOffset.ofHours(3)));

                earthquakes.add(earthquake);
            }
        }
        return earthquakes;
    }
}
