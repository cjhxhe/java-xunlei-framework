package com.xunlei.framework.cache.console;

import com.xunlei.framework.cache.storage.JVMCache;

import java.util.*;
import java.util.regex.Pattern;

/**
 * JVM CAche Viewer
 */
public class JvmCacheViewer implements CacheViewer {

    private Map<String, Object> cache = new JVMCache().getCacheObject();

    @Override
    public String get(String cacheKey) {
        Object o = cache.get(cacheKey);
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    @Override
    public List<String> keys(String cacheKeyPattern) {
        List<String> keyList = new ArrayList<String>();

        if (cacheKeyPattern == null || "".equals(cacheKeyPattern)) {
            return keyList;
        }

        Set<String> keySet = cache.keySet();
        Pattern regex = parseCacheKeyPattern(cacheKeyPattern);

        for (String key : keySet) {
            if (regex.matcher(key).matches()) {
                keyList.add(key);
            }
        }
        // sort list
        Collections.sort(keyList);

        // return sorted list
        return keyList;
    }

    private Pattern parseCacheKeyPattern(String cacheKeyPattern) {
        return Pattern.compile("^" + cacheKeyPattern + "$");
    }

    @Override
    public String getDescription() {
        return "------- 监控本地JVM缓存 ---------";
    }

}
