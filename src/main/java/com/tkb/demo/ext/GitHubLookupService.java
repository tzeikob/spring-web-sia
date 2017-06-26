package com.tkb.demo.ext;

import com.tkb.demo.model.GithubUser;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubLookupService {

    private static final Logger logger = Logger.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService() {
        this.restTemplate = new RestTemplate();
    }

    @Async
    public Future<GithubUser> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);

        String url = String.format("https://api.github.com/users/%s", user);
        GithubUser results = restTemplate.getForObject(url, GithubUser.class);

        Thread.sleep(3000L);

        return new AsyncResult<>(results);
    }

}
