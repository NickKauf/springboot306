package com.example.cloudinarytemplate;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;



    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/")
    public String listActors(Model model){


        //grab all and display

        model.addAttribute("directors", directorRepository.findAll());

        return "list";
    }

    @GetMapping("/addDirector")
    public String addDirector(Model model){
        model.addAttribute("director", new Director());
        return "directorform";
    }

    @GetMapping("/addMovie")
    public String addMovie(Model model){
        model.addAttribute("movie", new Movie());
        model.addAttribute("directors", directorRepository.findAll());
        return "movieform";
    }

    @PostMapping("/processMovie")
    public String processMovie(@ModelAttribute Movie movie){
        movieRepository.save(movie);
        return "redirect:/";
    }

    @RequestMapping("/updateMovie/{id}")
    public String updateMovie(@PathVariable("id") long id, Model model){
        model.addAttribute("movie", movieRepository.findById(id).get());
        model.addAttribute("directors", directorRepository.findAll());
        return "movieForm";
    }

    @RequestMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable("id") long id){
        movieRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processDirector")
    public String processDirector(@ModelAttribute Director director){
        directorRepository.save(director);
        return "redirect:/";
    }

    @RequestMapping("/updateDirector/{id}")
    public String updateDirector(@PathVariable("id") long id, Model model){
        model.addAttribute("director", directorRepository.findById(id).get());
        return "directorForm";
    }

    @RequestMapping("/deleteDirector/{id}")
    public String deleteDirector(@PathVariable("id") long id){
        directorRepository.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/add")
    public String newActor(Model model){
        model.addAttribute("actor", new Actor());
        return "actorform";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepository.save(actor);
        } catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }


}