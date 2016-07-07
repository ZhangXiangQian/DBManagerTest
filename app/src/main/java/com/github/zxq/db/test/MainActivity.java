package com.github.zxq.db.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.android.zxq.db.DbException;
import org.android.zxq.db.DbManager;
import org.android.zxq.db.DbManagerImpl;

import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "Main";
    private EditText etName,etAge,etSex;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_to_db);

        etName  = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etSex = (EditText) findViewById(R.id.etSex);

        mListView = (ListView) findViewById(R.id.mListView);


        findViewById(R.id.btnInstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String sex = etSex.getText().toString();
                Student s = new Student();
                s.setName(name);
                s.setAge(age);
                s.setSex(sex);
                try {
                    DbManagerImpl.getInstance(config).saveOrUpdate(s);
                    Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "插入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (isNull(name) && isNull(age) && isNull(sex)) {
               // } else {
                    try {
                        List<Student> list = DbManagerImpl.getInstance(config).findAll(Student.class);
                        mListView.setAdapter(new TestAdapter(list));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
              //  }
            }
        });

    }

    private boolean isNull(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }
        return false;
    }

    class TestAdapter extends BaseAdapter {
        List<Student> list;

        TestAdapter(List<Student> list) {
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
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter_item, null);
            }
            TextView info = (TextView) convertView.findViewById(R.id.info);

            info.setText("姓名：" + list.get(position).getName() + "\n 年龄：" + list.get(position).getAge() + "\n性别：" + list.get(position).getSex());
            return convertView;
        }
    }

    private DbManager.DaoConfig config = new DbManager.DaoConfig()
            .setDbName("dbTest")
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });


}
