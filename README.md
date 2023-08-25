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

## Kullanılan Uygulamalar

<p align="center">
<img width="100%" src="https://raw.githubusercontent.com/sqayner/earthquake-library-java/main/images/son-depremler-thumbnail.png" style="border-radius:100px">
</p>

<p align="center">
<a href="https://play.google.com/store/apps/details?id=tr.org.sondepremler">
<img src="https://cdn.rawgit.com/steverichey/google-play-badge-svg/master/img/tr_get.svg" width="20%">
</a>
</p>
