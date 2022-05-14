package qiao712.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface InvertedIndexMapper {
    int saveInvertedIndexes(@Param("keywords") Set<String> keywords, @Param("archiveId") long archiveId);

    int deleteIndexByArchive(long archiveId);

    List<Long> getArchiveIdsByKeyword(String keyword);
}
