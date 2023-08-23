package tr.com.erenkaynar.library.earthquake.sources.parsers;

import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.models.Earthquake;

public abstract class Parser {

    public abstract ArrayList<Earthquake> parse(String data);
}
