package qiao712.search.service.dto;

import lombok.Data;
import qiao712.search.domain.Archive;

import java.util.List;

@Data
public class SearchResult {
    long pageNo;
    long pageSize;
    long pageCount;
    long itemCount;
    List<Archive> archives;
}
