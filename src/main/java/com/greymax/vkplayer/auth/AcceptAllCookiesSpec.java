package com.greymax.vkplayer.auth;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BestMatchSpec;


public class AcceptAllCookiesSpec extends BestMatchSpec {
    public static final String ID = "_acceptAllCookies";

    public AcceptAllCookiesSpec(String[] patterns, boolean singleHeader) {
        super(patterns, singleHeader);
    }

    public AcceptAllCookiesSpec() {
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        try {
            super.validate(cookie, origin);
        } catch (MalformedCookieException e) {
            if (!e.getMessage().contains("Domain of origin"))
                throw e;
        }
    }
}
