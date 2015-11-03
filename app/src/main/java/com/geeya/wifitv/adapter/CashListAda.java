package com.geeya.wifitv.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.CashCoupons;

public class CashListAda extends BasicAdapter {

    private List<CashCoupons> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private Bitmap bitmap;

    public CashListAda(Context context, List<CashCoupons> list) {
        this.context = context;
        this.list = list;
        Drawable drawable = context.getResources().getDrawable(R.mipmap.cash_bg);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CashCoupons cashList = list.get(position);
        View view = convertView;
        final Holder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_cash_list, parent, false);
            holder = new Holder();
            holder.rlCashDetail = (RelativeLayout) view.findViewById(R.id.cash_detail);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.rlCashDetail.setBackground(getDrawable(cashList.getBgColor()));
//		holder.rlCashDetail.setBackgroundDrawable(getdDrawable(cashList.getBgColor()));
        return view;
    }

    public class Holder {
        public RelativeLayout rlCashDetail;
    }

    private Drawable getDrawable(int colorId) {
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
//		Drawable drawable = new BitmapDrawable(bitmap);
        // drawable = context.getResources().getDrawable(R.drawable.cash_bg);
        drawable.setColorFilter(colorId, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    // private Bitmap readBitmap(int resId){
    // BitmapFactory.Options opt = new BitmapFactory.Options();
    // opt.inPreferredConfig = Config.RGB_565;
    // opt.inPurgeable = true;
    // opt.inInputShareable = true;
    // InputStream is = context.getResources().openRawResource(resId);
    // return BitmapFactory.decodeStream(is, null, opt);
    // //return BitmapFactory.decodeResource(context.getResources(),
    // R.drawable.cash_bg);
    // }
}
