package edu.feicui.news.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2017/2/16.
 */
public class BitMapUtil {
    private static ImageLoadListener mListener;

    public static void setImageLoadListener(ImageLoadListener listener) {
        mListener = listener;
    }

    public Bitmap getBitmapBy(String url) {
        Bitmap map;
        map = MemoryCacheUtil.getBitmapFromCache(url);
        if (map == null) {
            map = FileCacheUtil.getBitmapFromCache(url);
            if (map == null) {
                new MySycnTask().execute(url);
            } else {
                MemoryCacheUtil.writeToCache(url, map);
            }
        }
        return map;
    }

    class MySycnTask extends AsyncTask<String, Integer, Bitmap> {
        String mUrl;

        @Override
        protected Bitmap doInBackground(String[] strings) {
            mUrl = strings[0];
            return BitmapFactory.decodeStream(HttpUtil.getDataStream(mUrl));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null&&mListener!=null){
                MemoryCacheUtil.writeToCache(mUrl, bitmap);
                FileCacheUtil.setBitmapToCache(bitmap, mUrl);
                mListener.imageLoadOver(bitmap, mUrl);
            }
        }
    }

    public interface ImageLoadListener {
        void imageLoadOver(Bitmap map, String url);
    }
}
