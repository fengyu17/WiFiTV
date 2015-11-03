/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-2 下午3:03:05
 */
package com.geeya.wifitv.ui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.BasicAdapter;
import com.geeya.wifitv.base.BaseActivity;

/**
 * @author Administrator
 */
public class FolderSelectedActivity extends BaseActivity implements OnClickListener {

    private TextView tvBack, tvNew, tvConfirm, tvCancel;
    private TextView tvFolder;
    private ListView lvList;
    private ArrayList<File> fileList;
    private MyAdapter adapter;
    private File currentFile;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_folderselected);
        initView();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_folderselected_back);
        tvNew = (TextView) findViewById(R.id.tv_folderselected_new);
        tvConfirm = (TextView) findViewById(R.id.tv_folderselected_confirm);
        tvCancel = (TextView) findViewById(R.id.tv_folderselected_cancel);
        tvFolder = (TextView) findViewById(R.id.tv_folderselected_folder);
        lvList = (ListView) findViewById(R.id.lv_folderselected_list);

        tvBack.setOnClickListener(this);

        tvNew.setOnClickListener(this);

        tvConfirm.setOnClickListener(this);

        tvCancel.setOnClickListener(this);

        lvList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                currentFile = fileList.get(arg2);
                refreshList();
            }

        });

        fileList = new ArrayList<File>();
        adapter = new MyAdapter(fileList);
        lvList.setAdapter(adapter);
        currentFile = new File("/"); // 初始目录设置为根目录
        refreshList();
    }

    private void refreshList() {
        fileList.clear();
        File[] fl = currentFile.listFiles();
        if (fl != null) {
            Arrays.sort(fl, new Comparator<File>() {

                @Override
                public int compare(File lhs, File rhs) {
                    // TODO Auto-generated method stub
                    return 0;
                }
            });
            for (File f : fl) {
                if (f.isDirectory())// 是目录才加入，也就是过滤掉不是目录的文件
                    this.fileList.add(f);
            }
        }
        adapter.notifyDataSetChanged();
        tvFolder.setText(currentFile.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_folderselected_back:
                File parent = currentFile.getParentFile();
                if (parent == null)
                    return;
                currentFile = parent;
                refreshList();
                break;
            case R.id.tv_folderselected_cancel:
                Intent intent = new Intent();
                intent.putExtra("result", "");
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.tv_folderselected_confirm:
                Toast.makeText(mContext, "你选择了文件夹：" + currentFile.getPath(), Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent();
                intent1.putExtra("result", currentFile.getPath());
                this.setResult(RESULT_OK, intent1);
                this.finish();
                break;
            case R.id.tv_folderselected_new:
                Builder dialog = new Builder(FolderSelectedActivity.this);
                dialog.setMessage("新建文件夹");
                final EditText et = new EditText(FolderSelectedActivity.this);
                dialog.setView(et);
                dialog.setPositiveButton("Cancle", null);
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = new File(currentFile, et.getText().toString());
                        if (f.mkdir()) {
                            refreshList();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "文件夹创建失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.putExtra("result", "");
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    class MyAdapter extends BasicAdapter {

        private ArrayList<File> fileList;

        public MyAdapter(ArrayList<File> list) {
            this.fileList = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fileList.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView tv = null;
            if (view == null) {
                view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_folderselected_list, parent, false);
                tv = (TextView) view.findViewById(R.id.tv_folder_name);
                view.setTag(tv);
            } else {
                tv = (TextView) view.getTag();
            }
            tv.setText(fileList.get(position).getName());
            return view;
        }

    }

}
