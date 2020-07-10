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

    // Page<VacationOrderVo> queryVacationOrder(Page<VacationOrderVo> page, @Param("userId") String userId);

}
