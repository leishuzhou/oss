package pub.tbc.mybatis.plugin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author tbc on 2016/10/16 1:26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class PageWrapper<T> {

    private Integer pageNo;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    private List<T> list;

    private Map<String , Object> otherData;

    public PageWrapper(PageParams pageParams) {
        this.pageNo = pageParams.getPageNo();
        this.pageSize = pageParams.getPageSize();
        this.total = pageParams.getTotal();
        this.pages = pageParams.getPages();
    }

}
