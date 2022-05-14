package qiao712.search.domain;

import lombok.Data;

@Data
public class InvertedIndex {
    private long keywordId;
    private long archiveId;

    private String keyword;
}
