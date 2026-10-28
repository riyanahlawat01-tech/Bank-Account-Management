============================================================
  Bank Account Manager - README
  Student : Riyan Ahlawat
  SAP-ID  : 590015207
  Roll No : 24010113016
============================================================

JAVA VERSION  : JDK 17+
BUILD TOOL    : Maven 3.8+
IDE TESTED IN : IntelliJ IDEA / Eclipse

------------------------------------------------------------
HOW TO IMPORT IN IntelliJ IDEA
------------------------------------------------------------
1. Open IntelliJ IDEA
2. File > Open > Select the BankApp folder
3. IntelliJ will auto-detect pom.xml and configure Maven
4. Wait for indexing to complete

HOW TO IMPORT IN Eclipse
------------------------------------------------------------
1. File > Import > Maven > Existing Maven Projects
2. Browse to BankApp folder > Finish

------------------------------------------------------------
COMPILE & RUN
------------------------------------------------------------
  mvn compile
  mvn exec:java -Dexec.mainClass="com.bankapp.BankApp"

  OR in IDE: Right-click BankApp.java > Run

------------------------------------------------------------
RUN ALL JUNIT TESTS
------------------------------------------------------------
  mvn test

  Single class : mvn test -Dtest=BankAccountTest
  Single method: mvn test -Dtest=BankAccountTest#testDeposit_ValidAmount
  HTML Report  : mvn surefire-report:report
                 (opens: target/site/surefire-report.html)

  In IntelliJ: Right-click BankAccountTest.java > Run
  In Eclipse : Right-click BankAccountTest.java > Run As > JUnit Test

------------------------------------------------------------
KNOWN LIMITATIONS
------------------------------------------------------------
  - Data is stored in-memory only (no file/database persistence)
  - TC-025 is @Disabled (large transfer limit not implemented)
============================================================
