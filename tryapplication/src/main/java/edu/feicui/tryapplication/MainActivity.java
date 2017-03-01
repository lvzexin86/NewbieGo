package edu.feicui.tryapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity {
    static Context con;
    FragmentTransaction t;
    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
        setContentView(R.layout.activity_main);
        Button btn= (Button) findViewById(R.id.btn1);
//        t = getSupportFragmentManager().beginTransaction();
//        rel= (RelativeLayout) findViewById(R.id.lin);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("####################","Onclick");
//                MyFragment.Fragment1 b = new MyFragment.Fragment1();
////                rel.removeAllViewsInLayout();
//                getSupportFragmentManager().beginTransaction().replace(R.id.lin,b).commit();
//            }
//        });
//        MyFragment a = new MyFragment();
//        t.replace(R.id.lin, a).commit();


    }

    public static Context getContext() {
        return con;
    }
}
