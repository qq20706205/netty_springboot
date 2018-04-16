package com.netty.utils;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelMap {
    public static int channelNum=0;
    private static Map<String,Channel> channelHashMap = new ConcurrentHashMap<>();

    public static Map<String, Channel> getChannelHashMap() {
        return channelHashMap;
    }

    public static Channel getChannelByName(String name){
        if(channelHashMap == null||channelHashMap.isEmpty()){
            return null;
        }
        return channelHashMap.get(name);
    }
    public static void addChannel(String name,Channel channel){
        channelHashMap.put(name,channel);
        channelNum++;
    }
    public static boolean removeChannelByName(String name){
        if(channelHashMap.containsKey(name)){
            channelHashMap.remove(name);
            return true;
        }
            return false;

    }
}
