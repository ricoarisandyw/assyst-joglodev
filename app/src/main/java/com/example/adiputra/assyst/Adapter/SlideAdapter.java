package com.example.adiputra.assyst.Adapter;

/**
 * Created by Mr Abid on 9/2/2017.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adiputra.assyst.R;


public class SlideAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.img_cm,R.drawable.img_dr,R.drawable.img_templar,R.drawable.img_invo};
    private String[] describe =
            {"    Welcome to Projects Manager enjoy for using manage your project with easily, friendly and completely"
            ,"    With Projects Manager , you can create unlimited project with free account (without cost)"
            ,"    Find your partner or your employee just with barcode or email"
            ,"    Enjoy Premium Account with ......................................."};
    private String[] titles = {"Projects Manager","Unlimited Projects","Easily Partner","Premium Account"};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context ctx){
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.imgV_slider);
        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
