package tanruimin.bwei.com.zhoukaodemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * data:2017/4/14
 * name:tanruimin tanruimin
 * function:
 */

public class ViewHolder {
    private final SparseArray<View> views;
        private int position;
        private View convertView;
        private Context mContext;

        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position){
            this.mContext=context;
            this.views=new SparseArray<>();
            this.convertView= LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
            convertView.setTag(this);
        }
        public static ViewHolder get(View convertView,ViewGroup parent,int layoutId,int position){
            if (convertView == null ){
                return new ViewHolder(parent.getContext(),parent,layoutId,position);
            }
            return (ViewHolder)convertView.getTag();
        }
        public <T extends View> T getView(int viewId){
            View view=views.get(viewId);
            if (view ==null){
                view=convertView.findViewById(viewId);
                views.put(viewId,view);
            }
            return (T)view;
        }
        public View getConvertView(){
            return  convertView;
        }
        public TextView setText(int viewId, String text){
            TextView textView=getView(viewId);
            textView.setText(text);
            return textView;
        }
        public Button setButton(int viewId, String text){
            Button button=getView(viewId);
            button.setText(text);
            return button;
        }
        public ImageView setImageResource(int viewId, int drawableId){
            ImageView imageView=getView(viewId);
            imageView.setImageResource(drawableId);
            return imageView;
        }
        public ImageView setImageBitmap(int viewId, Bitmap bitmap){
            ImageView imageView=getView(viewId);
            imageView.setImageBitmap(bitmap);
            return imageView;
        }
        public ImageView setImageByUrl(int viewId,String url){
            ImageView imageView=getView(viewId);
            //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
            //ImageLoader.getInstance().displayImage(url, (ImageView) getView(viewId));
            return imageView;
        }
        public int getPosition(){
            return position;
        }
}
