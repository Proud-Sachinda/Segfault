package com.CookieHandling;

import com.vaadin.server.VaadinService;

import javax.servlet.http.Cookie;

public class CookieHandling {

    private static Cookie[] getCookies() {
        return VaadinService.getCurrentRequest().getCookies();
    }

    public static void addCookie(String name, String value, int age) {

        // create a new cookie
        Cookie cookie = new Cookie(name, value);

        // make cookie expire in week
        cookie.setMaxAge(age);

        // set the cookie path
        cookie.setPath(VaadinService.getCurrentRequest().getContextPath());

        // save cookie
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    public static void removeCookie(String name) {

        // get cookie
        Cookie cookie = getCookieByName(name);

        if (cookie != null) {
            // remove
            cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            VaadinService.getCurrentResponse().addCookie(cookie);
        }
    }

    public static Cookie getCookieByName(String name) {

        // return variable
        Cookie cookie = null;

        for (Cookie c : getCookies())
            if (c.getName().equals(name)) cookie = c;

        return cookie;
    }
}
