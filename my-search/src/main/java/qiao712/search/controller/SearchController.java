package qiao712.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qiao712.search.domain.Archive;
import qiao712.search.service.SearchService;
import qiao712.search.service.dto.SearchResult;

import javax.websocket.server.PathParam;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/archives")
    public void addArchive(@RequestBody Archive archive){
        searchService.saveArchive(archive.getContent(), archive.getAppendix());
    }

    @GetMapping("/archives")
    public SearchResult searchArchive(@PathParam("search") String search, @PathParam("pageNo") Long pageNo, @PathParam("pageSize") Long pageSize){
        return searchService.searchArchive(search, pageNo, pageSize);
    }

    @DeleteMapping("/archives")
    public void deleteArchiveByAppendix(@PathParam("appendix") String appendix){
        searchService.deleteArchiveByAppendix(appendix);
    }

    @DeleteMapping("/archives/{id}")
    public void deleteArchive(@PathVariable("id") Long id){
        searchService.deleteArchive(id);
    }
}
