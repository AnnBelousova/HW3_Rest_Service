import com.example.hw3_rest_service.dao.ClaimDao;
import com.example.hw3_rest_service.entity.Claim;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.List;

@Testcontainers
public class TestClaimDAO extends PostgreSQLContainer<TestClaimDAO> {

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("restdb")
            .withUsername("postgres")
            .withPassword("123")
            .withInitScript("script.sql");
    ClaimDao claimDao;

    @BeforeAll
    static void runContainer() {
        Startables.deepStart(container);
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword()
        );
        claimDao = new ClaimDao();
        claimDao.setConnection(dbConnectionProvider.getConnection());
    }

    @Test
    void testGetAllClaims() {
        List<Claim> claimList = claimDao.getAllClaims();
        Assertions.assertFalse(claimList.isEmpty());
    }

    @Test
    void testInsertClaim() {
        Claim claim = new Claim();
        claim.setDescription("description3");
        claim.setUser_id(1);
        claim.setProductId(List.of(1, 2));
        List<Claim> claimList = claimDao.getAllClaims();
        int beforeInsert = claimList.size();
        Claim addedClaim = claimDao.insertClaim(claim);
        Assertions.assertNotNull(addedClaim);
        claimList = claimDao.getAllClaims();
        int afterInsert = claimList.size();
        Assertions.assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    void testGetClaimByEmail() {
        String email1 = "email1";
        String email2 = "email2";
        List<Claim> claimList1 = claimDao.getClaimListByEmail(email1);
        List<Claim> claimList2 = claimDao.getClaimListByEmail(email2);
        Assertions.assertFalse(claimList1.isEmpty());
        Assertions.assertTrue(claimList2.isEmpty());
    }

    @Test
    void testDeleteClaimById() {
        List<Claim> claimList = claimDao.getAllClaims();
        int beforeDeleting = claimList.size();
        boolean result = claimDao.deleteClaim(claimList.get(0).getId());
        Assertions.assertTrue(result);
        claimList = claimDao.getAllClaims();
        int afterDeleting = claimList.size();
        Assertions.assertEquals(beforeDeleting - 1, afterDeleting);
    }

    @Test
    void testUpdateClaim() {
        List<Claim> claimList = claimDao.getAllClaims();
        Claim claim = claimList.get(0);
        Claim updatedClaim = new Claim();
        updatedClaim.setId(claim.getId());
        updatedClaim.setDescription("updated description");
        updatedClaim.setUser_id(claim.getUser_id());
        updatedClaim.setProductId(List.of(3));
        Claim result = claimDao.updateClaim(updatedClaim);
        Assertions.assertNotNull(result);
        Claim newClaim = claimDao.getClaimById(claim.getId());
        Assertions.assertEquals(updatedClaim.getId(), newClaim.getId());
        Assertions.assertEquals(updatedClaim.getDescription(), newClaim.getDescription());
        Assertions.assertEquals(updatedClaim.getUser_id(), newClaim.getUser_id());
        Assertions.assertArrayEquals(updatedClaim.getProductId().toArray(), newClaim.getProductId().toArray());
    }

    @Test
    void testGetClaimById() {
        List<Claim> claimList = claimDao.getAllClaims();
        Claim claim = claimList.get(0);
        int id = claim.getId();
        Claim result = claimDao.getClaimById(id);
        Assertions.assertEquals(claim.getId(), result.getId());
        Assertions.assertEquals(claim.getDescription(), result.getDescription());
        Assertions.assertEquals(claim.getUser_id(), result.getUser_id());
        Assertions.assertArrayEquals(claim.getProductId().toArray(), result.getProductId().toArray());
    }
}
