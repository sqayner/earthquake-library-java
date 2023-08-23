package tr.com.erenkaynar.library.earthquake.sources.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;

public class AfadParser extends Parser {
    @Override
    public ArrayList<Earthquake> parse(String data) {
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