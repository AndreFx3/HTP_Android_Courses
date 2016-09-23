package andrewpolvoko.htp_android_courses;

import android.text.Html;
import android.text.Spanned;

public class NewsItem {

    public final Spanned title;
    public final Spanned description;
    public final String newsLink;
    public final String imageLink;

    @SuppressWarnings("deprecation")
    public NewsItem(String title, String description, String newsLink, String imageLink) {
        this.title = Html.fromHtml(title);
        this.description = Html.fromHtml(description.replaceAll("\\<img.*?>", ""));
        this.newsLink = newsLink;
        this.imageLink = imageLink;
    }

}
