package qiao712.search;

import com.alibaba.fastjson.JSON;
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
        String content = "在这个xxxfff反智横行的年代，只有金轮的直播给了我感性和理性的认知。[傲娇]\n" +
                "他的直播展现出的，是一个人纯粹所具有的良好品质，说不上什么几分特别突兀的地方，整场直播十分的融洽。\n" +
                "他直播发病的机智是精妙的，真诚和正直的朴素给人一种无法接触的高贵感，就好像永远抓不住的闪亮的星星，一个彬彬有礼的人用完美的操作给观众带来最为精致的美的感受，我不禁怀疑上帝的天意是否对我太过宠溺，让我人生中认识到如此不朽和无价的灵魂，充满着可爱与美好。\n" +
                "我不得不再次审视男童们，原来他们早已追随如此伟大的事物，想必他们的品行、修养也一定很好。\n" +
                "呐呐呐，你看我现在，再超神给你看啊[傲娇]";

        Map<String, Object> map = new HashMap<>();
        map.put("k1", "hello");
        map.put("k2", 123);
        Map<String, Object> subMap = new HashMap<>();
        map.put("k3", subMap);
        subMap.put("kk1" , content);
        searchService.saveArchive(JSON.toJSONString(map), null);
    }

    @Test
    public void testDelete(){
        searchService.deleteArchive(3L);
    }
}
