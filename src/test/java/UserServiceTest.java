import com.example.BookManagement.user.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testInvalidPassword() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("testuser");
        dto.setPassword("123"); // rõ ràng không hợp lệ
        dto.setEmail("user@example.com");
        dto.setRole("CUSTOMER");

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);

        // In ra lỗi
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));

        // Chắc chắn có lỗi
        assertEquals(true, violations.size() > 0);
    }

    @Test
    void testValidUser() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("TestUser1");
        dto.setPassword("Password1!"); // hợp lệ
        dto.setEmail("user@example.com");
        dto.setRole("CUSTOMER");

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);

        // Không có lỗi
        assertEquals(0, violations.size());
    }
}
