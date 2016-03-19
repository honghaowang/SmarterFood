package uml.swinlab.smarterfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by kinse on 3/18/2016.
 */
public class LogAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<LogData> mList;
    private LayoutInflater mLayoutInflater = null;

    public LogAdapter(Context context, ArrayList<LogData> list){
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.group_layout, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }
        viewHolder.foodInfo.setText(mList.get(position).foodInfo);
        viewHolder.timeInfo.setText(mList.get(position).startTime);
        if(mList.get(position).isConfirmed.equals("yes")) {
            viewHolder.checkState.setImageResource(R.drawable.check);
        }
        else{
            viewHolder.checkState.setImageResource(R.drawable.cross);
        }
        return v;
    }
}

class CompleteListViewHolder {
    public TextView foodInfo, timeInfo;
    public ImageView checkState;
    public CompleteListViewHolder(View base) {
        foodInfo = (TextView) base.findViewById(R.id.food);
        timeInfo = (TextView) base.findViewById(R.id.time);
        checkState = (ImageView) base.findViewById(R.id.check);
    }
}