package com.codeofus.rent_a_park.errors;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadRequestException extends Exception {

    String message;

    String entityName;

    String errorKey;
}
