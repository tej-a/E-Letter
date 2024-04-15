package com.adp.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.adp.entity.BasicCandidate;
import com.adp.service.CandidateDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

public class BasicCandidateControllerTest {
 @Autowired
 private MockMvc mockMvc;
 private ObjectMapper objectMapper;
   
 @InjectMocks
    private BasicCandidateController basicCandidateController;

    @Mock
    private CandidateDetailsService candidateDetailsService;
    
   

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTotalBasicCandidates() {
        // Mock the behavior of candidateDetailsService.getTotalBasicCandidates()
        when(candidateDetailsService.getTotalBasicCandidates()).thenReturn("MockedValue");

        // Call the controller method
        ResponseEntity<String> response = basicCandidateController.getTotalBasicCandidates();

        // Verify that the method was called
        verify(candidateDetailsService, times(1)).getTotalBasicCandidates();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MockedValue", response.getBody());
    }
    
    @Test
    public void testGetTotalSentLinks() {
        // Mock the behavior of candidateDetailsService.getTotalSentLinks()
        when(candidateDetailsService.getTotalSentLinks()).thenReturn("MockedValue");

        // Call the controller method
        ResponseEntity<String> response = basicCandidateController.getTotalSentLinks();

        // Verify that the method was called
        verify(candidateDetailsService, times(1)).getTotalSentLinks();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MockedValue", response.getBody());
    }
    
    @Test
    public void testGetTotalNotSentLinks() {
        // Mocking behavior of the candidateDetailsService
        when(candidateDetailsService.getTotalNotSentLinks()).thenReturn("expected_result");

        // Performing the actual test
        ResponseEntity<String> responseEntity = basicCandidateController.getTotalNotSentLinks();

        // Asserting the expected result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("expected_result", responseEntity.getBody());
    }

    @Test
    void testGetCandidates() {
        // Arrange
    	
        List<BasicCandidate> mockCandidateList =  new ArrayList<BasicCandidate>(1);
        when(candidateDetailsService.getBasicCadidate()).thenReturn(mockCandidateList);

        // Act
        ResponseEntity<List<BasicCandidate>> responseEntity = basicCandidateController.getCandidates();

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(mockCandidateList, responseEntity.getBody());
    }
    

        @Test
        public void testUploadBasicCandidateDetails() throws Exception {
            // Mocking
            MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Mock File Content".getBytes());

            // Call the controller method
            ResponseEntity<?> responseEntity = basicCandidateController.uploadBasicCandidateDetails(file);

            // Verify that the service method was called with the correct parameter
            verify(candidateDetailsService).saveCandidatesToDatabase(file);

            // Verify the response entity
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals("added successfully", ((Map<?, ?>) responseEntity.getBody()).get("Message"));
        }

//        @Test
//        public void testGetEmails() throws Exception {
//            // Mocked input data
//        	
//            List<String> emails = Arrays.asList();
//           // lemails.add("email1@example.com");
//            
//            //List<String>emails=Arrays.asList(lemails);
//
//            // Mocking the service method to avoid actual execution
//            doNothing().when(candidateDetailsService).updateLinkStatus(emails);
//
//            // Perform the POST request
//            mockMvc.perform(post("/get-emails")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(new ObjectMapper().writeValueAsString(emails)))
//                    .andExpect(status().isOk());
//
//            // Verify that the service method was called with the correct arguments
//            verify(candidateDetailsService, times(1)).updateLinkStatus(emails);
//        }

//        @Test
//        public void testUpdateComments() throws Exception {
//            // Mocked input data
//            String comments = "comments added";
//            String email = "test@example.com";
//
//            // Mocking the service method to avoid actual execution
//            doNothing().when(candidateDetailsService).updateComments(comments, email);
//
//            // Perform the POST request using the instance of MockMvc
//            mockMvc.perform(post("/update-comments")
//                    .param("comments", comments)
//                    .param("email", email))
//                    .andExpect(status().is4xxClientError());
//                    
//
//            // Verify that the service method was called with the correct arguments
//            verify(candidateDetailsService, times(1)).updateComments(comments, email);
//        }


}

