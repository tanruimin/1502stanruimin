package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanruimin.zhoukao0407.R;

import java.util.List;

import bean.phone;

/**
 * data:2017/4/9
 * name:tanruimin tanruimin
 * function:
 */

public class leftadapter extends BaseAdapter{
    private Context context;
    private List<phone> arr;

    public leftadapter(Context context, List<phone> arr) {
        this.context = context;
        this.arr = arr;
    }


    public int getCount() {
        return arr.size();
    }


    public Object getItem(int position) {
        return arr.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder vh;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.leftlist,null);
            vh=new ViewHoder();
            vh.im= (ImageView) convertView.findViewById(R.id.frag1_im);
            vh.tv= (TextView) convertView.findViewById(R.id.frag_tv);
            convertView.setTag(vh);
        }else {
            vh= (ViewHoder) convertView.getTag();
        }
        vh.im.setBackgroundResource(arr.get(position).getIm());
        vh.tv.setText(arr.get(position).getName());
        return convertView;
    }

    class ViewHoder{
        ImageView im;
        TextView tv;
    }

}
