package xyz.imcodist.simpleplayerwarps.data;

import org.bukkit.Location;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarpData {
    public String name;
    public Location location;
    public UUID author;

    public String authorName;

    public boolean isValidName(String givenName) {
        String nameRegex = "[A-Za-z0-9_-]*";

        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(givenName);

        return matcher.matches();
    }
}
