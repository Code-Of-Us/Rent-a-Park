package com.codeofus.rent_a_park.errors;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BadRequestException extends RuntimeException {

    String message;

    String entityName;

    String errorKey;
}
