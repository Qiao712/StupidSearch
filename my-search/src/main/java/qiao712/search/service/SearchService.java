package qiao712.search.service;

import qiao712.search.service.dto.SearchResult;

public interface SearchService {
    void saveArchive(String contentJson, String appendixJson);

    void deleteArchive(long id);

    SearchResult searchArchive(String text, long pageNo, long pageSize);
}
