import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A twitter bot that tells you to do push-ups
 */
public class Pushy {

    private final Random random = new Random();

    public static void main(String[] args) throws TwitterException, InterruptedException {
        new Pushy().run();
    }

    private void run() throws TwitterException, InterruptedException {
        ConfigurationBuilder cb = new ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey("TsCwojpT0cJkXGsSR24VANpTy")
            .setOAuthConsumerSecret("GtMZ7VGSqe2DBG3MSZzIlvAhTD1MM25R5XSmAUu29VAjQPeWsj")
            .setOAuthAccessToken("2876468957-g0d0RGxiIazTM0tb7bYtUEsxytQLQg9CdWiUu9l")
            .setOAuthAccessTokenSecret("OL2eV0eEJw63R2idTHJLcjUOnQYJAZU7aU7vJus1bIoYW");

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());

        Twitter twitter = twitterFactory.getInstance();

        while (true) {
            String statusUpdate = "Drop and give me " + (random.nextInt(20)) + "!";
            twitter.tweets().updateStatus(statusUpdate);
            System.out.println("I tweeted: \"" + statusUpdate + "\"");

            int timeout = 4 + random.nextInt(12);

            System.out.println("Waiting " + timeout + " hours before next tweet");

            TimeUnit.HOURS.sleep(timeout);
        }
    }
}