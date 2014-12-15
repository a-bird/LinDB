package com.zhulin.llhibernet;

public class SessionFactory {

	private ISqliteDataBase mDataBase;

	public SessionFactory(ISqliteDataBase mDataBase) {
		this.mDataBase = mDataBase;
	}

	public <T> ISession<T> openSession() {
        Session session = new Session();
        session.initDabaBase(mDataBase);
		return session;
	}
}
