package cn.appsys.dao.datadictionary;

import cn.appsys.pojo.DataDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 11:40
 * @params
 * @return
 */
public interface DataDictionaryMapper {
    public List<DataDictionary> getDataDictionaryList(@Param("typeCode")String typeCode)throws Exception;
}
