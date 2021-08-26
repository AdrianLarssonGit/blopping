package com.example.blopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    ArticleRepo articleRepo;


    //List all articles
    @GetMapping("/articles")
    public String listAllArticles(Model model){
        List<Article> listArticle = articleRepo.findAll();
        model.addAttribute("listArticles", listArticle);

        return "articlesMainList";
    }

    //Create new article
    @GetMapping("/skapa")
    public String createArticle(Model model){
        model.addAttribute("article", new Article());
        return "skapaArtikel";
    }

    //Saved newly created article and returns home view
    @PostMapping("/sparaartikel")
    public String saveArticleToRepoAndRedirect(@AuthenticationPrincipal CustomUserDetails userPrincipal, Article article){
        article.setEmailOfAuthor(userPrincipal.getUsername());
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
    public String updatearticle(@AuthenticationPrincipal CustomUserDetails userPrincipal, Article article, Model model){
        String articleId = article.getId().toString();
        String articleText = article.getArticleText();
        String authorOfArticle = article.getAuthor();
        byte finalPrivateTrigger = article.getPrivateArticle();


        System.out.println(model);



        String[] articleTextAsString = articleText.split(" ");


        for(int i = 0; i < articleTextAsString.length; i++){
            if(articleTextAsString[i].startsWith("TITLE::")){
                articleTextAsString[i] = "<h1><b>" + articleTextAsString[i].substring(7,articleTextAsString[i].length()-1) + "</h1></b>";
            }
        }



        String articleTextFinal = "";

        for(int i = 0; i < articleTextAsString.length; i++){
            articleTextFinal = articleTextFinal + articleTextAsString[i] + " ";
        }

        articleTextFinal = articleTextFinal.substring(0,articleTextFinal.length()-1);





        EntityManager session = entityManagerFactory.createEntityManager();

 /*       session.createNativeQuery("UPDATE artiklar SET article_text=:articleText email_of_author=:authorOfArticle private_article=:finalPrivateTrigger WHERE id=:articleId")
                .setParameter("articleText", articleTextFinal)
                .setParameter("authorOfArticle", authorOfArticle)
                .setParameter("finalPrivateTrigger", 0) //cast top byte??
                .setParameter("articleId", articleId);*/

        article.setArticleText(articleTextFinal);
        article.setEmailOfAuthor(authorOfArticle);
        article.setPrivate(finalPrivateTrigger);

        System.out.println(article.getAuthor());
        System.out.println("this is how it came in : " + finalPrivateTrigger);
        System.out.println(article.getPrivateArticle());
        articleRepo.save(article);

        //System.out.println(model);

        return listAllArticles(model);
    }

    @GetMapping("/minaartiklar")
    public String minaArtiklar(@AuthenticationPrincipal CustomUserDetails userPrincipal, Model model){
        List<Article> mainArticleList = articleRepo.findAll();
        List<Article> finalList = new ArrayList<>();

        for(int i = 0; i < mainArticleList.size(); i++){
            if(mainArticleList.get(i).getAuthor().equals(userPrincipal.getUsername())){
                model.addAttribute("article", mainArticleList.get(i));
            }
        }

        System.out.println(model);

        //model.addAttribute("article", finalList.get(0));

        return "myarticles";


    }

}
