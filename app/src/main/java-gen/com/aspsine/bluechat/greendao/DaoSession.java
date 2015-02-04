package com.aspsine.bluechat.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.aspsine.bluechat.model.Notice;

import com.aspsine.bluechat.greendao.NoticeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noticeDaoConfig;

    private final NoticeDao noticeDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noticeDaoConfig = daoConfigMap.get(NoticeDao.class).clone();
        noticeDaoConfig.initIdentityScope(type);

        noticeDao = new NoticeDao(noticeDaoConfig, this);

        registerDao(Notice.class, noticeDao);
    }
    
    public void clear() {
        noticeDaoConfig.getIdentityScope().clear();
    }

    public NoticeDao getNoticeDao() {
        return noticeDao;
    }

}
