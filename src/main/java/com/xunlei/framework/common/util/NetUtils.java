package com.xunlei.framework.common.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class NetUtils {

    public static List<URI> fromURIStringArray(String conStr) {
        if (StringUtils.isEmpty(conStr)) {
            return null;
        }
        String[] conStrs = conStr.split(",");
        List<URI> uriList = new ArrayList<>();
        for (String uriStr : conStrs) {
            URI uri = URI.create(uriStr);
            uriList.add(uri);
        }
        return uriList;
    }
}
