package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import com.example.demo.ErrorResponse;
// import com.example.demo.ReposListResponse;
import com.example.demo.*;

import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/ghuser")
public class Controller {

    static final String GIT_API_URL = "https://api.github.com";

    static final RestClient defaultClient = RestClient.create();
    
    @GetMapping("/{username}")
    public ResponseEntity<?> getGHUser(@PathVariable String username) {

        String s = GIT_API_URL + "/users/"+username+"/repos";

        // Sends the request, retrieves a response, maps it to a list of repository objects.

        // For the sake of honesty... For a while, i had a string returned and tried to manually parse it. But that didn't quite work.
        // Ended up asking chatGPT for a little bit of help in here.
        // Because apparently Spring had a way to map the result to not just AN OBJECT, but also a LIST of objects.
        // But i couldn't find specifics on HOW. When searching places like stackoverflow and just a documentation, i found a toEntityList() method
        // But that method simply didn't work. Straight up gave me a "cannot find symbol" error when building with gradle.
        // Therefore, i asked about it. And was informed about ParameterizedTypeReference. So that's how i got that bit.

        try {
            ResponseEntity<List<Repository>> result = defaultClient.get()
            .uri(s)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> { 
                // Leaving it here as kind of a reminder. I initially wanted to return that 404 response from that. Alas, it did not have the fields i needed.
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            })
            .toEntity(new ParameterizedTypeReference<List<Repository>>() {});

            List<Repository> repos = result.getBody();

            List<ReposListResponse> reporesp = new ArrayList<ReposListResponse>();

            for (Repository repo : repos) {

                if (!repo.fork) {
                    String branches_url = repo.branches_url.replace("{/branch}", "");

                    ResponseEntity<List<Branch>> br_result = defaultClient.get()
                    .uri(branches_url)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<Branch>>() {});

                    List<Branch> branches = br_result.getBody();

                    repo.branches = branches;

                    for (Branch branch: branches) {
                        reporesp.add(new ReposListResponse(repo.name, repo.owner.login, branch.name, branch.commit.sha));
                    }
                }
            }

            // Another customized response return. Jackson's fairly smart, turns out. Builds a neat json from a list like this automatically.
            return new ResponseEntity(reporesp, HttpStatus.OK);

        } catch (ResponseStatusException e) {

            // I'm returning a customized response since the previous one had a different structure altogether.
            return new ResponseEntity(new ErrorResponse(404, "User not found"), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/")
    public ResponseEntity<?> emptyUser() {
        return new ResponseEntity(new ErrorResponse(404, "Empty username provided"), HttpStatus.NOT_FOUND);
    }
    
}
