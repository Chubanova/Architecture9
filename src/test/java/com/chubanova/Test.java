package com.chubanova;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class Test {
    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    static void init() {
        WireMockServer wireMockServer = new WireMockServer(
                new WireMockConfiguration().port(7070)
        );
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @org.junit.jupiter.api.Test
    void shouldRequireAuthToStartNewBattle() {
        ResponseEntity<String> result = template.getForEntity("/battle", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode(), "authenticate first to start a battle");
    }

    @org.junit.jupiter.api.Test
    void shouldReturnNewJwtTokenForCorrectUser() {
        var jwtResponse = template
                .postForEntity("/auth", new JwtRequest("user1", "password"), JwtResponse.class);
        Assertions.assertThat(jwtResponse.getBody().getToken())
                .as("should return correct token")
                .isNotEmpty();
    }

    @org.junit.jupiter.api.Test
    void shouldReturnNewGameIdForAuthenticatedUser() {
        var jwtResponse = template
                .postForEntity("/auth", new JwtRequest("user1", "password"), JwtResponse.class);
        String token = jwtResponse.getBody().getToken();
        RequestEntity<SendCommandRequest> request = RequestEntity.post(URI.create("/battle"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .body(new SendCommandRequest());
        var newGameResponse = template
                .postForEntity("/battle", request, StartGameResponse.class);
        Assertions.assertThat(newGameResponse.getBody().getBattleId())
                .as("should return correct battle id")
                .isNotEmpty();
    }

}
