package tr.com.erenkaynar.library.earthquake.models;

import java.io.Serializable;
import java.time.Instant;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.utils.MD5;

public class Earthquake implements Serializable {

    private Source source;
    private String id;
    private String location;
    private LatLong coordinates;
    private Double magnitude;
    private Double depth;
    private Instant datetime;
    private Revised revised;

    public Earthquake(Source source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LatLong getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLong coordinates) {
        this.coordinates = coordinates;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Revised getRevised() {
        return revised;
    }

    public void setRevised(Revised revised) {
        this.revised = revised;
    }

    public String getHash() {
        if (source == Source.KANDILLI)
            return "kandilli-" + MD5.encode((magnitude == null ? "" : magnitude) + "," + (coordinates == null ? "" : +coordinates.getLatitude()) + "," + (coordinates == null ? "" : +coordinates.getLongitude()) + "," + (depth == null ? "" : +depth) + "," + (datetime == null ? "" : +datetime.getEpochSecond()) + "," + (location == null ? "" : location));
        if (source == Source.AFAD) return "afad-" + MD5.encode(id);
        if (source == Source.USGS) return "usgs-" + MD5.encode(id);
        if (source == Source.EMSC) return "emsc-" + MD5.encode(id);

        return null;
    }
}
