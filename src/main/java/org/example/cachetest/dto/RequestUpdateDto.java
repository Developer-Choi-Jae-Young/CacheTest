package org.example.cachetest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDto {
    private Long id;
    private String changeName;
    private String changeType;
}
