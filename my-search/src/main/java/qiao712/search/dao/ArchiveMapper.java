package qiao712.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qiao712.search.domain.Archive;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArchiveMapper {
    int saveArchive(Archive archive);

    int deleteArchive(long id);

    Archive getArchive(long id);

    List<Archive> getArchiveByAppendix(String appendix);
}
