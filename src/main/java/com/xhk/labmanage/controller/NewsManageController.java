package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.model.News;
import com.xhk.labmanage.rmodel.*;
import com.xhk.labmanage.service.NewsManageService;
import com.xhk.labmanage.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by xhk on 2018/4/15
 */
@Controller
public class NewsManageController {
    private static Logger logger = LoggerFactory.getLogger(NewsManageController.class);

    @Autowired
    private NewsManageService newsManageService;

    /**
     * 进入新闻管理页面，翻页
     * @param whichPage
     * @param perCount
     * @param modelMap
     * @return
     */
    @RequestMapping("news-manage")
    @RetFormat(isPage = true)
    public String newsManage(Integer whichPage, Integer perCount,ModelMap modelMap){
        whichPage = whichPage == null ? 1 : whichPage;
        perCount = perCount == null ? 10 : perCount;
        NewsGetRequest request = new NewsGetRequest();
        request.setNum(perCount);
        request.setPage(whichPage);
        NewsGetResponse response = newsManageService.getNewsList(request);
        logger.info(JsonUtil.getJsonFromObject(response));
        modelMap.addAttribute("whichPage",whichPage);
        modelMap.addAttribute("perCount",perCount);
        modelMap.addAttribute("allCount",response.getTotalNum());
        modelMap.addAttribute("list",response.getNewsList());
        return "page/news-manage";
    }

    @RequestMapping("news-change-page")
    @RetFormat(isPage = true)
    public String newsChange(Integer newsId ,ModelMap modelMap){
        if (newsId != null){
            //修改新闻
            NewsGetByIdRequest request = new NewsGetByIdRequest();
            request.setNewsId(newsId);
            NewsGetByIdResponse response = newsManageService.getNewsById(request);
            modelMap.addAttribute("news",response.getNews());
        }
        //为空新增新闻
        return "page/news-change";
    }

    @RequestMapping("news-change")
    @ResponseBody
    @RetFormat
    public Object newsAdd(News news){
        if (news == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        if (news.getId() == null){
            // 新增新闻
            NewsAddRequest request = new NewsAddRequest();
            request.setContent(news.getContent());
            request.setEcontent(news.getEcontent());
            request.setTitle(news.getTitle());
            request.setEtitle(news.getEtitle());
            request.setUrl(news.getUrl());
            newsManageService.addNews(request);
        }
        else{
            //修改新闻
            NewsUpdateRequest request = new NewsUpdateRequest();
            request.setContent(news.getContent());
            request.setEcontent(news.getEcontent());
            request.setTitle(news.getTitle());
            request.setEtitle(news.getEtitle());
            request.setUrl(news.getUrl());
            request.setNewsId(news.getId());
            newsManageService.updateNews(request);
        }
        System.out.println(JsonUtil.getJsonFromObject(news));
        return "success";
    }

    @RequestMapping("news-delete")
    @ResponseBody
    @RetFormat
    public Object deleteNews(Integer newsId){
        if(newsId == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        NewsDeleteRequest request = new NewsDeleteRequest();
        request.setNewsId(newsId);
        newsManageService.deleteNews(request);
        return "success";
    }
}
