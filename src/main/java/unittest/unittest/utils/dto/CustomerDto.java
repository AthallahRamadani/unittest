package unittest.unittest.utils.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    @NotBlank(message = "Name must no be blank")
    private String name;
    @NotBlank(message = "BirthDate must no be blank")
    private LocalDate birthDate;

    private MultipartFile imageFile;
}
