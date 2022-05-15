package qiao712.search.service;

import qiao712.search.service.dto.SearchResult;

public interface SearchService {
    void saveArchive(String contentStr, String appendixStr);

    void deleteArchive(long id);

    SearchResult searchArchive(String text, long pageNo, long pageSize);
}
