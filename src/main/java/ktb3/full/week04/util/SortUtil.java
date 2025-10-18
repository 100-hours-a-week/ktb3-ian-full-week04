package ktb3.full.week04.util;

import ktb3.full.week04.dto.page.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;

public class SortUtil {

    @SuppressWarnings("unchecked")
    public static <T, R extends Comparable<R>> Comparator<T> getComparator(Sort sort) {
        return Comparator.comparing(t -> {
            try {
                Field field = t.getClass().getDeclaredField(sort.getProperty());
                field.setAccessible(true);
                return (R) field.get(t);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(String.format("필드 %s가 존재하지 않습니다.", sort.getProperty()));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("필드 %s에 접근할 수 없습니다.", sort.getProperty()));
            }
        });
    }
}
