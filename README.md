# earthquake-library-java

## Örnek Kod
```java
EarthquakeAPI.initialize().setSource(Source.KANDILLI).setEarthquakeAPIListener(new EarthquakeAPIListener() {
            @Override
            public void onLoaded(ArrayList<Earthquake> earthquakes) {
                
            }

            @Override
            public void onError(Exception e) {

            }
        }).load();
```
