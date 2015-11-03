package com.geeya.wifitv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.ProgramDetailGridViewAda;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.bean.ProgramDetail;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.presenter.video.VideoAction;
import com.geeya.wifitv.presenter.video.VideoActionImpl;
import com.geeya.wifitv.ui.interf.IProgramDetail;
import com.geeya.wifitv.utils.VolleyUtil;

public class ProgramDetailActivity extends BaseActivity implements IProgramDetail, OnClickListener {

    private TextView tvDirector;
    private TextView tvActor;
    private TextView tvDescrib;
    private TextView tvName;
    private ImageView ivPoster;
    private GridView gvEpisode;

    private ProgramInfo mprogramInfo;

    private VideoAction videoAction;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_programdetail);
        mprogramInfo = (ProgramInfo) getIntent().getSerializableExtra("ProgramInfo");
        initView();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        videoAction = new VideoActionImpl(this);
        videoAction.programDetailAction(mprogramInfo);
    }

    private void initView() {
        ivPoster = (ImageView) findViewById(R.id.iv_moviepic);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDirector = (TextView) findViewById(R.id.tv_director);
        tvActor = (TextView) findViewById(R.id.tv_actor);
        tvDescrib = (TextView) findViewById(R.id.tv_describle);
        gvEpisode = (GridView) findViewById(R.id.gv_episode);
        TextView tvBack = (TextView) findViewById(R.id.detail_back);
        tvBack.setOnClickListener(this);
        ImageLoader imageLoader = VolleyUtil.getImageLoader();
        imageLoader.get(mprogramInfo.getCoverImg(), ImageLoader.getImageListener(ivPoster, R.mipmap.ic_empty, R.mipmap.ic_empty), 200, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateView(final ProgramDetail programDetail) {
        // TODO Auto-generated method stub
        tvName.setText("《" + mprogramInfo.getName() + "》");
        if (null != programDetail.getDirector())
            tvDirector.setText("导演:\t" + programDetail.getDirector() + "");
        else
            tvDirector.setText("导演:\t");
        if (null != programDetail.getLeadingActor())
            tvActor.setText("演员:\t" + programDetail.getLeadingActor() + "");
        else
            tvActor.setText("演员:\t");
        if (null != programDetail.getDescrib())
            tvDescrib.setText(Html.fromHtml("简介:\t" + programDetail.getDescrib()));
        else
            tvDescrib.setText("简介:\t");
        SpannableStringBuilder directorBuilder = new SpannableStringBuilder(tvDirector.getText().toString());
        SpannableStringBuilder actorBuilder = new SpannableStringBuilder(tvActor.getText().toString());
        ForegroundColorSpan blackColorSpan = new ForegroundColorSpan(0xff000000);
        directorBuilder.setSpan(blackColorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actorBuilder.setSpan(blackColorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDirector.setText(directorBuilder);
        tvActor.setText(actorBuilder);
        gvEpisode.setAdapter(new ProgramDetailGridViewAda(programDetail.getLength(), mContext));
        gvEpisode.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ProgramDetailActivity.this, ProgramPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ProgramSource", programDetail.getList());
                bundle.putInt("Position", position);
                bundle.putString("ProgramID", mprogramInfo.getProgramID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        videoAction = null;
    }

    @Override
    public void showToast(int message) {
        // TODO Auto-generated method stub
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

}
