package il.test.TestWithReact.data.entity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ILPage<T> {
    @JsonView({ViewLevel.All.class})
    private List<T> content;
    @JsonView({ViewLevel.All.class})
    private long totalElements;
    @JsonView({ViewLevel.All.class})
    private int totalPages;
    @JsonView({ViewLevel.All.class})
    private int pageSize;
    @JsonView({ViewLevel.All.class})
    private int pageNum;

    public ILPage(Page<T> page) {
        content = page.getContent();
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        pageSize = page.getSize();
        pageNum = page.getNumber();
    }
}
