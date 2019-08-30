package cn.appsys.controller.backend;

import cn.appsys.controller.BaseController;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AppService;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DatadictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小未来leo
 * @date 2019/8/27 15:43
 * @params
 * @return
 */
@Controller
@RequestMapping(value = "/manager/backend/app")
public class AppCheckController extends BaseController {

    private Logger logger=Logger.getLogger(AppCheckController.class);
    @Resource
    private AppService appService;
    @Resource
    private DatadictionaryService datadictionaryService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;

    @RequestMapping(value = "/list")
    public Object getAppInfoList(Model model,
                                 @RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
                                 @RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
                                 @RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
                                 @RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
                                 @RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
                                 @RequestParam(value="pageIndex",required=false) String pageIndex){
        logger.info("getAppInfoList -- > querySoftwareName: " + querySoftwareName);
        logger.info("getAppInfoList -- > queryCategoryLevel1: " + _queryCategoryLevel1);
        logger.info("getAppInfoList -- > queryCategoryLevel2: " + _queryCategoryLevel2);
        logger.info("getAppInfoList -- > queryCategoryLevel3: " + _queryCategoryLevel3);
        logger.info("getAppInfoList -- > queryFlatformId: " + _queryFlatformId);
        logger.info("getAppInfoList -- > pageIndex: " + pageIndex);

        List<AppInfo> appInfoList=null;
        List<DataDictionary> flatFormList=null;
        List<AppCategory> categoryLevel1List=null;
        List<AppCategory> categoryLevel2List=null;
        List<AppCategory> categoryLevel3List=null;
        //页面容量
        int pageSize= Constants.pageSize;
        //当前页码
        Integer currentPageNo=1;
        if (pageIndex!=null){
            try {
                currentPageNo=Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Integer queryCategoryLevel1=null;
        if (_queryCategoryLevel1!=null&&!"".equals(_queryCategoryLevel1)){
            queryCategoryLevel1=Integer.parseInt(_queryCategoryLevel1);
        }
        Integer queryCategoryLevel2 = null;
        if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
            queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
        }
        Integer queryCategoryLevel3 = null;
        if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
            queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
        }
        Integer queryFlatformId = null;
        if(_queryFlatformId != null && !_queryFlatformId.equals("")){
            queryFlatformId = Integer.parseInt(_queryFlatformId);
        }
        //总数量
        int totalCount=0;
        try {
            totalCount=appService.getAppInfoCount(querySoftwareName,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pager pages=new Pager();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount=pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        try {
            appInfoList=appService.getAppInfoList(querySoftwareName,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,currentPageNo,pageSize);
            flatFormList=getDataDictionaryList("APP_FLATFORM");
            categoryLevel1List=appCategoryService.getAppCategoryListByParentId(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("appInfoList",appInfoList);
        model.addAttribute("flatFormList",flatFormList);
        model.addAttribute("categoryLevel1List",categoryLevel1List);
        model.addAttribute("pages",pages);
        model.addAttribute("querySoftwareName",querySoftwareName);
        model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
        model.addAttribute("queryFlatformId",queryFlatformId);
        //二级分类列表和三级分类列表---回显
        if (queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)) {
            try {
                categoryLevel2List=getCategoryList(queryCategoryLevel1.toString());
                model.addAttribute("categoryLevel2List",categoryLevel2List);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (queryCategoryLevel3 != null && !"".equals(queryCategoryLevel3)) {
            try {
                categoryLevel3List=getCategoryList(queryCategoryLevel2.toString());
                model.addAttribute("categoryLevel3List",categoryLevel3List);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "backend/applist";
    }

    public List<AppCategory> getCategoryList (String pid){
        List<AppCategory> categoryLevelList = null;
        try {
            categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return categoryLevelList;
    }

    public List<DataDictionary> getDataDictionaryList(String typeCode){
        List<DataDictionary> dataDictionaryList = null;
        try {
            dataDictionaryList = datadictionaryService.getDataDictionaryList(typeCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataDictionaryList;
    }

    @RequestMapping(value = "/categorylevellist",method = RequestMethod.GET)
    @ResponseBody
    public Object categorylevellist(@RequestParam String pid){
        if ("".equals(pid))pid=null;
        return getCategoryList(pid);
    }

    @RequestMapping(value = "/check")
    public Object check(@RequestParam(value="aid",required=false) String appId,
                        @RequestParam(value="vid",required=false) String versionId,
                        Model model){
        AppInfo appInfo=null;
        AppVersion appVersion=null;
        try {
            appInfo=appService.getAppInfo(Integer.parseInt(appId));
            appVersion=appVersionService.getAppVersionById(Integer.parseInt(versionId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute(appVersion);
        model.addAttribute(appInfo);
        return "backend/appcheck";
    }

    @RequestMapping(value = "/checksave",method = RequestMethod.POST)
    public String checkSave(AppInfo appInfo){
        try {
            if (appService.updateStatus(appInfo.getStatus(),appInfo.getId())){
                return "redirect:/manager/backend/app/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "backend/appcheck";
    }
}
