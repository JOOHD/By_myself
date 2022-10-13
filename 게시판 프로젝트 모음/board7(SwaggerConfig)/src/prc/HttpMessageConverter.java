public class HttpMessageConverter {
    
    @PostMapping("/test/json")
    public ResponseEntity<?> returnJson() {
        SignupDto SignupDto = SignUpDto.builder()
                                .name("홍길동")
                                .password("1234!@#$")
                                .eamil("test@test.com")
                                .build();

        return new ResponseEntity<>(signupDto.HttpStatus.OK);                                
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    protected boolean canRead(@Nullable MediaType mediaType) {

        if (mediaType == null) { // mediaType 이 존재하는지 체크
            return true;
        } else {
            Iterator var2 = this.getSupportedMediaTypes().iterator();

            MediaType getSupportedMediaTypes;
            do {
                if (!var2.hashNext()) {
                    return false;
                }

                getSupportedMediaTypes = (MediaType)var2.next();
            } while(!getSupportedMediaTypes.includes(mediaType));

            return true;
        }
    }
    
}
