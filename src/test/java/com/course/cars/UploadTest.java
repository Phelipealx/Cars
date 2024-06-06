package com.course.cars;

import com.course.cars.api.upload.UploadInput;
import com.course.cars.api.upload.UploadOutput;
import com.course.cars.domain.upload.FirebaseStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadTest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FirebaseStorageService service;

    private TestRestTemplate basicAuth() {
        return rest.withBasicAuth("admin", "admin");
    }

    private UploadInput getUploadInput() {
        UploadInput upload = new UploadInput();
        upload.setFileName("nome.txt");
        // Base64 - Phelipe Magalhães
        upload.setBase64("UGhlbGlwZSBNYWdhbGjDo2Vz");
        upload.setMimeType("text/plain");
        return upload;
    }

    @Test
    public void testUploadFirebase() {
        String url = service.upload(getUploadInput());

        // Get on URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    @Test
    public void testUploadAPI() {

        UploadInput upload = getUploadInput();

        // Insert
        ResponseEntity<UploadOutput> response = basicAuth().postForEntity("/api/v1/upload", upload, UploadOutput.class);
        System.out.println(response);

        // Verify if created
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UploadOutput out = response.getBody();
        assertNotNull(out);

        String url = out.getUrl();
        System.out.println(url);

        // Get on URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

}