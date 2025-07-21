package alliwannadev.shop.support.event.payload;

import alliwannadev.shop.support.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignedUpEventPayload implements EventPayload {

    private Long userId;

    private String email;
}
