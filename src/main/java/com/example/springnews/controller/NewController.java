package com.example.springnews.controller;

import com.example.springnews.model.News;
import com.example.springnews.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class NewController {

    @Autowired
    NewsRepository newsR;

    @GetMapping(value = "/newsmain") //URL 입력하여 요청
    public ModelAndView newsMain(){ //전체 뉴스 출력

        ModelAndView mav = new ModelAndView();
        List<News> newsList = newsR.findAll();

        if(newsList.size() != 0){
            mav.addObject("listAll", newsList);
        } else {
            mav.addObject("msg", "결과가 없습니다");
        }

        mav.setViewName("newsView");

        return mav;
    }

    @GetMapping(value = "/one") //뉴스 제목을 선택하여 요청
    public ModelAndView newsSelectOne(int id){ //뉴스 Id로 해당 뉴스 출력
        ModelAndView mav = new ModelAndView();

        try{
            mav.addObject("listAll", newsR.findAll());
            mav.addObject("selectNews", newsR.findById(id).get());
        } catch (Exception e){
            mav.addObject("msg", "열람 중 오류가 발생했습니다");
        }

        mav.setViewName("newsView");

        return mav;
    }

    @GetMapping(value = "/delete") //삭제 버튼을 클릭하여 요청
    public ModelAndView newsDelete(int id){ //뉴스 id로 뉴스 삭제
        ModelAndView mav = new ModelAndView();

        try{
            newsR.deleteById(id);
            mav.addObject("listAll", newsR.findAll());
        } catch (Exception e){
            mav.addObject("msg", "삭제를 처리하는 동안 오류 발생");
        }

        mav.setViewName("newsView");

        return mav;
    }

    @GetMapping(value = "/search") //검색 요청
    @ResponseBody
    public ModelAndView newsSearch(String keyword){ //전달된 검색어로 뉴스글 내용에서 검색하여 결과출력

        ModelAndView mav = new ModelAndView();
        List<News> searchList = newsR.findByTitleContains(keyword);

        if(searchList.size() != 0){
            mav.addObject("listAll", searchList);
            //mav.addObject("button", "메인화면으로");
        } else {
            mav.addObject("msg", "검색 결과가 없습니다");
        }

        mav.setViewName("newsView");

        return mav;
    }

    @GetMapping(value = "/writer") //리스트에 출력된 작성자 이름을 클릭하여 요청
    @ResponseBody
    public ModelAndView newsWriter(String writer){ //작성자가 작성한 뉴스 글만 출력
        ModelAndView mav = new ModelAndView();
        List<News> writeList = newsR.findByWriterContains(writer);

        if(writeList.size() != 0){
            mav.addObject("listAll", writeList);
            //mav.addObject("button", "메인화면으로");
        } else {
            mav.addObject("msg", "검색 결과가 없습니다");
        }

        mav.setViewName("newsView");

        return mav;
    }

    @PostMapping(value = "/insert")
    @Transactional
    public ModelAndView newsInsert(News vo){ //뉴스 작성

        ModelAndView mav = new ModelAndView();

        try{
            newsR.save(vo);
            mav.addObject("listAll", newsR.findAll());
        } catch(Exception e){
            mav.addObject("msg", "글 작성을 처리하는 동안 오류가 발생");
        }

        mav.setViewName("newsView");
        return mav;
    }

    @PostMapping(value = "/update")
    @Transactional
    public ModelAndView newsUpdate(News vo){ //뉴스 수정
        ModelAndView mav = new ModelAndView();
        try {
            News oldvo = newsR.findById(vo.getId()).get();
            oldvo.setWriter(vo.getWriter());
            oldvo.setTitle(vo.getTitle());
            oldvo.setContent(vo.getContent());
            newsR.save(oldvo);

            mav.addObject("listAll", newsR.findAll());
        } catch(Exception e) {
            mav.addObject("msg", "글 작성을 수정하는 동안 오류 발생");
        }
        mav.setViewName("newsView");
        return mav;
    }
}
