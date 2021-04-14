package org.infobase.to;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTo {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private LocalDate birthDate;

//    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    private String companyName;
}
