package com.example.cloudinarytemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DirectorRepository directorRepository;

    public void run(String...args){
        User admin = new User("admin", "Dave@domain.com", "admin", "Dave","Wolf", true);
        Role adminRole = new Role("admin", "ROLE_USER");
        Role adminRole2 = new Role("admin", "ROLE_ADMIN");

        userRepository.save(admin);
        roleRepository.save(adminRole);
        roleRepository.save(adminRole2);

        User user = new User("user", "user@domain.com", "user", "User", "User", true);
        Role userRole = new Role("user", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);

        Director director = new Director();
        director.setName("James Cameron");
        director.setGenre("sci-fi");

        Movie movie = new Movie();
        movie.setTitle("Star Movie");
        movie.setYear(2017);
        movie.setDescription("About Stars...");
        movie.setDirector(director);

        Movie movie2 = new Movie();
        movie2.setTitle("Deathstar Ewoks");
        movie2.setYear(2011);
        movie2.setDescription("About Ewoks on the Deathstar");
        movie2.setDirector(director);

        Set<Movie> movies = new HashSet<>();
        movies.add(movie);
        movies.add(movie2);

        director.setMovies(movies);

        directorRepository.save(director);

    }


}
