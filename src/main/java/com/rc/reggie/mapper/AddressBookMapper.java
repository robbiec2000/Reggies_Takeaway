package com.rc.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.reggie.entity.AddressBook;
import com.rc.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
