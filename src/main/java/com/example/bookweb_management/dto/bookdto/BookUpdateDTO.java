package com.example.bookweb_management.dto.bookdto;

import com.example.bookweb_management.constant.BookConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDTO {
    @NotBlank(message = "Title " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 150, message = BookConstants.MSG_TITLE_SIZE)
    private String title;

    @NotBlank(message = "Author " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 80, message = BookConstants.MSG_AUTHOR_SIZE)
    private String author;

    @NotBlank(message = "Publisher " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 80, message = BookConstants.MSG_PUBLISHER_SIZE)
    private String publisher;

    @Min(value = 1, message = BookConstants.MSG_PAGECOUNT_MIN)
    @NotNull(message = "Page count is not null")
    private int pageCount;

    @NotBlank(message = "Author " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 50, message = BookConstants.MSG_PRINTTYPE_SIZE)
    private String printType;

    @NotBlank(message = "Author " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 50, message = BookConstants.MSG_LANGUAGE_SIZE)
    private String language;

    @NotBlank(message = "Author " + BookConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 1000, message = BookConstants.MSG_DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = "User id must be not null")
    private Long userId;

    @NotNull(message = "Category id must be not null")
    private List<Long> categoryIds;

}
