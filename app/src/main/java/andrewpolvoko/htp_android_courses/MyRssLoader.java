package andrewpolvoko.htp_android_courses;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class MyRssLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {
    private static String feedUrl;
    private ArrayList<NewsItem> mData;
    private NewsItem newsItem;

    public MyRssLoader(Context context, String url) {
        super(context);
        feedUrl = url;
    }

    private static ArrayList<NewsItem> doParse() {
        ArrayList<NewsItem> data = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(feedUrl)
                    .ignoreContentType(true)
                    .parser(Parser.xmlParser())
                    .get();
            doc.select("script, style, .hidden").remove();

            for (Element item : doc.select("item")) {
                final String title = item.select("title").first().text().toString();
                final String link = item.select("link").first().text();
                final String description = item.select("description").text();
                Element linksInner = item.select("media|thumbnail ").first();
                final String imageUrl = linksInner.attr("feedUrl").trim();
                data.add(new NewsItem(title, description, link, imageUrl));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public ArrayList<NewsItem> loadInBackground() {
        return doParse();
    }

    @Override
    public void deliverResult(ArrayList<NewsItem> data) {
        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            super.deliverResult(mData);
        } else {
            forceLoad();
        }
    }
}
