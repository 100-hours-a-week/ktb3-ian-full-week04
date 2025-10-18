package ktb3.full.week04.util;

import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.infrastructure.database.table.Table;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public static <T extends Deletable, ID> List<T> paging(Table<T, ID> table, List<ID> sortedList, PageRequest pageRequest) {
        if (sortedList == null) {
            return new ArrayList<>();
        }

        List<T> content = new ArrayList<>();
        int start = (pageRequest.getNumber() - 1) * pageRequest.getSize();

        int count = 0;
        int curr = start;
        while (count < pageRequest.getSize() && curr < sortedList.size()) {
            T entity = table.select(sortedList.get(curr++));

            if (entity.isDeleted()) {
                continue;
            }

            content.add(entity);
            count++;
        }

        return content;
    }
}
