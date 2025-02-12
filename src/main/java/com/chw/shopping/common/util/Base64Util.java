package com.chw.shopping.common.util;

import java.util.Base64;

public class Base64Util {

    public static String encode(String value) {
        return Base64.getUrlEncoder().encodeToString(value.getBytes());
    }

    public static String decode(String encodedValue) {
        return new String(Base64.getUrlDecoder().decode(encodedValue));
    }

}
