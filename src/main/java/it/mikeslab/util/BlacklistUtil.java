package it.mikeslab.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class BlacklistUtil {


    public boolean isBlacklisted(UUID uuid) {
        return CacheHandler.getBlacklistedUUIDsCache().getIfPresent(uuid) != null;
    }


    public boolean blacklist(UUID uuid) {
        if(isBlacklisted(uuid)) {
            return false;
        }
        CacheHandler.getBlacklistedUUIDsCache().put(uuid, true);
        return true;
    }


    public boolean unBlacklist(UUID uuid) {
        if(!isBlacklisted(uuid)) {
            return false;
        }
        CacheHandler.getBlacklistedUUIDsCache().invalidate(uuid);
        return true;
    }




}
