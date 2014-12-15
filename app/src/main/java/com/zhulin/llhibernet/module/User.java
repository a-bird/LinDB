package com.zhulin.llhibernet.module;

import com.zhulin.llhibernet.common.TableField;
import com.zhulin.llhibernet.common.Table;

@Table(tableName="User")
public class User {
	
	@TableField(key="id", type="String",length=10)
	public String id;
	@TableField(key="name", type="String",length=20)
	public String name;
    @TableField(key="sex", type="String",length=20)
	public String sex;
    @TableField(key="age", type="int",length=10)
    public int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
