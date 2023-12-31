package tr.com.erenkaynar.library.earthquake;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tr.com.erenkaynar.library.earthquake.enums.Source;
import tr.com.erenkaynar.library.earthquake.models.Earthquake;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);

        EarthquakeAPI.initialize().setSource(Source.KANDILLI).setEarthquakeAPIListener(new EarthquakeAPIListener() {
            @Override
            public void onLoaded(ArrayList<Earthquake> earthquakes) {
                StringBuilder output = new StringBuilder();

                if (earthquakes != null)
                    for (Earthquake earthquake : earthquakes) {
                        output
                                .append(earthquake.getHash())
                                .append("|")
                                .append(earthquake.getLocation())
                                .append("|")
                                .append(earthquake.getCoordinates().getLatitude())
                                .append(",")
                                .append(earthquake.getCoordinates().getLongitude())
                                .append("|")
                                .append(earthquake.getMagnitude())
                                .append("|")
                                .append(earthquake.getDepth())
                                .append("|")
                                .append(earthquake.getDatetime().toString())
                                .append("|")
                                .append(earthquake.getRevised() == null ? "revize yok" : earthquake.getRevised().getNumber())
                                .append("\n");
                    }
                text.setText(output.toString());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).load();
    }
}