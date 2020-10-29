package cs203t10.ryver.fts.security;

public class SecurityConstants {

	public static final String SECRET = "RyverAPISecretKey";

	public static final long EXPIRATION_TIME = 864_000_000;

	public static final String BEARER_PREFIX = "Bearer ";

	public static final String BASIC_PREFIX = "Basic ";

	public static final String AUTH_HEADER_KEY = "Authorization";

	public static final String AUTHORITIES_KEY = "auth";

	public static final String UID_KEY = "uid";

	public static final String FTS_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmdHMiLCJ1aWQiOjAsImF1dGgiOiJST0xFX0ZUUyIsImV4cCI6MTkwMDAwMDAwMDB9.GTpFiYTxqIO2NLcClycrX9a3c_kMvW6Vyw77XDVqhmKoP04ysOryMsvTa1T-OCwoPc86e9GMpGWWKh7QnRF8tg";
}
