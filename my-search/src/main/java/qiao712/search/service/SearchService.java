package qiao712.search.service;

public interface SearchService {
    void saveArchive(String contentJson, String appendixJson);

    void deleteArchive(long id);
}
