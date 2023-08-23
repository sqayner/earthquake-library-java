package tr.com.erenkaynar.library.earthquake.sources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;
import tr.com.erenkaynar.library.earthquake.sources.parsers.AfadParser;
import tr.com.erenkaynar.library.earthquake.sources.parsers.KandilliParser;
import tr.com.erenkaynar.library.earthquake.sources.parsers.Parser;
import tr.com.erenkaynar.library.earthquake.sources.parsers.TextParser;
import tr.com.erenkaynar.library.earthquake.utils.Callable;

public class EarthquakeAPICallable extends Callable<ArrayList<Earthquake>> {

    private Source source;
    private Parser parser;

    public EarthquakeAPICallable(Source source) {
        this.source = source;
    }

    @Override
    protected ArrayList<Earthquake> call() throws Exception {
        URLConnection connection = new URL(source.getUrl()).openConnection();

        final Charset charset;
        char a = '\n';
        switch (source) {
            case KANDILLI:
                charset = Charset.forName("windows-1254");
                parser = new KandilliParser();
                break;
            case USGS:
            case EMSC:
                charset = Charset.defaultCharset();
                parser = new TextParser();
                break;
            case AFAD:
                charset = StandardCharsets.UTF_8;
                parser = new AfadParser();
                a = ' ';
                break;
            default:
                return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
        String line;
        StringBuilder data = new StringBuilder();
        while ((line = reader.readLine()) != null)
            data.append(line).append(a);
        reader.close();

        return parser.parse(data.toString());
    }
}
