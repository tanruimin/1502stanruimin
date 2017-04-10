package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.Mybean;
import shanyao.tabpagerindicatordemo.R;
import shanyao.tabpagerindicatordemo.utils.MyImageUtils;

/**
 * data:2017/4/10
 * name:tanruimin tanruimin
 * function:
 */

public class Mybase extends BaseAdapter {
    private Context context;
    private List<Mybean.ResultBean.DataBean> list;

    public Mybase(Context context, List<Mybean.ResultBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView=convertView.inflate(context, R.layout.mylistview,null);
            vh.iv= (ImageView) convertView.findViewById(R.id.iv);
            vh.tv1= (TextView) convertView.findViewById(R.id.tv1);
            vh.tv2= (TextView) convertView.findViewById(R.id.tv2);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv1.setText(list.get(position).getTitle());
        vh.tv2.setText(list.get(position).getAuthor_name());
        MyImageUtils.loadpic(list.get(position).getThumbnail_pic_s(),vh.iv);
        return convertView;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv1,tv2;
    }
}
