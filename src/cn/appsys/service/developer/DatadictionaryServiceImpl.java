package cn.appsys.service.developer;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/23 14:49
 * @params
 * @return
 */
@Service
@Transactional
public class DatadictionaryServiceImpl implements DatadictionaryService {
    @Resource
    private DataDictionaryMapper dictionaryMapper;
    @Override
    public List<DataDictionary> getDataDictionaryList(String typeCode) throws Exception {
        return dictionaryMapper.getDataDictionaryList(typeCode);
    }
}
