package ru.exercise.alphabank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.exercise.alphabank.model.GifObject;

@FeignClient(name = "gif", url = "${gif.url}")
public interface GifClient {

    @GetMapping(params = {"api_key", "q"})
    GifObject getGif(@RequestParam String api_key,
                     @RequestParam (name = "q") String query);
}
