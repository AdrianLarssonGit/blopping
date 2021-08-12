package com.example.blopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController{

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Autowired
    ArticleRepo articleRepo;


    //List all articles
    @GetMapping("/articles")
    public String listAllArticles(Model model){
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
            if(articles.get(i).getId().toString().equals(cleanedPath)){
                finalList.add(articles.get(i));
            }
        }


            model.addAttribute("article", finalList.get(0));


        return "singleArticleViewForEdit";
    }

    @PostMapping("/updatearticle")
    public String updatearticle(Article article, Model model){
        String articleId = article.getId().toString();
        String articleText = article.getArticleText();
        EntityManager session = entityManagerFactory.createEntityManager();

        session.createNativeQuery("UPDATE artiklar SET article_text=:articleText WHERE id=:articleId")
                .setParameter("articleText", articleText)
                .setParameter("articleId", articleId);

        articleRepo.save(article);


        return listAllArticles(model);
    }

}
