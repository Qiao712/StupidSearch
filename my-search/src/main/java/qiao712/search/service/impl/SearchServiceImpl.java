package qiao712.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import javafx.scene.shape.Arc;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qiao712.search.dao.ArchiveMapper;
import qiao712.search.dao.InvertedIndexMapper;
import qiao712.search.dao.KeywordMapper;
import qiao712.search.domain.Archive;
import qiao712.search.service.SearchService;
import qiao712.search.service.dto.SearchResult;
import qiao712.segmenter.core.Segmenter;
import qiao712.segmenter.core.TopSegmenter;
import qiao712.segmenter.dictionary.Lexeme;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService , InitializingBean {
    private Segmenter segmenter;

    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private InvertedIndexMapper invertedIndexMapper;

    @Override
    @Transactional
    public void saveArchive(String contentStr, String appendixStr) {
        Set<String> keywords = new HashSet<>();
        Queue<Object> queue = new LinkedList<>(); //待处理JSONObject队列

        try{
            Object content = JSON.parse(contentStr);
            queue.add(content);
        }catch (JSONException e){
            //不是json则全字符串参与搜索
            queue.add(contentStr);
        }

        while(!queue.isEmpty()){
            Object current = queue.poll();
            if(current instanceof Map){
                queue.add(((Map<?, ?>) current).values());
            }if(current instanceof List){
                queue.addAll((List<?>) current);
            }else {
                //分词
                List<Lexeme> lexemes = segmenter.match(current.toString());
                for (Lexeme lexeme : lexemes) {
                    //去除两端空白
                    keywords.add(lexeme.getWord().trim());
                }
            }
        }

        //持久化
        //保持关键字
        for (String keyword : keywords) {
            if(!keywordMapper.existsKeyword(keyword)){
                keywordMapper.saveKeyword(keyword);
            }
        }

        //保存archive
        Archive archive = new Archive();
        archive.setContent(contentStr);
        archive.setAppendix(appendixStr);
        archiveMapper.saveArchive(archive);

        //保存索引
        invertedIndexMapper.saveInvertedIndexes(keywords, archive.getId());
    }

    @Override
    public void deleteArchive(long id) {
        invertedIndexMapper.deleteIndexByArchive(id);
        archiveMapper.deleteArchive(id);
    }

    @Override
    public void deleteArchiveByAppendix(String appendixStr) {
        List<Archive> archives = archiveMapper.getArchiveByAppendix(appendixStr);
        for (Archive archive : archives) {
            deleteArchive(archive.getId());
        }
    }

    @Override
    @Transactional
    public SearchResult searchArchive(String text, long pageNo, long pageSize){
        List<Lexeme> lexemes = segmenter.match(text);

        //文档权重
        Map<Long, Integer> archiveWeights = new HashMap<>();
        for (Lexeme lexeme : lexemes) {
            if(!lexeme.getWord().isEmpty()){
                List<Long> archiveIds = invertedIndexMapper.getArchiveIdsByKeyword(lexeme.getWord());
                for (Long archiveId : archiveIds) {
                    Integer weight = archiveWeights.get(archiveId);
                    archiveWeights.put(archiveId, weight == null ? 1 : weight + 1);
                }
            }
        }

        //排序
        List<Map.Entry<Long, Integer>> sortedArchives = new ArrayList<>(archiveWeights.entrySet());
        sortedArchives.sort(new Comparator<Map.Entry<Long, Integer>>() {
            @Override
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return - Long.compare(o1.getValue(), o2.getValue());
            }
        });

        //组织结果
        long pageCount = sortedArchives.size() / pageSize;
        pageCount = sortedArchives.size() % pageSize == 0 ? pageCount : pageCount + 1;

        SearchResult searchResult = new SearchResult();
        searchResult.setPageNo(pageNo);
        searchResult.setPageSize(pageSize);
        searchResult.setPageCount(pageCount);
        searchResult.setItemCount(sortedArchives.size());

        List<Archive> archives = new ArrayList<>();
        for(long i = pageSize * (pageNo-1); i < sortedArchives.size() && i < pageSize * pageNo; i++){
            archives.add(archiveMapper.getArchive(sortedArchives.get((int)i).getKey()));
        }
        searchResult.setArchives(archives);

        return searchResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //阻塞地初始化segmenter
        segmenter = new TopSegmenter();
    }
}
