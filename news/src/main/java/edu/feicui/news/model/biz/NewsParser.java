package edu.feicui.news.model.biz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.news.model.entity.News;

/**
 * Created by Administrator on 2017/2/16.
 */
public class NewsParser {
    public static List<News> parserData(String data,ParserOverListener listener) {
        if(data.equals("")){
            return null;
        }
        List<News> newses = new ArrayList<News>();
        try {
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject js = array.getJSONObject(i);
                newses.add(new News(js.getString("summary")
                        , js.getString("icon")
                        , js.getString("stamp")
                        , js.getString("title")
                        , js.getInt("nid")
                        , js.getString("link")
                        , js.getInt("type")));
            }
            NewsDBManager.getInstance().AddNewsData(newses);
            listener.over(newses);
            return newses;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ParserOverListener {
        void over(List<News> newses);
    }
}
