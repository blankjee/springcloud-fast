package org.blankjee.cloudfast.common.config;

import cn.hutool.core.util.IdUtil;
import org.activiti.engine.impl.cfg.IdGenerator;

/**
 * @Author blankjee
 * @Date 2020/7/6 15:10
 */
public class ActivitiIdGenerate implements IdGenerator {
    @Override
    public String getNextId() {
        // 生成一批自增且唯一的ID，UUID可以保证唯一性，但是没有顺序，因此这里选择雪花算法，只要时间不会播，理论上不会重复，当然必须用单线程来跑.
        return String.valueOf(IdUtil.getSnowflake(0, 0).nextId());
    }
}
