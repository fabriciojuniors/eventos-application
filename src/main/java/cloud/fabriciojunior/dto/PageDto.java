package cloud.fabriciojunior.dto;

import java.util.List;

public record PageDto<T> (List<T> content,
                          boolean hasNext,
                          long totalItens) {

}
