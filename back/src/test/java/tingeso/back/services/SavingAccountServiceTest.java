package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.SavingAccountRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SavingAccountServiceTest {

    @InjectMocks
    private SavingAccountService savingAccountService;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private CustomerWorksheetRepository worksheetRepository;

    private SavingAccountEntity account;
    private CustomerWorksheetEntity worksheetEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new SavingAccountEntity();
        account.setRut("12345678");
        account.setBalance(100000);
        account.setNegativePoints(2);
        account.setPeriodicDeposit(5000);
        account.setSeniority(3);
        account.setRetiredCash(30000);
        account.setRecentlyRetiredCash(5000);

        worksheetEntity = new CustomerWorksheetEntity();
        worksheetEntity.setRut("12345678");
        worksheetEntity.setSalary(50000);

        when(savingAccountRepository.findByRut("12345678")).thenReturn(account);
        when(worksheetRepository.findByRut("12345678")).thenReturn(worksheetEntity);
    }

    @Test
    public void testGetAll() {
        when(savingAccountRepository.findAll()).thenReturn(List.of(account));
        assertNotNull(savingAccountService.getAll());
    }

    @Test
    public void testFindByRut() {
        SavingAccountEntity foundAccount = savingAccountService.findByRut("12345678");
        assertNotNull(foundAccount);
        assertEquals("12345678", foundAccount.getRut());
    }

    @Test
    public void testR71() {
        savingAccountService.R71("12345678");
        assertEquals(3, account.getNegativePoints());  // Debe incrementar en 1
    }

    @Test
    public void testR72consistentHistory() {
        savingAccountService.R72consistentHistory("12345678");
        assertEquals(2, account.getNegativePoints());  // Debe incrementar en 1
    }

    @Test
    public void testR73periodicDeposit() {
        savingAccountService.R73periodicDeposit("12345678");
        assertEquals(3, account.getNegativePoints());  // Debe incrementar en 1
    }

    @Test
    public void testR74balanceSeniority() {
        savingAccountService.R74balanceSeniority("12345678");
        assertEquals(3, account.getNegativePoints());  // Debe incrementar en 1 según la seniority
    }

    @Test
    public void testR75recentlyRecent() {
        savingAccountService.R75recentlyRecent("12345678");
        assertEquals(3, account.getNegativePoints());  // Debe incrementar en 1
    }

    @Test
    public void testRateEvaluationSolid() {
        account.setNegativePoints(5);  // Suficientes puntos negativos para tener capacidad sólida
        savingAccountService.rateEvaluation("12345678");
        assertEquals(1, account.getTypeCapacity());  // Tipo de capacidad "sólida"
    }

    @Test
    public void testRateEvaluationModerate() {
        account.setNegativePoints(3);  // Puntos negativos para tener capacidad moderada
        savingAccountService.rateEvaluation("12345678");
        assertEquals(1, account.getTypeCapacity());  // Tipo de capacidad "moderada"
    }

    @Test
    public void testRateEvaluationInsufficient() {
        account.setNegativePoints(2);  // Pocos puntos negativos, tipo insuficiente
        savingAccountService.rateEvaluation("12345678");
        assertEquals(1, account.getTypeCapacity());  // Tipo de capacidad "insuficiente"
    }

    @Test
    public void testRateEvaluationNoNegativePoints() {
        account.setNegativePoints(0);  // Ningún punto negativo, tipo insuficiente
        savingAccountService.rateEvaluation("12345678");
        assertEquals(3, account.getTypeCapacity());  // Tipo de capacidad "insuficiente"
    }

    @Test
    public void testR71BalanceCheck() {
        account.setBalance(6000);
        savingAccountService.R71("12345678");
        assertEquals(3, account.getNegativePoints());  // No debe incrementarse porque el balance no supera el 10% del salario
    }

    @Test
    public void testR72HistoryRetiredCheck() {
        account.setRetiredCash(35000);  // Mayor que el 50% del balance
        savingAccountService.R72consistentHistory("12345678");
        assertEquals(2, account.getNegativePoints());  // No debe incrementarse
    }

    @Test
    public void testR73PeriodicDepositCheck() {
        account.setPeriodicDeposit(3000);  // Menor que el 5% del salario
        savingAccountService.R73periodicDeposit("12345678");
        assertEquals(3, account.getNegativePoints());  // No debe incrementarse
    }

    @Test
    public void testR74BalanceSeniorityCheck() {
        account.setSeniority(1);  // Seniority menor a 2
        account.setBalance(15000);  // Balance mayor al 20% del balance
        savingAccountService.R74balanceSeniority("12345678");
        assertEquals(3, account.getNegativePoints());  // No debe incrementarse
    }

    @Test
    public void testR75RecentlyRetiredCheck() {
        account.setRecentlyRetiredCash(40000);  // Mayor que el 70% del balance
        savingAccountService.R75recentlyRecent("12345678");
        assertEquals(3, account.getNegativePoints());  // No debe incrementarse
    }
}
