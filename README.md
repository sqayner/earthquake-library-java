# earthquake-library-java
[![](https://jitpack.io/v/sqayner/earthquake-library-java.svg)](https://jitpack.io/#sqayner/earthquake-library-java)

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
