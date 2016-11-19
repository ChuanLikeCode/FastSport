package com.sibo.fastsport.utils;

import com.sibo.fastsport.domain.NewsChannel;
import com.sibo.fastsport.domain.NewsDetails;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 解析  http://rss.sina.com.cn/sina_all_opml.xml
 * Created by Administrator on 2016/11/13.
 */
public class XmlParseUtils {
    //包含频道+新闻类别的标题和URL
    public static List<NewsChannel> list_channelAndNews = new ArrayList<>();
    //新闻类别里面的新闻Items
    public static List<NewsDetails> list_details;
    //包含新闻类别标题
    public static Map<String, List<NewsDetails>> map_newsItems = new HashMap<>();
    public static boolean isFirst = true;
    public static boolean isPreSelectFirst = true;
    private NewsChannel channel;
    private NewsDetails newsDetails;
    private boolean isChannel = true;
    private int evenType;
    private XmlPullParser pullParser;
    private String rootNodeName = "1";

    /**
     * 获取频道信息
     *
     * @param input
     */
    public void pullParseChannelXml(InputStream input) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            pullParser = factory.newPullParser();
            pullParser.setInput(input, "UTF-8");
            evenType = pullParser.getEventType();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (evenType != XmlPullParser.END_DOCUMENT) {
            String nodeName = pullParser.getName();
            switch (evenType) {
                case XmlPullParser.START_DOCUMENT:
                    list_channelAndNews.clear();
                    break;
                case XmlPullParser.START_TAG:
                    if (nodeName.equals("outline") && (pullParser.getAttributeValue(null, "xmlUrl") == null)) {
                        isChannel = false;
                        channel = new NewsChannel();
                        String[] str = pullParser.getAttributeValue(0).split("\\-");
                        channel.setChannel(str[0]);
                    } else if (nodeName.equals("outline") && isChannel) {
                        channel.setTitle(pullParser.getAttributeValue(0));
                        channel.setUrl(pullParser.getAttributeValue(3));
                    }
                    isChannel = true;
                    break;
                case XmlPullParser.END_TAG:
                    if (nodeName.equals("outline") && (pullParser.getAttributeValue(null, "xmlUrl") == null)) {
                        list_channelAndNews.add(channel);
                        channel = null;
                    }
                    break;
                default:
                    break;
            }
            try {
                evenType = pullParser.next();
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

        }
        //Log.e("pullParseXml",list.size()+"");
    }

    /**
     * 解析每个频道里面的新闻类别保存在HashMap中 map_newsItems
     *
     * @param input    获取网络连接的输入流
     * @param newsType Key：新闻类别
     */
    public void pullParseItemXml(InputStream input, String newsType) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            pullParser = factory.newPullParser();
            pullParser.setInput(input, "UTF-8");
            evenType = pullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        while (evenType != XmlPullParser.END_DOCUMENT) {
            String nodeName = pullParser.getName();
            switch (evenType) {
                case XmlPullParser.START_DOCUMENT:
                    list_details = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    try {
                        matcherTAG(nodeName);
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (nodeName.equals("item")) {
                        list_details.add(newsDetails);
                        newsDetails = null;
                        rootNodeName = "1";
                    }
                    break;
                default:
                    break;
            }
            try {
                evenType = pullParser.next();
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }
        map_newsItems.put(newsType, list_details);
    }

    /**
     * START_TAG中匹配标签名字
     *
     * @param nodeName 标签名
     * @throws IOException
     * @throws XmlPullParserException
     */
    private void matcherTAG(String nodeName) throws IOException, XmlPullParserException {
        switch (nodeName) {
            case "item":
                newsDetails = new NewsDetails();
                rootNodeName = "item";
                break;
            case "title":
                if (rootNodeName.equals("item")) {
                    //Log.e("title",pullParser.nextText()+"");
                    String str = pullParser.nextText().replaceAll("\r\n", "");
                    str = str.trim();
                    newsDetails.setTitle(str);
                }

                break;
            case "link":
                if (rootNodeName.equals("item")) {
                    //Log.e("link",pullParser.nextText()+"");
                    newsDetails.setUrl(pullParser.nextText());
                }

                break;
            case "pubDate":
                if (rootNodeName.equals("item")) {
                    //Log.e("pubDate",pullParser.nextText()+"");
                    newsDetails.setPubDate(pullParser.nextText());
                }
                break;
            case "description":
                if (rootNodeName.equals("item")) {
                    String str = pullParser.nextText().replaceAll("\\s*|\\t|\\r|\\n", "");
                    str = str.trim();
                    newsDetails.setDescription("\u3000\u3000" + str);
                }
                break;
        }
    }

}
