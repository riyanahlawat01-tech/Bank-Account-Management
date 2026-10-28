package com.bankapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

/**
 * JUnit 5 Test Suite for Bank Account Manager.
 * Tests: BankAccount and Bank classes.
 *
 * Student: Riyan Ahlawat | SAP-ID: 590015207 | Roll: 24010113016
 */
@DisplayName("Bank Account Manager - JUnit 5 Test Suite")
class BankAccountTest {

    private BankAccount account;
    private Bank bank;

    // ── TC-001: BeforeAll ────────────────────────────────────────────────────
    @BeforeAll
    static void setupClass() {
        System.out.println("=== BankAccount Test Suite Starting ===");
    }

    // ── TC-002: BeforeEach ───────────────────────────────────────────────────
    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC001", "Riyan Ahlawat", 1000.0);
        bank    = new Bank("RiyanBank");
    }

    // ── TC-003: AfterEach ────────────────────────────────────────────────────
    @AfterEach
    void tearDown() {
        account = null;
        bank    = null;
    }

    // ── TC-004: AfterAll ─────────────────────────────────────────────────────
    @AfterAll
    static void tearDownClass() {
        System.out.println("=== BankAccount Test Suite Completed ===");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  FEATURE 1: CREATE ACCOUNT
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-001: Create account with valid data - account not null")
    void testCreateAccount_Valid() {
        BankAccount acc = bank.createAccount("ACC100", "John Doe", 5000.0);
        assertNotNull(acc);
    }

    @Test
    @DisplayName("TC-002: Create account - holder name matches")
    void testCreateAccount_HolderName() {
        BankAccount acc = bank.createAccount("ACC101", "Jane Doe", 2000.0);
        assertEquals("Jane Doe", acc.getHolderName());
    }

    @Test
    @DisplayName("TC-003: Create account - initial balance correct")
    void testCreateAccount_InitialBalance() {
        BankAccount acc = bank.createAccount("ACC102", "Test User", 3000.0);
        assertEquals(3000.0, acc.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-004: Create duplicate account throws exception")
    void testCreateAccount_Duplicate_ThrowsException() {
        bank.createAccount("ACC103", "User A", 1000.0);
        assertThrows(IllegalArgumentException.class,
            () -> bank.createAccount("ACC103", "User B", 500.0));
    }

    @Test
    @DisplayName("TC-005: Create account with empty name throws exception")
    void testCreateAccount_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("ACC104", "", 1000.0));
    }

    // ════════════════════════════════════════════════════════════════════════
    //  FEATURE 2: DEPOSIT
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-006: Deposit valid amount increases balance")
    void testDeposit_ValidAmount() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-007: Deposit negative amount throws exception")
    void testDeposit_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.deposit(-100.0));
    }

    @Test
    @DisplayName("TC-008: Deposit zero throws exception")
    void testDeposit_Zero_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.deposit(0));
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 500.0, 10000.0})
    @DisplayName("TC-009: Parameterized - multiple valid deposit amounts")
    void testDeposit_MultipleAmounts(double amount) {
        double before = account.getBalance();
        account.deposit(amount);
        assertEquals(before + amount, account.getBalance(), 0.01);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  FEATURE 3: WITHDRAW
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-010: Withdraw valid amount decreases balance")
    void testWithdraw_ValidAmount() {
        account.withdraw(400.0);
        assertEquals(600.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-011: Withdraw more than balance throws exception")
    void testWithdraw_ExceedsBalance_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.withdraw(5000.0));
    }

    @Test
    @DisplayName("TC-012: Withdraw negative amount throws exception")
    void testWithdraw_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.withdraw(-50.0));
    }

    @Test
    @DisplayName("TC-013: Withdraw exact balance leaves zero")
    void testWithdraw_ExactBalance() {
        account.withdraw(1000.0);
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    @ParameterizedTest
    @MethodSource("validWithdrawAmounts")
    @DisplayName("TC-014: Parameterized - valid withdraw amounts succeed")
    void testWithdraw_ParameterizedValid(double amount) {
        assertDoesNotThrow(() -> account.withdraw(amount));
    }

    static Stream<Double> validWithdrawAmounts() {
        return Stream.of(1.0, 500.0, 999.99);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  FEATURE 4: TRANSFER
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-015: Transfer reduces sender balance")
    void testTransfer_ReducesSenderBalance() {
        BankAccount target = new BankAccount("ACC200", "Target", 0.0);
        account.transfer(target, 300.0);
        assertEquals(700.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-016: Transfer increases receiver balance")
    void testTransfer_IncreasesReceiverBalance() {
        BankAccount target = new BankAccount("ACC201", "Target", 0.0);
        account.transfer(target, 300.0);
        assertEquals(300.0, target.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-017: Transfer with null target throws exception")
    void testTransfer_NullTarget_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.transfer(null, 100.0));
    }

    @Test
    @DisplayName("TC-018: Transfer exceeding balance throws exception")
    void testTransfer_ExceedsBalance_ThrowsException() {
        BankAccount target = new BankAccount("ACC202", "Target", 0.0);
        assertThrows(IllegalArgumentException.class,
            () -> account.transfer(target, 9999.0));
    }

    // ════════════════════════════════════════════════════════════════════════
    //  FEATURE 5: VIEW STATEMENT
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-019: Statement is not null")
    void testStatement_NotNull() {
        assertNotNull(account.getStatement());
    }

    @Test
    @DisplayName("TC-020: Statement contains account holder name")
    void testStatement_ContainsHolderName() {
        assertTrue(account.getStatement().contains("Riyan Ahlawat"));
    }

    @Test
    @DisplayName("TC-021: Statement contains account number")
    void testStatement_ContainsAccountNumber() {
        assertTrue(account.getStatement().contains("ACC001"));
    }

    @Test
    @DisplayName("TC-022: Statement reflects deposit transaction")
    void testStatement_ReflectsDeposit() {
        account.deposit(200.0);
        assertTrue(account.getStatement().contains("Deposited"));
    }

    // ════════════════════════════════════════════════════════════════════════
    //  BOUNDARY TESTS
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-023: Boundary - deposit minimum value 0.01")
    void testDeposit_MinimumBoundary() {
        assertDoesNotThrow(() -> account.deposit(0.01));
    }

    @Test
    @DisplayName("TC-024: Boundary - withdraw exactly balance amount")
    void testWithdraw_BoundaryExact() {
        assertDoesNotThrow(() -> account.withdraw(1000.0));
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-025 - Disabled: Large transfer (pending)")
    @Disabled("Feature for large amount transfer limit not yet implemented")
    void testTransfer_LargeAmount() {
        // To be implemented
    }

    // ════════════════════════════════════════════════════════════════════════
    //  GROUPED ASSERTION
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("TC-026: assertAll - validate full account state after deposit")
    void testAccountState_AfterDeposit_AssertAll() {
        account.deposit(500.0);
        assertAll("account state after deposit",
            () -> assertEquals(1500.0, account.getBalance(), 0.01),
            () -> assertNotNull(account.getStatement()),
            () -> assertEquals("Riyan Ahlawat", account.getHolderName()),
            () -> assertFalse(account.getTransactions().isEmpty())
        );
    }
}
