package cs203t10.ryver.fts.security;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Hidden
@AllArgsConstructor
public class RyverPrincipal {

	public Long uid;

	public String username;

	public String jwt;

}
