package com.tocheck.parent.common.util;

import com.tocheck.parent.common.ssdb.SSDB;
import com.tocheck.parent.common.ssdb.SSDBInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kchen on 2015-08-14.
 * 存放官网临时数据
 */
@Component
public class SSDBUtils {

    private static final Logger logger = LoggerFactory.getLogger(SSDBUtils.class);

    @Autowired
    private SSDBInfo ssdbInfo;

    private  SSDB ssdb;

    private void resetSsdb() throws Exception {
        if (ssdb != null) {
            ssdb.close();
        }
        ssdb = new SSDB(ssdbInfo.getHost(), ssdbInfo.getPort());
    }

    public synchronized void save(String key, String value) {
        try {
            ssdb.set(key, value);
        } catch (Exception e) {
            logger.error("第一次保存数据时重置ssdb出错",e);
            try {
                resetSsdb();
                ssdb.set(key, value);
            } catch (Exception e1) {
                logger.error("第二次保存数据时重置ssdb出错",e1);
            }
        }
    }
    
    public synchronized void saveThrowException(String key, String value) throws Exception {
        try {
            ssdb.set(key, value);
        } catch (Exception e) {
            logger.error("第一次保存数据时重置ssdb出错",e);
            resetSsdb();
            ssdb.set(key, value);
        }
    }

    public synchronized void save(String key, String value, Integer ttl) {
        try {
            ssdb.setx(key, value, ttl);
        } catch (Exception e) {
            logger.error("第一次保存数据时重置ssdb出错",e);
            try {
                resetSsdb();
                ssdb.setx(key, value, ttl);
            } catch (Exception e1) {
                logger.error("第二次保存数据时重置ssdb出错",e1);
            }
        }
    }

    public synchronized String getValue(String key) {
        try {
            byte[] content = ssdb.get(key);
            if (null != content) {
                return new String(content,"UTF-8");
            }
            return null;
        } catch (Exception e) {
            logger.error("第一次获取ssdb数据时,重置ssdb出错",e);
            try {
                resetSsdb();
                byte[] content = ssdb.get(key);
                if (null != content) {
                    return new String(content,"UTF-8");
                }
                return null;
            } catch (Exception e1) {
                logger.error("第二次获取ssdb数据时,重置ssdb出错",e1);
            }
        }
        return null;
    }

    public synchronized void del(String key) {
        try {
            ssdb.del(key);
        } catch (Exception e) {
            logger.error("第一次删除ssdb数据时,重置ssdb出错",e);
            try {
                resetSsdb();
                ssdb.del(key);
            } catch (Exception e1) {
                logger.error("第二次删除ssdb数据时,重置ssdb出错",e1);
            }
        }
    }
}