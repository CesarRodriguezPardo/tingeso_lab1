package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tingeso.back.entities.*;
import tingeso.back.repositories.CreditRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreditServiceTest {

    @InjectMocks
    private CreditService creditService;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private EvaluateCreditService evaluateCreditService;

    @Mock
    private CalculateService calculateService;

    @Mock
    private UserService userService;

    @Mock
    private CustomerWorksheetService customerWorksheetService;

    @Mock
    private SavingAccountService savingAccountService;

    private CreditEntity credit;
    private String rut = "12345678-9";
    private SavingAccountEntity savingAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        credit = new CreditEntity();
        credit.setRut(rut);
        credit.setRequestedAmount(1000000);
        credit.setInterestRate(5);
        credit.setRequestedTerm(12);

        savingAccount = new SavingAccountEntity();
        savingAccount.setNegativePoints(1);
    }

    @Test
    void testFindByRut() {
        // Arrange
        when(creditRepository.findByRut(rut)).thenReturn(List.of(credit));

        // Act
        List<CreditEntity> result = creditService.findByRut(rut);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(rut, result.get(0).getRut());
    }

    @Test
    void testFindAll() {
        // Arrange
        when(creditRepository.findAll()).thenReturn(List.of(credit));

        // Act
        List<CreditEntity> result = creditService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        // Arrange
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        // Act
        CreditEntity result = creditService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(credit, result);
    }

    @Test
    void testSaveCredit_WithSuccessfulCreditApproval() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(true);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(true);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(true);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(true);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(true);
        when(creditRepository.save(any(CreditEntity.class))).thenReturn(credit);

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result);
        assertEquals(7, credit.getStatus());
    }

    @Test
    void testSaveCredit_WithSavingAccountNegativePoints() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(false);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(false);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(false);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(false);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(false);
        when(savingAccountService.findByRut(rut)).thenReturn(savingAccount);

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result);
        assertEquals(7, credit.getStatus());
    }

    @Test
    void testSaveCredit_WithFailedCreditApproval() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(false);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(false);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(false);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(false);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(false);
        when(savingAccountService.findByRut(rut)).thenReturn(savingAccount);

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result);
        assertEquals(7, credit.getStatus());
    }

    @Test
    void testSetStatus() {
        // Arrange
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        // Act
        creditService.setStatus(1L, 3);

        // Assert
        assertEquals(3, credit.getStatus());
        verify(creditRepository, times(1)).save(credit);
    }

    @Test
    void testSetType() {
        // Arrange
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        // Act
        creditService.setType(1L, 2);

        // Assert
        assertEquals(2, credit.getType());
        verify(creditRepository, times(1)).save(credit);
    }


    @Test
    void testSaveCredit_WithSavingAccountLowNegativePoints() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(false);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(false);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(false);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(false);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(false);
        when(savingAccountService.findByRut(rut)).thenReturn(savingAccount); // Account with negativePoints = 1

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result); // Credit should be approved based on the saving account evaluation
        assertEquals(7, credit.getStatus());
    }

    @Test
    void testSaveCredit_WithFailedApprovalAndHighNegativePoints() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(false);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(false);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(false);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(false);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(false);

        savingAccount.setNegativePoints(3);  // Set high negative points

        when(savingAccountService.findByRut(rut)).thenReturn(savingAccount);

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result); // Even if credit conditions failed, status will be set to 1 due to high negative points
        assertEquals(1, credit.getStatus());
    }
    @Test
    void testSaveCredit_WithPassedAllEvaluations() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(true);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(true);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(true);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(true);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(true);

        when(creditRepository.save(any(CreditEntity.class))).thenReturn(credit);

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result); // Credit should be approved based on R1-R6 conditions
        assertEquals(7, credit.getStatus());
    }

    @Test
    void testSaveCredit_WithFailedEvaluationsAndNegativeSavingAccount() {
        // Arrange
        when(calculateService.calculatePayment(1000000, 5.5, 12)).thenReturn(100000.0);
        when(evaluateCreditService.R1feeAndIncomeRatio(100000.0, rut)).thenReturn(false);
        when(evaluateCreditService.R2creditHistory(rut)).thenReturn(false);
        when(evaluateCreditService.R3seniority(rut)).thenReturn(false);
        when(evaluateCreditService.R4debtIncome(rut, 100000.0)).thenReturn(false);
        when(evaluateCreditService.R6maxAge(12, rut)).thenReturn(false);
        when(savingAccountService.findByRut(rut)).thenReturn(savingAccount);

        savingAccount.setNegativePoints(2);  // Account with 2 negative points

        // Act
        Boolean result = creditService.saveCredit(credit);

        // Assert
        assertTrue(result); // Status will still be 1 since the saving account has >= 2 negative points
        assertEquals(1, credit.getStatus());
    }


}
