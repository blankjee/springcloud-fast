package org.blankjee.cloudfast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.blankjee.cloudfast.entity.VacationOrder;
import org.blankjee.cloudfast.entity.VacationOrderVo;

/**
 * @Author blankjee
 * @Date 2020/7/7 19:40
 */
public interface VacationOrderMapper extends BaseMapper<VacationOrder> {

    /**
     * 此处的方法是关联了Mapper.xml文件的，路径：src/main/resources/mapper/VacationOrderMapper.xml
     * @param page
     * @param userId
     * @return
     */
    Page<VacationOrderVo> queryVacationOrder(Page<VacationOrder> page, @Param("userId") String userId);

}
