package qiao712.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qiao712.search.domain.Keyword;

import java.util.Set;

@Mapper
public interface KeywordMapper {
    int saveKeywords(@Param("keywords") Set<String> keywords);

    int saveKeyword(@Param("keyword") String keyword);

    boolean existsKeyword(@Param("keyword") String keyword);
}
