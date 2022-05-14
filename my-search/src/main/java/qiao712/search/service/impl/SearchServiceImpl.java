package qiao712.search.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qiao712.search.dao.ArchiveMapper;
import qiao712.search.dao.InvertedIndexMapper;
import qiao712.search.dao.KeywordMapper;
import qiao712.search.domain.Archive;
import qiao712.search.service.SearchService;
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
    public void saveArchive(String contentJson, String appendixJson) {
        Object content = JSON.parse(contentJson);
        Set<String> keywords = new HashSet<>();
        //待处理JSONObject队列
        Queue<Object> queue = new LinkedList<>();
        queue.add(content);

        while(!queue.isEmpty()){
            Object current = queue.poll();
            if(current instanceof Map){
                queue.add(((Map<?, ?>) current).values());
            }else{
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
        archive.setContent(contentJson);
        archive.setAppendix(appendixJson);
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
    public void afterPropertiesSet() throws Exception {
        //阻塞地初始化segmenter
        segmenter = new TopSegmenter();
    }
}
