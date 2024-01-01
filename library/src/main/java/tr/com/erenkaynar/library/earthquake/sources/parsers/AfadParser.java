package tr.com.erenkaynar.library.earthquake.sources.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.models.LatLong;

public class AfadParser extends Parser {

    @Override
    public ArrayList<Earthquake> parse(String data) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {

                Earthquake earthquake = parseJsonObject(array.getJSONObject(i));
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            Log.e("AfadParser", "parse: ", e);
        }

        return earthquakes;
    }

    private Earthquake parseJsonObject(JSONObject object) throws JSONException {
        final Earthquake earthquake = new Earthquake(Source.AFAD);

        earthquake.setId(object.getString("eventID"));
        earthquake.setDepth(Double.parseDouble(object.getString("depth")));

        LatLong latLong = new LatLong();
        latLong.setLatitude(Double.parseDouble(object.getString("latitude")));
        latLong.setLongitude(Double.parseDouble(object.getString("longitude")));
        earthquake.setCoordinates(latLong);

        earthquake.setMagnitude(Double.parseDouble(object.getString("magnitude")));
        earthquake.setLocation(object.getString("location"));

        earthquake.setDatetime(Instant.parse(object.getString("date") + ".000Z"));

        earthquake.setRevised(null);
        return earthquake;
    }
}