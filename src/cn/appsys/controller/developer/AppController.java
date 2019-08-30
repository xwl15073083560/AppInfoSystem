package cn.appsys.controller.developer;

import cn.appsys.controller.BaseController;
import cn.appsys.pojo.*;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DatadictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.Pager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小未来leo
 * @date 2019/8/23 10:52
 * @params
 * @return
 */
@Controller
@RequestMapping(value = "/dev/flatform/app")
public class AppController extends BaseController {
    private Logger logger=Logger.getLogger(AppController.class);
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private DatadictionaryService datadictionaryService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;
    /**
     *分页查询AppInfo信息
     * @return
     */
    @RequestMapping(value = "/list")
    public Object getAppInfoList(Model model, @RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
                                 @RequestParam(value="queryStatus",required=false) String _queryStatus,
                                 @RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
                                 @RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
                                 @RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
                                 @RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
                                 @RequestParam(value="pageIndex",required=false) String pageIndex){
        logger.info("getAppInfoList -- > querySoftwareName: " + querySoftwareName);
        logger.info("getAppInfoList -- > queryStatus: " + _queryStatus);
        logger.info("getAppInfoList -- > queryCategoryLevel1: " + _queryCategoryLevel1);
        logger.info("getAppInfoList -- > queryCategoryLevel2: " + _queryCategoryLevel2);
        logger.info("getAppInfoList -- > queryCategoryLevel3: " + _queryCategoryLevel3);
        logger.info("getAppInfoList -- > queryFlatformId: " + _queryFlatformId);
        logger.info("getAppInfoList -- > pageIndex: " + pageIndex);
        Integer devId=((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
        List<AppInfo> appInfoList=null;
        List<DataDictionary> statusList=null;
        List<DataDictionary> flatFormList=null;
        //列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
        List<AppCategory> categoryLevel1List=null;
        List<AppCategory> categoryLevel2List=null;
        List<AppCategory> categoryLevel3List=null;
        //页面容量
        int pageSize=Constants.pageSize;
        //当前页码
        Integer currentPageNo=1;
        if (pageIndex!=null){
            try {
                currentPageNo=Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Integer queryStatus=null;
        if (_queryStatus!=null&&!"".equals(_queryStatus)){
            queryStatus=Integer.parseInt(_queryStatus);
        }
        Integer queryCategoryLevel1 = null;
        if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
            queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
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
            totalCount=appInfoService.getAppInfoCount(querySoftwareName,queryStatus,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,devId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //总页数
        Pager pages=new Pager();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount=pages.getTotalPageCount();
        //控制首页和尾页
        if (currentPageNo<1){
            currentPageNo=1;
        }else if (currentPageNo>totalPageCount){
            currentPageNo=totalPageCount;
        }
        try {
            appInfoList=appInfoService.getAppInfoList(querySoftwareName,queryStatus,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,devId,(currentPageNo-1)*pageSize,pageSize);
            statusList=this.getDataDictionaryList("APP_STATUS");
            flatFormList=this.getDataDictionaryList("APP_FLATFORM");
            categoryLevel1List=appCategoryService.getAppCategoryListByParentId(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("appInfoList", appInfoList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("flatFormList", flatFormList);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("pages", pages);
        model.addAttribute("queryStatus", queryStatus);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
        model.addAttribute("queryFlatformId", queryFlatformId);
        //二级分类列表和三级分类列表---回显
        if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
            categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
            model.addAttribute("categoryLevel2List", categoryLevel2List);
        }
        if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
            categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
            model.addAttribute("categoryLevel3List", categoryLevel3List);
        }
        return "devoloper/appinfolist";
    }

    /**
     * 根据typeCode返回数据字典列表
     * @param typeCode
     * @return
     */
    public List<DataDictionary> getDataDictionaryList(String typeCode){
        List<DataDictionary> dataDictionaryList=null;
        try {
            dataDictionaryList=datadictionaryService.getDataDictionaryList(typeCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataDictionaryList;
    }

    /**
     * 根据parentID查询分类级别列表
     * @param pid
     * @return
     */
    public List<AppCategory> getCategoryList (String pid){
        List<AppCategory> categoryLevelList = null;
        try {
            categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryLevelList;
    }

    /**
     * ajax返回分类级别列表
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/categorylevellist.json",method = RequestMethod.GET)
    public Object getAppCategoryListLevel1(@RequestParam String pid){
        logger.debug("categoryLevel1List ====================pid:"+pid);
        if ("".equals(pid))pid=null;
        return getCategoryList(pid);
    }

    /**
     * 跳转到新增APP信息页面
     * @return
     */
    @RequestMapping(value = "/appinfoadd")
    public Object appinfoadd(@ModelAttribute("appinfo")AppInfo appInfo){
        return "devoloper/appinfoadd";
    }

    /**
     * ajax获得平台列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/datadictionarylist.json",method = RequestMethod.GET)
    public Object datadictionarylist(@RequestParam String tcode){
        return getDataDictionaryList(tcode);
    }
    /**
     * 验证APKName是否存在
     */
    @ResponseBody
    @RequestMapping(value = "/apkexist.json")
    public Object checkAPKNameExists(@RequestParam String APKName){
        AppInfo appInfo=appInfoService.getAppInfoByAPKName(APKName,null);
        Map<String,String> map=new HashMap<>();
        if (APKName==null||"".equals(APKName)){
            map.put("APKName","empty");
        }else if (appInfo==null){
            map.put("APKName","noexist");
        }else if(appInfo!=null){
            map.put("APKName","exist");
        }
        return JSONArray.toJSONString(map);
    }
    /**
     * 添加AppInfo信息
     */
    @RequestMapping(value = "/appinfoaddsave",method = RequestMethod.POST)
    public Object addAppInfo(AppInfo appInfo, @RequestParam(value = "a_logoPicPath",required = false)MultipartFile attach){
        String logoPicPath=null;
        String logoLocPath=null;
        if (!attach.isEmpty()){
            String path=session.getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
            String oldName=attach.getOriginalFilename();
            String suffix= FilenameUtils.getExtension(oldName);
            int fileSize=500000;
            if (attach.getSize()>fileSize){
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
            }else if (suffix.equalsIgnoreCase("jpg")||suffix.equalsIgnoreCase("png")||
                      suffix.equalsIgnoreCase("jpeg")||suffix.equalsIgnoreCase("pneg")){
                String fileName=appInfo.getAPKName()+".jpg";
                File file=new File(path,fileName);
                if (!file.exists()){
                    file.mkdirs();
                }
                try {
                    attach.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appinfoadd";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+File.separator+fileName;
            }else{
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "developer/appinfoadd";
            }
        }
        appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setStatus(1);
        if (appInfoService.addAppInfo(appInfo)){
            return "redirect:/dev/flatform/app/list";
        }
        return "developer/appinfoadd";
    }

    @RequestMapping(value = "/appinfomodify",method = RequestMethod.GET)
    public Object appinfomodify(@RequestParam("id")String id,@RequestParam(value="error",required= false)String fileUploadError,Model model){
        if(null != fileUploadError && fileUploadError.equals("error1")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        }else if(null != fileUploadError && fileUploadError.equals("error2")){
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        }else if(null != fileUploadError && fileUploadError.equals("error3")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }else if(null != fileUploadError && fileUploadError.equals("error4")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_4;
        }
        AppInfo appInfo=null;
        try {
            appInfo=appInfoService.getAppInfoByAPKName(null,Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute("fileUploadError",fileUploadError);
        model.addAttribute("appInfo",appInfo);
        return "devoloper/appinfomodify";
    }
    /**
     * 根据Appinfo id修改清空2个路径
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delfile.json",method = RequestMethod.GET)
    public Object delFile(@RequestParam(value = "id",required = false)String id,
                          @RequestParam(value = "flag",required = false)String flag){
        HashMap<String,String> map=new HashMap<>();
        String fileLocPath=null;
        String path=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
        if (StringUtils.isNullOrEmpty(id)||StringUtils.isNullOrEmpty(flag)){
            map.put("result","failed");
        }else if("logo".equals(flag)){
            fileLocPath=appInfoService.getAppInfoByAPKName(null,Integer.parseInt(id)).getLogoLocPath();
            File file=new File(fileLocPath);
            if (file.exists()){
                if (file.delete()){
                    if (appInfoService.deleteAppLogo(Integer.parseInt(id))){
                        map.put("result","success");
                    }
                }
            }
        }else if("apk".equals(flag)){
            try {
                fileLocPath=(appVersionService.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
                File file=new File(fileLocPath);
                if (file.exists()){
                    if (file.delete()){
                        if (appVersionService.deleteApkFile(Integer.parseInt(id))){
                            map.put("result", "success");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 修改Appinfo信息
     * @param appInfo
     * @param attach
     * @return
     */
    @RequestMapping(value = "/appinfomodifysave",method = RequestMethod.POST)
    public Object appinfomodifysave(AppInfo appInfo,@RequestParam(value = "attach",required = false)MultipartFile attach){
        String logoPicPath=null;
        String logoLocPath=null;
        if (!attach.isEmpty()){
            String path=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldName=attach.getOriginalFilename();
            String suffix=FilenameUtils.getExtension(oldName);
            int fileSize=500000;
            if (attach.getSize()>fileSize){
                return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()+"&error=error4";
            }else if ("jpg".equalsIgnoreCase(suffix)||suffix.equalsIgnoreCase("png")||
                    suffix.equalsIgnoreCase("jpeg")||suffix.equalsIgnoreCase("pneg")){
                String fileName=appInfo.getAPKName()+".jpg";
                File file=new File(path,fileName);
                if (!file.exists()){
                    file.mkdirs();
                }
                try {
                    attach.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
                            +"&error=error2";
                }
                logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath=path+File.separator+fileName;
            }else {
                return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
                        +"&error=error3";
            }
        }
        appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setModifyDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        if (appInfoService.modify(appInfo)){
            return "redirect:/dev/flatform/app/list";
        }
        return "developer/appinfomodify";
    }

    @RequestMapping(value = "/appversionadd",method = RequestMethod.GET)
    public Object appversionadd(@RequestParam(value = "id")String appid,
                                @RequestParam(value = "error",required = false)String fileUploadError,
                                AppVersion appVersion,Model model){
        logger.debug("fileUploadError============> " + fileUploadError);
        if(null != fileUploadError && fileUploadError.equals("error1")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        }else if(null != fileUploadError && fileUploadError.equals("error2")){
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        }else if(null != fileUploadError && fileUploadError.equals("error3")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }
        appVersion.setAppId(Integer.parseInt(appid));
        List<AppVersion> appVersionList=null;
        try {
            appVersionList=appVersionService.getAppVersionList(Integer.parseInt(appid));
            appVersion.setAppName(appInfoService.getAppInfoByAPKName(null,Integer.parseInt(appid)).getSoftwareName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appVersion);
        model.addAttribute("fileUploadError",fileUploadError);
        return "devoloper/appversionadd";
    }

    /**
     * 保存新增appversion数据（子表）-上传该版本的apk包
     * @param appVersion
     * @param attach
     * @return
     */
    @RequestMapping(value = "/addversionsave",method = RequestMethod.POST)
    public Object addversionsave(AppVersion appVersion,
                                 @RequestParam(value="a_downloadLink",required= false) MultipartFile attach){
        String downloadLink=null;
        String apkLocPath=null;
        String apkFileName=null;
        if (!attach.isEmpty()){
            String path=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldName=attach.getOriginalFilename();
            String suffix=FilenameUtils.getExtension(oldName);
            if ("apk".equalsIgnoreCase(suffix)){
                String apkName=null;
                try {
                    apkName=appInfoService.getAppInfoByAPKName(null,appVersion.getAppId()).getAPKName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null==apkName||"".equals(apkName)){
                    return "redirect:/dev/flatform/app/appversionadd?id="+appVersion.getAppId()
                            +"&error=error1";
                }
                apkFileName=apkName+"-"+appVersion.getVersionNo()+".apk";
                File file=new File(path,apkFileName);
                if (!file.exists()){
                    file.mkdirs();
                }
                try {
                    attach.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/flatform/app/appversionadd?id="+appVersion.getAppId()
                            +"&error=error2";
                }
                downloadLink=request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath=path+File.separator+apkFileName;
            }else{
                return "redirect:/dev/flatform/app/appversionadd?id="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setCreationDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        if (appVersionService.add(appVersion)){
            return "redirect:/dev/flatform/app/list";
        }
        return "redirect:/dev/flatform/app/appversionadd?id="+appVersion.getAppId();
    }

    /**
     * 跳转到Appversion修改页面
     * @param vid
     * @param aid
     * @param fileUploadError
     * @param model
     * @return
     */
    @RequestMapping(value = "/appversionmodify",method = RequestMethod.GET)
    public Object appversionmodify(@RequestParam(value = "vid")String vid,@RequestParam(value = "aid")String aid,
                                   @RequestParam(value="error",required= false)String fileUploadError,
                                   Model model){
        AppVersion appVersion = null;
        List<AppVersion> appVersionList = null;
        if(null != fileUploadError && fileUploadError.equals("error1")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        }else if(null != fileUploadError && fileUploadError.equals("error2")){
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        }else if(null != fileUploadError && fileUploadError.equals("error3")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }
        try {
            appVersion = appVersionService.getAppVersionById(Integer.parseInt(vid));
            appVersionList = appVersionService.getAppVersionList(Integer.parseInt(aid));
        }catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute(appVersion);
        model.addAttribute("appVersionList",appVersionList);
        model.addAttribute("fileUploadError",fileUploadError);
        return "devoloper/appversionmodify";
    }

    /**
     * 修改AppVersion信息
     * @param appVersion
     * @param attach
     * @return
     */
    @RequestMapping(value = "/appversionmodifysave",method = RequestMethod.POST)
    public Object appversionmodifysave(AppVersion appVersion,@RequestParam(value = "attach",required = false)MultipartFile attach){
        String downloadLink=null;
        String apkLocPath=null;
        String apkFileName=null;
        if (!attach.isEmpty()){
            String path=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldName=attach.getOriginalFilename();
            String suffix=FilenameUtils.getExtension(oldName);
            if ("apk".equalsIgnoreCase(suffix)){
                String apkName=null;
                try {
                    apkName=appInfoService.getAppInfoByAPKName(null,appVersion.getAppId()).getAPKName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null==apkName||"".equals(apkName)){
                    return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error1";
                }
                apkFileName=apkName+"-"+appVersion.getVersionNo()+".apk";
                File file=new File(path,apkFileName);
                if (!file.exists()){
                    file.mkdirs();
                }
                try {
                    attach.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error2";
                }
                downloadLink=request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath=path+File.separator+apkFileName;
            }else{
                return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
                        +"&aid="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setModifyDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if(appVersionService.modify(appVersion)){
                return "redirect:/dev/flatform/app/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "devoloper/appversionmodify";
    }

    /**
     * 查看AppinfoVersion、Appinfo信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/appview/{id}",method = RequestMethod.GET)
    public Object appview(@PathVariable String id,Model model){
        AppInfo appInfo=appInfoService.getAppInfoByAPKName(null,Integer.parseInt(id));
        List<AppVersion> appVersionList=null;
        try {
            appVersionList=appVersionService.getAppVersionList(appInfo.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appInfo);
        return "devoloper/appinfoview";
    }

    /**
     * 删除appinfo以及所有版本信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delapp.json",method = RequestMethod.GET)
    public Object delApp(@RequestParam(value = "id")String id){
        HashMap<String,String> map=new HashMap<>();
        if (StringUtils.isNullOrEmpty(id)){
            map.put("delResult","noexist");
        }else{
            try {
                if (appInfoService.appsysdeleteAppById(Integer.parseInt(id))){
                    map.put("delResult","true");
                }else{
                    map.put("delResult","false");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return JSONArray.toJSONString(map);
    }

    /**
     * 上下架
     * @return
     */
    @RequestMapping(value = "/{appid}/sale",method = RequestMethod.PUT)
    @ResponseBody
    public Object sale(@PathVariable("appid")String appid){
        HashMap<String,String> map=new HashMap<>();
        Integer appidInteger=0;
        try {
            appidInteger=Integer.parseInt(appid);
        } catch (NumberFormatException e) {
            appidInteger=0;
        }
        map.put("errorCode","0");
        map.put("appId",appid);
        if (appidInteger>0){
            try {
                DevUser devUser=(DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
                AppInfo appInfo=new AppInfo();
                appInfo.setId(appidInteger);
                appInfo.setModifyBy(devUser.getId());
                if (appInfoService.appsysUpdateSaleStatusByAppId(appInfo)){
                    map.put("resultMsg","success");
                }else{
                    map.put("resultMsg","failed");
                }
            } catch (Exception e) {
                map.put("errorCode","exception000001");
            }
        }else{
            map.put("errorCode","param000001");
        }
        return map;
    }
}
