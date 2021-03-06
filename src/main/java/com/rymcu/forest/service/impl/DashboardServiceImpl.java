package com.rymcu.forest.service.impl;

import com.rymcu.forest.dto.admin.Dashboard;
import com.rymcu.forest.dto.admin.DashboardData;
import com.rymcu.forest.mapper.DashboardMapper;
import com.rymcu.forest.service.DashboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author ronger
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private DashboardMapper dashboardMapper;

    @Override
    public Dashboard dashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setCountUserNum(dashboardMapper.selectUserCount());
        dashboard.setNewUserNum(dashboardMapper.selectNewUserCount());
        dashboard.setCountArticleNum(dashboardMapper.selectArticleCount());
        dashboard.setNewArticleNum(dashboardMapper.selectNewArticleCount());
        dashboard.setCountViewNum(dashboardMapper.selectCountViewNum());
        dashboard.setTodayViewNum(dashboardMapper.selectTodayViewNum());
        return dashboard;
    }

    @Override
    public Map lastThirtyDaysData() {
        Map map = new HashMap(4);
        ArrayList<String> dates = new ArrayList(30);
        ArrayList<Integer> articleData = new ArrayList(30);
        ArrayList<Integer> userData = new ArrayList(30);
        ArrayList<Integer> visitData = new ArrayList(30);
        List<DashboardData> articles = dashboardMapper.selectLastThirtyDaysArticleData();
        List<DashboardData> users = dashboardMapper.selectLastThirtyDaysUserData();
        List<DashboardData> visits = dashboardMapper.selectLastThirtyDaysVisitData();
        LocalDate now = LocalDate.now().plusDays(1);
        LocalDate localDate = LocalDate.now().plusDays(-29);
        while (now.isAfter(localDate)) {
            String date = localDate.toString();
            dates.add(date);

            articles.forEach(article->{
                if (date.equals(article.getLabel())) {
                    articleData.add(article.getValue());
                    return;
                }
            });
            if (articleData.size() < dates.size()) {
                articleData.add(0);
            }

            users.forEach(user->{
                if (date.equals(user.getLabel())) {
                    userData.add(user.getValue());
                    return;
                }
            });
            if (userData.size() < dates.size()) {
                userData.add(0);
            }

            visits.forEach(visit->{
                if (date.equals(visit.getLabel())) {
                    visitData.add(visit.getValue());
                    return;
                }
            });
            if (visitData.size() < dates.size()) {
                visitData.add(0);
            }

            localDate = localDate.plusDays(1);
        }
        map.put("dates", dates);
        map.put("articles", articleData);
        map.put("users", userData);
        map.put("visits", visitData);
        return map;
    }

    @Override
    public Map history() {
        Map map = new HashMap(4);
        ArrayList<String> dates = new ArrayList(30);
        ArrayList<Integer> articleData = new ArrayList(30);
        ArrayList<Integer> userData = new ArrayList(30);
        ArrayList<Integer> visitData = new ArrayList(30);
        List<DashboardData> articles = dashboardMapper.selectHistoryArticleData();
        List<DashboardData> users = dashboardMapper.selectHistoryUserData();
        List<DashboardData> visits = dashboardMapper.selectHistoryVisitData();
        LocalDate now = LocalDate.now().plusMonths(1);
        LocalDate localDate = LocalDate.now().plusYears(-1).plusMonths(1);
        while (now.getYear() >= localDate.getYear()) {
            if (now.getYear() == localDate.getYear()) {
                if (now.getMonthValue() > localDate.getMonthValue()){
                    String date = localDate.getYear() + "-" + (localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue() : localDate.getMonthValue());
                    dates.add(date);

                    articles.forEach(article->{
                        if (date.equals(article.getLabel())) {
                            articleData.add(article.getValue());
                            return;
                        }
                    });
                    if (articleData.size() < dates.size()) {
                        articleData.add(0);
                    }

                    users.forEach(user->{
                        if (date.equals(user.getLabel())) {
                            userData.add(user.getValue());
                            return;
                        }
                    });
                    if (userData.size() < dates.size()) {
                        userData.add(0);
                    }

                    visits.forEach(visit->{
                        if (date.equals(visit.getLabel())) {
                            visitData.add(visit.getValue());
                            return;
                        }
                    });
                    if (visitData.size() < dates.size()) {
                        visitData.add(0);
                    }
                }
            } else {
                String date = localDate.getYear() + "-" + (localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue() : localDate.getMonthValue());
                dates.add(date);

                articles.forEach(article->{
                    if (date.equals(article.getLabel())) {
                        articleData.add(article.getValue());
                        return;
                    }
                });
                if (articleData.size() < dates.size()) {
                    articleData.add(0);
                }

                users.forEach(user->{
                    if (date.equals(user.getLabel())) {
                        userData.add(user.getValue());
                        return;
                    }
                });
                if (userData.size() < dates.size()) {
                    userData.add(0);
                }

                visits.forEach(visit->{
                    if (date.equals(visit.getLabel())) {
                        visitData.add(visit.getValue());
                        return;
                    }
                });
                if (visitData.size() < dates.size()) {
                    visitData.add(0);
                }
            }

            localDate = localDate.plusMonths(1);
        }
        map.put("dates", dates);
        map.put("articles", articleData);
        map.put("users", userData);
        map.put("visits", visitData);
        return map;
    }
}
