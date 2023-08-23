package tr.com.erenkaynar.library.earthquake.enums;

public enum Source {
    KANDILLI(
            "Kandilli Rasathanesi ve Deprem Araştırma Enstitüsü",
            "http://www.koeri.boun.edu.tr/scripts/sondepremler.asp"
    ),
    AFAD(
            "Afet ve Acil Durum Yönetimi Başkanlığı",
            "https://deprem.afad.gov.tr/last-earthquakes.html"
    ),
    USGS(
            "ABD Jeoloji Araştırmaları Kurumu (United States Geological Survey)",
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=text&minlongitude=25&maxlongitude=46&minlatitude=35&maxlatitude=43&limit=100"
    ),
    EMSC(
            "Avrupa-Akdeniz Sismoloji Merkezi (European-Mediterranean Seismological Centre)",
            "https://www.seismicportal.eu/fdsnws/event/1/query?contributor=EMSC&limit=100&minlat=35&maxlat=43&minlon=25&maxlon=46&format=text&mindepth=0&minmag=0"
    );

    private final String name;
    private final String url;

    Source(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
