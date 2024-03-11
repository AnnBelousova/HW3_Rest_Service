import com.example.hw3_rest_service.dao.ClaimDao;
import com.example.hw3_rest_service.dao.ProductDao;
import com.example.hw3_rest_service.dao.UserDao;
import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.entity.Product;
import com.example.hw3_rest_service.entity.User;
import com.example.hw3_rest_service.model.*;
import com.example.hw3_rest_service.service.ClaimService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;


class TestServiceClaim {
    private List<User> createUserList() {
        User user1 = new User(1, "user1", "email1");
        User user2 = new User(2, "user2", "email2");
        User user3 = new User(3, "user3", "email3");
        List<User> userList = List.of(user1, user2, user3);
        return userList;
    }

    private List<Claim> createClaimList() {
        List<Integer> productIdList = createProductList().stream().map(item -> item.getId()).collect(Collectors.toList());
        Claim claim1 = new Claim(1, "description1", 1, productIdList);
        Claim claim2 = new Claim(2, "description2", 2, productIdList.stream().limit(1).collect(Collectors.toList()));
        Claim claim3 = new Claim(3, "description3", 1, productIdList.stream().skip(1).limit(2).collect(Collectors.toList()));
        List<Claim> claimList = List.of(claim1, claim2, claim3);
        return claimList;
    }

    private List<Product> createProductList() {
        Product product1 = new Product(1, "product1", 100, null);
        Product product2 = new Product(2, "product2", 200, null);
        Product product3 = new Product(3, "product3", 300, null);

        List<Product> prodcutList = List.of(product1, product2, product3);
        return prodcutList;
    }

    @Test
    public void testGetListOfClaim() {
        List<User> userList = createUserList();
        List<Product> productList = createProductList();
        List<Claim> claimList = createClaimList();
        ClaimDao claimDao = Mockito.mock(ClaimDao.class);
        Mockito.when(claimDao.getAllClaims()).thenReturn(claimList);

        UserDao userDao = Mockito.mock(UserDao.class);
        for (User user : userList) {
            Mockito.when(userDao.getUserById(user.getId())).thenReturn(user);
        }

        ProductDao productDao = Mockito.mock(ProductDao.class);
        for (Product product : productList) {
            Mockito.when(productDao.getProductById(product.getId())).thenReturn(product);
        }
        ClaimService claimService = new ClaimService(claimDao, userDao, productDao);
        String claims = claimService.getAllClaims();
        Assertions.assertNotNull(claims);
        Gson gson = new Gson();
        ResponseClaimList responseClaimList = gson.fromJson(claims, ResponseClaimList.class);
        Assertions.assertTrue(responseClaimList.isSuccess());
        Assertions.assertEquals(claimList.size(), responseClaimList.getTotal());
        Assertions.assertEquals(claimList.size(), responseClaimList.getClaimList().size());
        Assertions.assertArrayEquals(claimList.stream().map(item -> item.getId()).sorted().toArray(), responseClaimList.getClaimList().stream().map(item -> item.getId()).sorted().toArray());
    }

    @Test
    public void testUpdateClaim() {
        List<Claim> claimList = createClaimList();
        List<User> userList = createUserList();
        List<Product> productList = createProductList();

        UserDao userDao = Mockito.mock(UserDao.class);
        for (User user : userList) {
            Mockito.when(userDao.getUserById(user.getId())).thenReturn(user);
        }

        ProductDao productDao = Mockito.mock(ProductDao.class);
        for (Product product : productList) {
            Mockito.when(productDao.getProductById(product.getId())).thenReturn(product);
        }

        Claim claim = claimList.get(0);
        claim.setId(claim.getId());
        claim.setDescription("new description");

        ClaimDao claimDao = Mockito.mock(ClaimDao.class);

        Mockito.when(claimDao.updateClaim(claim)).thenReturn(claim);

        ClaimService claimService = new ClaimService(claimDao, userDao, productDao);
        Gson gson = new Gson();
        String result = claimService.updateClaim(gson.toJson(claim));

        ResponseUpdateClaim responseUpdateClaim = gson.fromJson(result, ResponseUpdateClaim.class);
        Assertions.assertTrue(responseUpdateClaim.isSuccess());
        Assertions.assertNull(responseUpdateClaim.getError());
        Assertions.assertNotNull(claim);
        Assertions.assertEquals(claim.getDescription(), responseUpdateClaim.getClaimDto().getDescription());
    }

    @Test
    public void testInsertClaim() {
        List<Claim> claimList = createClaimList();
        List<User> userList = createUserList();
        List<Product> productList = createProductList();

        UserDao userDao = Mockito.mock(UserDao.class);
        for (User user : userList) {
            Mockito.when(userDao.getUserById(user.getId())).thenReturn(user);
        }

        ProductDao productDao = Mockito.mock(ProductDao.class);
        for (Product product : productList) {
            Mockito.when(productDao.getProductById(product.getId())).thenReturn(product);
        }

        Claim claim = claimList.get(0);
        claim.setId(10);

        ClaimDao claimDao = Mockito.mock(ClaimDao.class);

        Mockito.when(claimDao.insertClaim(claim)).thenReturn(claim);

        ClaimService claimService = new ClaimService(claimDao, userDao, productDao);
        Gson gson = new Gson();
        String result = claimService.addClaim(gson.toJson(claim));

        ResponseAddClaim responseAddClaim = gson.fromJson(result, ResponseAddClaim.class);
        Assertions.assertTrue(responseAddClaim.isSuccess());
        Assertions.assertNull(responseAddClaim.getError());
        Assertions.assertNotNull(claim);
        Assertions.assertEquals(claim.getId(), responseAddClaim.getClaimDto().getId());
    }

    @Test
    public void testGetClaimByEmail() {
        List<User> userList = createUserList();
        List<Product> productList = createProductList();
        List<Claim> claimList = createClaimList();
        List<Claim> claimListNew = claimList.stream().filter(item -> item.getUser_id() == 2).collect(Collectors.toList());
        String email = "email2";
        ClaimDao claimDao = Mockito.mock(ClaimDao.class);
        Mockito.when(claimDao.getClaimListByEmail(email)).thenReturn(claimListNew);

        UserDao userDao = Mockito.mock(UserDao.class);
        for (User user : userList) {
            Mockito.when(userDao.getUserById(user.getId())).thenReturn(user);
        }

        ProductDao productDao = Mockito.mock(ProductDao.class);
        for (Product product : productList) {
            Mockito.when(productDao.getProductById(product.getId())).thenReturn(product);
        }
        ClaimService claimService = new ClaimService(claimDao, userDao, productDao);
        String claim = claimService.getClaimListByEmail(email);
        Assertions.assertNotNull(claim);
        Gson gson = new Gson();
        ResponseClaimList responseClaimList = gson.fromJson(claim, ResponseClaimList.class);
        Assertions.assertTrue(responseClaimList.isSuccess());
        Assertions.assertEquals(claimListNew.stream().map(item -> item.getId()).collect(Collectors.toList()), responseClaimList.getClaimList().stream().map(item -> item.getId()).collect(Collectors.toList()));
    }

    @Test
    public void testDeleteClaim() {
        ClaimDao claimDao = Mockito.mock(ClaimDao.class);
        Mockito.when(claimDao.deleteClaim(1)).thenReturn(true);
        Mockito.when(claimDao.deleteClaim(2)).thenReturn(false);
        ClaimService claimService = new ClaimService(claimDao, null, null);

        String answer1 = claimService.deleteClaim(1);
        String answer2 = claimService.deleteClaim(2);
        Gson gson = new Gson();
        ResponseDeleteClaim responseDeleteClaim1 = gson.fromJson(answer1, ResponseDeleteClaim.class);
        ResponseDeleteClaim responseDeleteClaim2 = gson.fromJson(answer2, ResponseDeleteClaim.class);
        Assertions.assertTrue(responseDeleteClaim1.isSuccess());
        Assertions.assertFalse(responseDeleteClaim2.isSuccess());
    }
}
