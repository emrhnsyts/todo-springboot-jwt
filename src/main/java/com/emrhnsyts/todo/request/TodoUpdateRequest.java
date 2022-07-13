package com.emrhnsyts.todo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoUpdateRequest {
    @Length(max = 100,message = "Text can not be longer than 100 letters")
    @NotBlank(message = "Text can not be blank.")
    private String text;
    private Boolean isCrossed;
}
