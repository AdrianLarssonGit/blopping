package com.example.blopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;

    //List all articles
    @GetMapping("/articles")
    public String listedUsers(Model model){
        List<Article> listArticle = articleRepo.findAll();
        model.addAttribute("listArticles", listArticle);

        return "articlesToEdit";
    }

    //Create new article
    @GetMapping("/skapa")
    public String createArticle(Model model){
        model.addAttribute("article", new Article());
        return "skapaArtikel";
    }

    //Saved newly created article and returns home view
    @PostMapping("/sparaartikel")
    public String saveArticleToRepoAndRedirect(Article article){
        articleRepo.save(article);

        return "home";
    }

    @GetMapping("/editarticle/*")
    public String articleedit(Model model, HttpServletRequest httpServletRequest){
        List<Article> articles = articleRepo.findAll();
        String pathInfo = httpServletRequest.getRequestURI();



        String cleanedPath = pathInfo.replaceAll("\\D+","");
        List<Article> finalList = new ArrayList<>();

        for(int i = 0; i < articles.size(); i++){
            System.out.println(articles);
            if(articles.get(i).getId().toString().equals(cleanedPath)){
                finalList.add(articles.get(i));
            }
        }

        for(int i = 0; i < finalList.size(); i++){
            model.addAttribute("article", finalList);
        }

        return "singleArticleViewForEdit";
    }

    @PostMapping("/updatearticle")
    public String updatearticle(Article article, Model model){
        Article finishedArticle = article;

        //Delete old version

        return "articlesToEdit";
    }

}
