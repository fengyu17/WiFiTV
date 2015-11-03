package com.geeya.wifitv.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.DiscountGridViewAda;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.widget.ScrollGridView;

public class DiscountCouponsFragment extends BaseFragment {

    private View rootView;
    private ScrollGridView sgGoods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_discount_coupons, container, false);
        }
        sgGoods = (ScrollGridView) rootView.findViewById(R.id.sg_discount_coupons);
        DiscountGridViewAda adapter = new DiscountGridViewAda(mContext, 10);
        sgGoods.setAdapter(adapter);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast toast = Toast.makeText(mContext, R.string.net_exception, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
