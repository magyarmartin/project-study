package hu.study.rest.response;

import hu.study.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This is the base response we send back to every request. It should be not mistaken with {@link javax.ws.rs.core.Response}.
 *
 * @author magyarm
 *
 */
@Data
@AllArgsConstructor
public class ServerResponse {

    public ServerResponse( final Status status ) {
        this.status = status;
    }

    private Status status;

    private BaseDto payload;

}
