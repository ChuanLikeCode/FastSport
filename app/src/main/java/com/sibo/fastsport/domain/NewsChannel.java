package com.sibo.fastsport.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */
public class NewsChannel {
    private String channel;
    private List<String> title = new ArrayList<>();
    private List<String> url = new ArrayList<>();

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.add(title);
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.add(url);
    }
}
