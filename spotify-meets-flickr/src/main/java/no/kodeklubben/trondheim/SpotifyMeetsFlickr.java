package no.kodeklubben.trondheim;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.photos.Size;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.models.Artist;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

class SpotifyMeetsFlickr extends JPanel {
    private final static String DEFAULT_ARTIST = "Daft Punk";
    private final static com.wrapper.spotify.Api spotify = Api.builder().build();
    private final static Flickr flickr = new Flickr("3a23a4a52f35cb3d2ffe1d4575b79da8", "3aad9d968a5582e1", new REST());

    public static void main(String[] commandLineArguments) {
            String artistToSearchFor = handleInput(commandLineArguments);

            String selectedArtist = searchSpotify(artistToSearchFor);

            Photo selectedPhoto = searchFlickr(selectedArtist);

            downloadAndDisplay(selectedPhoto);
    }

    private static String handleInput(String[] commandLineArguments) {
        String artistToSearchFor = DEFAULT_ARTIST;

        if (commandLineArguments.length == 0) {
            // using DEFAULT_ARTIST
        } else if (commandLineArguments.length == 1) {
            artistToSearchFor = commandLineArguments[0];
        } else {
            System.out.println("Usage: spotify-meets-flickr '<artist name>'");
            System.exit(1);
        }

        return artistToSearchFor;
    }

    private static String searchSpotify(String artist) {
        try {
            System.out.println("Searching Spotify for artist '" + artist + "'");

            ArtistSearchRequest request = spotify.searchArtists(artist).limit(5).build();
            List<Artist> artistResults = request.get().getItems();

            if (artistResults.size() == 0) {
                System.out.println("The artist does not exist!");
                System.exit(0);
            }

            System.out.println("Top results:");
            int index = 0;
            for (Artist artistResult : artistResults) {
                System.out.println(index++ + ": " + artistResult.getName());
            }

            return artistResults.get(0).getName();
        } catch (Exception e) {
            throw new RuntimeException("Could not search Spotify!", e);
        }
    }

    private static Photo searchFlickr(String selectedArtist) {
        try {
            System.out.println("Searching Flickr for image of '" + selectedArtist + "'");

            SearchParameters searchParameters = new SearchParameters();
            searchParameters.setText(selectedArtist);
            PhotoList<Photo> photoResults = flickr.getPhotosInterface().search(searchParameters, 5, 0);

            if (photoResults.size() == 0) {
                System.out.println("No image of the artist!");
                System.exit(0);
            }

            return photoResults.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Could not search Flickr!", e);
        }
    }

    private static void downloadAndDisplay(Photo photo) {
        try {
            BufferedImage imageToDisplay = flickr.getPhotosInterface().getImage(photo, Size.MEDIUM);

            JFrame frame = new JFrame();
            frame.getContentPane().add(new ImageViewer(imageToDisplay));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(imageToDisplay.getWidth(), imageToDisplay.getHeight());
            frame.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException("Could not download and display photo from Flickr!", e);
        }
    }
}

