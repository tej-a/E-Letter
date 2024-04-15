package com.adp.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.adp.dto.CandidateInfoDTO;
import com.adp.dto.OfferGenerationDetails;
import com.adp.entity.CandidateInfo;
import com.adp.exception.UserException;
import com.adp.repository.CandidateInfoRepository;

@ExtendWith(MockitoExtension.class)
public class CandidateInfoServiceImplTest {

    @InjectMocks
    private CandidateInfoServiceImpl candidateInfoService;

    @Mock
    private CandidateInfoRepository candidateInfoRepository;
    
    private ModelMapper modelMapper;
    
    @Mock
    private GenerateOfferLettersService generateOfferLettersService;
    
    @SuppressWarnings("deprecation")
	@BeforeEach

    void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
       candidateInfoService.setModelMapper(modelMapper);

    }

    @Test
    void testAddToDataBase() throws UserException, IOException {
        CandidateInfoDTO candidateInfo = new CandidateInfoDTO();
        candidateInfoService.addToDataBase(candidateInfo);
        verify(candidateInfoRepository, times(1)).save(any(CandidateInfo.class));
    }
    
    @Test
    void testGetCandidateInfo() throws UserException {
        // Arrange
        List<CandidateInfo> candidateInfoList = Collections.singletonList(new CandidateInfo());
        when(candidateInfoRepository.findAll()).thenReturn(candidateInfoList);

        List<CandidateInfoDTO> expectedDTOList = Collections.singletonList(new CandidateInfoDTO());
       

        // Act
        List<CandidateInfoDTO> result = candidateInfoService.getCandidateInfo();

        // Assert
        assertEquals(expectedDTOList, result);
    }
    
    
    @Test
    void testGetGeneratedCandidateInfo() throws UserException {
        // Arrange
        List<CandidateInfo> generatedCandidateInfoList = Collections.singletonList(new CandidateInfo());
        when(candidateInfoRepository.findByOfferLetterStatus("Generated")).thenReturn(generatedCandidateInfoList);

        CandidateInfoDTO expectedDTO = new CandidateInfoDTO();
       

        // Act
        try {
            List<CandidateInfoDTO> result = candidateInfoService.getGeneratedCandidateInfo();

            // Assert
            assertEquals(Collections.singletonList(expectedDTO), result);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Test
    void testGetNotGeneratedCandidateInfo() throws UserException {
        // Arrange
        List<CandidateInfo> notGeneratedList = Collections.singletonList(new CandidateInfo());
        when(candidateInfoRepository.findByOfferLetterStatusAndVerificationStatus("Not Generated", "Verified"))
                .thenReturn(notGeneratedList);

        CandidateInfoDTO expectedDTO = new CandidateInfoDTO();
       
        // Act
        List<CandidateInfoDTO> result = candidateInfoService.getNotGeneratedCandidateInfo();

        // Assert
        assertEquals(Collections.singletonList(expectedDTO), result);
    }
    
    
    @Test
    void testGetNotApprovedCandidateInfo() throws UserException {
        // Arrange
        List<CandidateInfo> notApprovedList = Collections.singletonList(new CandidateInfo());
        when(candidateInfoRepository.findByApprovalStatus("Not Approved")).thenReturn(notApprovedList);

        CandidateInfoDTO expectedDTO = new CandidateInfoDTO();
        

        // Act
        List<CandidateInfoDTO> result = candidateInfoService.getNotApprovedCandidateInfo();

        // Assert
        assertEquals(Collections.singletonList(expectedDTO), result);
    }

    @Test
    void testGetApprovedCandidateInfo() throws UserException {
        // Arrange
        List<CandidateInfo> approvedList = Collections.singletonList(new CandidateInfo());
        when(candidateInfoRepository.findByApprovalStatus("Approved")).thenReturn(approvedList);

        CandidateInfoDTO expectedDTO = new CandidateInfoDTO();
       

        // Act
        List<CandidateInfoDTO> result = candidateInfoService.getApprovedCandidateInfo();

        // Assert
        assertEquals(Collections.singletonList(expectedDTO), result);
    }
    
    @Test
    void testUpdateOfferLetterGenerationStatus() throws UserException {
        // Arrange
        String email = "test@example.com";
        CandidateInfo candidateInfo = new CandidateInfo();
        when(candidateInfoRepository.getReferenceById(email)).thenReturn(candidateInfo);

        // Act
        candidateInfoService.updateOfferLetterGenerationStatus(email);

        // Assert
        verify(candidateInfoRepository, times(1)).save(candidateInfo);
        assertEquals("Generated", candidateInfo.getOfferLetterStatus());
    }

    @Test
    void testUpdateApprovalStatus() throws UserException {
        // Arrange
        List<String> emails = Collections.singletonList("test@example.com");
        CandidateInfo candidateInfo = new CandidateInfo();
        when(candidateInfoRepository.getReferenceById(any())).thenReturn(candidateInfo);

        // Act
        candidateInfoService.updateApprovalStatus(emails);

        // Assert
        verify(candidateInfoRepository, times(emails.size())).save(candidateInfo);
        assertEquals("Approved", candidateInfo.getApprovalStatus());
    }

    @Test
    void testGenerateOfferLetters() throws UserException, IOException {
        // Arrange
        OfferGenerationDetails details = new OfferGenerationDetails();
        details.setEmails(Collections.singletonList("test@example.com"));

        doNothing().when(generateOfferLettersService).generateOfferLetter(anyList(), eq(details));

        // Act
        candidateInfoService.generateOfferLetters(details);

        // Assert
        verify(generateOfferLettersService, times(1)).generateOfferLetter(anyList(), eq(details));
    }
    
    

}
    

