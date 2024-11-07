package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.UserEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.UserRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluateCreditServiceTest {

    @InjectMocks
    private EvaluateCreditService evaluateCreditService;

    @Mock
    private CustomerWorksheetRepository customerWorksheetRepository;

    @Mock
    private UserRepository userRepository;

    private CustomerWorksheetEntity worksheetEntity;
    private UserEntity user;
    private String rut = "12345678-9";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        worksheetEntity = new CustomerWorksheetEntity();
        worksheetEntity.setRut(rut);
        worksheetEntity.setSalary(1000000);
        worksheetEntity.setDicom(false);
        worksheetEntity.setLatePayment(false);
        worksheetEntity.setIndependentJob(false);
        worksheetEntity.setJobSeniority(1); // default job seniority
        worksheetEntity.setTotalDebts(300000);
        worksheetEntity.setIndependentEvaluate(false);

        user = new UserEntity();
        user.setRut(rut);
        user.setAge(30); // default age
    }

    @Test
    void testR1feeAndIncomeRatio_AboveThreshold() {
        // Arrange
        worksheetEntity.setSalary(1000000); // Setting salary to a high value to pass the test
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R1feeAndIncomeRatio(400000, rut);

        // Assert
        assertTrue(result);
    }

    @Test
    void testR1feeAndIncomeRatio_BelowThreshold() {
        // Arrange
        worksheetEntity.setSalary(1000000); // Setting salary to a high value to pass the test
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R1feeAndIncomeRatio(200000, rut);

        // Assert
        assertFalse(result);
    }

    @Test
    void testR2creditHistory_HasCreditIssues() {
        // Arrange
        worksheetEntity.setDicom(true); // Setting dicom to true
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R2creditHistory(rut);

        // Assert
        assertTrue(result);
    }

    @Test
    void testR2creditHistory_NoCreditIssues() {
        // Arrange
        worksheetEntity.setDicom(false); // Setting dicom to false
        worksheetEntity.setLatePayment(false); // Setting latePayment to false
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R2creditHistory(rut);

        // Assert
        assertFalse(result);
    }

    @Test
    void testR3seniority_IndependentJobEvaluated() {
        // Arrange
        worksheetEntity.setIndependentJob(true); // Setting the person as independent worker
        worksheetEntity.setIndependentEvaluate(true); // The evaluation should pass
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R3seniority(rut);

        // Assert
        assertTrue(result);
    }

    @Test
    void testR3seniority_IndependentJobNotEvaluated() {
        // Arrange
        worksheetEntity.setIndependentJob(true); // Setting the person as independent worker
        worksheetEntity.setIndependentEvaluate(false); // Evaluation fails
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R3seniority(rut);

        // Assert
        assertFalse(result);
    }





    @Test
    void testR4debtIncome_ExceedsThreshold() {
        // Arrange
        worksheetEntity.setTotalDebts(500000); // Set debts to a high value
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R4debtIncome(rut, 600000);

        // Assert
        assertTrue(result);
    }

    @Test
    void testR4debtIncome_BelowThreshold() {
        // Arrange
        worksheetEntity.setTotalDebts(100000); // Set debts to a low value
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(worksheetEntity);

        // Act
        boolean result = evaluateCreditService.R4debtIncome(rut, 200000);

        // Assert
        assertFalse(result);
    }

    @Test
    void testR6maxAge_AgeExceedsThreshold() {
        // Arrange
        user.setAge(65); // User's age is 65
        when(userRepository.findByRut(rut)).thenReturn(user);

        // Act
        boolean result = evaluateCreditService.R6maxAge(6, rut);

        // Assert
        assertTrue(result);
    }

    @Test
    void testR6maxAge_AgeDoesNotExceedThreshold() {
        // Arrange
        user.setAge(60); // User's age is 60
        when(userRepository.findByRut(rut)).thenReturn(user);

        // Act
        boolean result = evaluateCreditService.R6maxAge(6, rut);

        // Assert
        assertFalse(result);
    }
}
