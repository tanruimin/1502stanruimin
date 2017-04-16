package tanruimin.bwei.com.zhoukaodemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * data:2017/4/14
 * name:tanruimin tanruimin
 * function:
 */

public abstract class Adapter<T> extends BaseAdapter {
    private Context mContext;
        private List<T> mList;

        public Adapter(Context context, List<T> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList ==null ? 0:mList.size();
        }

        @Override
        public T getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=ViewHolder.get(convertView,parent, R.layout.lv_item,position);
            convert(viewHolder,getItem(position));
            return viewHolder.getConvertView();
        }
        public abstract void convert(ViewHolder holder,T item);
}
