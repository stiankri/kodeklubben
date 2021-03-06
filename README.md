Kodeklubben  Eksempler 
===========
*Trondheim, 15. november 2014*

Workshop for å lære om programmering mot internett-tjenester.
 
## Utviklingsverktøy
 - [Eclipse](https://www.eclipse.org/downloads/)
 - [Maven](http://maven.apache.org/download.cgi)

## Yr.no
Eksempelet henter værdata fra Yr.no og skriver ut siste målte temperatur samt forventet temperatur de neste dagene for et hardkodet sted i Norge (Byåsen i Trondheim). Returdataene fra Yr.no er i XML og data hentes ut ved hjelp av XPath-uttrykk.

```sh
cd yr
mvn clean package
java -jar target/yr-1.0.jar
```

 - [Værvarsel i XML-format fra Yr.no](http://om.yr.no/verdata/xml/)
 - [Introduksjon til XPath (på engelsk)](http://www.w3schools.com/xpath/)

## Spotify og Flickr
Eksempelet søker etter en artist, f.eks. 'Justin Bieber', på [Spotify](https://www.spotify.com/). Den tar så det øverste treffet og søker etter bilder på [Flickr](https://www.flickr.com/). Resultatet blir vist i et eget vindu.
```sh
cd spotify-meets-flickr
mvn clean package
java -jar target/spotify-meets-flickr-1.0-jar-with-dependencies.jar 'Justin Bieber'
```

Det går an å utvide programmet på mange måter. En avansert variant er å hente ned spillelistene til en bruker og vise en ny artist hvert minutt. API dokumentasjonen nedenfor lister opp hvordan Spotify og Flickr kan brukes.

 - [Spotify API](https://developer.spotify.com/web-api/)
 - [Spotify Java Library](https://github.com/thelinmichael/spotify-web-api-java)
 - [Flickr API](https://www.flickr.com/services/api/)
 - [Flickr Java Library](https://github.com/callmeal/Flickr4Java)

## Instagram
På [Instagram](http://instagram.com/) kan du få beskjed når noen starter å følge deg, men ikke hvis noen slutter å følge deg. Dette eksempelet viser hvordan vi kan følge med på endringer.
```sh
cd followerguard
mvn clean package
java -jar target/follower-guard-1.0-SNAPSHOT-jar-with-dependencies.jar
```

 - [Instagram API](http://instagram.com/developer/)
 - [Instagram Java Library](https://github.com/sachin-handiekar/jInstagram)

## Twitter
Har du lyst til å sende ut [tweets](https://twitter.com/) automatisk? Det gjør dette eksempelet.
```sh
cd pushy
mvn clean package
java -jar target/pushy-1.0-SNAPSHOT-jar-with-dependencies.jar
```

 - [Twitter API](https://dev.twitter.com/rest/public)
 - [Twitter Java Library](http://twitter4j.org/en/)
