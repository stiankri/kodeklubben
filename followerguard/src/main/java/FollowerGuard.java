import org.jinstagram.Instagram;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/** An Instagram client that lets you know when someone stops following you */
public class FollowerGuard {

    private static Instagram instagram;

    public static void main(String[] args) throws InstagramException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        instagram = new Instagram("f3bc5a78a68d4beb8868be2a09285e67");

        System.err.print("Input user name to watch for: ");
        UserFeed userToWatch = instagram.searchUser(sc.nextLine().trim());
        UserFeedData firstMatchingUser = userToWatch.getUserList().get(0);

        System.out.println(
            "Watching for unfollows for "
            + firstMatchingUser.getUserName()
            + " (" + firstMatchingUser.getFullName() + ")");

        List<String> followers = Collections.emptyList();

        while (true) {
            List<String> followersBefore = followers;
            followers = getFollowerUserNames(firstMatchingUser);

            // for debugging
            System.err.println(followers.toString());

            for (String followerBefore : followersBefore) {
                if (!followers.contains(followerBefore)) {
                    System.out.println(followerBefore + " stopped following you!");
                }
            }
            TimeUnit.MINUTES.sleep(1);
        }
    }

    private static List<String> getFollowerUserNames(UserFeedData myUser) throws InstagramException {
        UserFeed userFollowedByList = instagram.getUserFollowedByList(myUser.getId());

        List<UserFeedData> userList = userFollowedByList.getUserList();

        ArrayList<String> userNames = new ArrayList<String>();
        for (UserFeedData userFeedData : userList) {
            userNames.add(userFeedData.getUserName());
        }

        return userNames;
    }
}