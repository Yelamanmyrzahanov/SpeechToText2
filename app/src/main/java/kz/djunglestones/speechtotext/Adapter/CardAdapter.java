package kz.djunglestones.speechtotext.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;
//import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import kz.djunglestones.speechtotext.Model.Model;
import kz.djunglestones.speechtotext.R;

/**
 * Created by yelaman on 03.07.17.
 */

public class CardAdapter extends BaseCardAdapter{

    private static List<Model> modelList;
    private Context context;

    public CardAdapter(List<Model> modelList, @Nullable Context context) {
        this.modelList = modelList;
        this.context = context;
    }


    @Override
    public  int getCount() {
        return modelList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {

        if (modelList==null || modelList.size()==0){
            return;
        }


        ImageView imageView = (ImageView)cardview.findViewById(R.id.imageview);
        TextView textView = (TextView)cardview.findViewById(R.id.textViewTongueTwister);
        Model model = modelList.get(position);
        System.out.println(model.getTitle());
        textView.setText(model.getTitle());
        imageView.setImageResource(model.getImage());





    }
}
