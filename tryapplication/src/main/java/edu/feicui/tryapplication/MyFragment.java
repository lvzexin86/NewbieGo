package edu.feicui.tryapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/25.
 */
public class MyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_fragment1, container, false);
        TextView txt = (TextView) v.findViewById(R.id.txt_item_fragment_a);
        Log.i("#####################", "onCreateViewMyFragment");
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return v;
    }

    public static class Fragment1 extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.item_fragment2, container, false);
            TextView txt = (TextView) v.findViewById(R.id.txt_item_fragment_b);
            Log.i("#####################", "onCreateViewFragment1");
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.getContext(), "无敌风火轮", Toast.LENGTH_LONG).show();
                }
            });
            return v;
        }
    }
}
