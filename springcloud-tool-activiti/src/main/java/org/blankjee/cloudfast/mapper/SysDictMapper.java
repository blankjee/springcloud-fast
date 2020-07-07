package org.blankjee.cloudfast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.blankjee.cloudfast.entity.SysDict;

import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/6 20:08
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 根据字典类型编码查询字典信息
     * @param dictTypeCode
     * @return
     */
    @Select("SELECT t1.*, t2.DICT_TYPE_NAME from sys_dict t1, sys_dict_type t2 WHERE t1.DICT_TYPE_CODE = t2.DICT_TYPE_CODE AND t1.DICT_TYPE_CODE = #{dictTypeCode}")
    List<SysDict> querySysDictInfo(int dictTypeCode);
}
