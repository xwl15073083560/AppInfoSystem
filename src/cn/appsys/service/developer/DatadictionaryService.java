package cn.appsys.service.developer;

import cn.appsys.pojo.DataDictionary;

import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 13:36
 * @params
 * @return
 */
public interface DatadictionaryService {
    public List<DataDictionary> getDataDictionaryList(String typeCode)throws Exception;
}
