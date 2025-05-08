package alliwannadev.shop.supports.event.payload;

import alliwannadev.shop.supports.event.EventPayload;
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
