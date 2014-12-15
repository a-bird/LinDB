package com.zhulin.llhibernet.config;

import com.zhulin.llhibernet.ISession;
import com.zhulin.llhibernet.SessionFactory;

public interface IConfiguration {
	public IConfiguration configure();
    public SessionFactory buildSessionFactory();
}
