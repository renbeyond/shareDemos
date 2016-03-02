package com.example.demo.bass.afinal.activity;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.bass.afinal.entity.Users;


/**
 * 操作数据库
 * 
 * @author bass
 * 
 */
public class FinalDBTest extends FinalActivity {
	@ViewInject(id = R.id.btnInsert, click = "insert")
	Button btnInsert;
	@ViewInject(id = R.id.btnDel, click = "delete")
	Button btnDel;
	@ViewInject(id = R.id.btnUpdate, click = "update")
	Button btnUpdate;
	@ViewInject(id = R.id.tVDB)
	TextView tv;
	
	private FinalDb db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_db);
		db = FinalDb.create(this);
	}

	public void insert(View v) {
		Users users = new Users();
		users.setName("Bass");
		users.setPwd("123456");
		db.save(users);
		getUserAll();
	};

	public void delete(View v) {
		if (null != db ) {
			List<Users> list = db.findAll(Users.class);
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					db.delete(list.get(i));
				}
			}
		}
	};

	/**
	 * 修改用户
	 * @param v
	 */
	public void update(View v) {
		db.update(Users.class, "name = 1");
	};

	/**
	 * 读取用户
	 * @return
	 */
	public Users getUserInfo(int id) {
		return db.findById(id, Users.class);
	}
	
	/**
	 * 查询所有用户
	 */
	public void getUserAll(){
		if (null != db) {
			List<Users> list = db.findAll(Users.class);
			if (null != list) {
				System.out.println("===========size:"+list.size());
				tv.append(list.size() + "条;");
			}
		}
	}
	
	
}
