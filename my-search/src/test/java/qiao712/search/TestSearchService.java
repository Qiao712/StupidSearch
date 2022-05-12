package qiao712.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import qiao712.search.service.SearchService;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestSearchService {
    @Autowired
    SearchService searchService;

    @Test
    public void test(){
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "hello");
        map.put("k2", 123);
        Map<String, Object> subMap = new HashMap<>();
        map.put("k3", subMap);
        subMap.put("kk1" , "world");
        searchService.saveArchive(map, null);
    }
}
