package com.lyb.olinemusicserver.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    /**
     * 支持的字段类型
     */
    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATE_TIME_FIELD = "updateTime";

    /**
     * 插入填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
            Date now =  new Date();

        // 使用更安全的方法填充
        if (hasField(metaObject, CREATE_TIME_FIELD)) {
            this.strictInsertFill(metaObject, CREATE_TIME_FIELD, Date.class, now);
        }
        if (hasField(metaObject, UPDATE_TIME_FIELD)) {
            this.strictInsertFill(metaObject, UPDATE_TIME_FIELD, Date.class, now);
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Date now =new  Date();

        if (hasField(metaObject, UPDATE_TIME_FIELD)) {
            this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD, Date.class, now);
        }
    }

    /**
     * 检查对象是否包含指定字段
     */
    private boolean hasField(MetaObject metaObject, String fieldName) {
        return metaObject.hasGetter(fieldName);
    }

}