package com.example.blopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;

    @GetMapping("/skapa")
    public String createArticle(Model model){
        model.addAttribute("article", new Article());
        return "skapaArtikel";
    }

}
