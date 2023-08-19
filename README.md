# earthquake-library-java

```java
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

            }
        }).load();
```
