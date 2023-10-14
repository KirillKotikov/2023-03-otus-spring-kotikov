package ru.kotikov.blog.Utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class UserUtils {

    public static String getLoginAuthenticated() {
        UserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }

    public static boolean isAdmin() {
        UserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities().stream().map(Objects::toString).anyMatch(x -> x.equals("ADMIN"));
    }

    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

}
