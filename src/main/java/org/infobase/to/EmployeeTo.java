package org.infobase.to;

import lombok.*;

import org.infobase.model.HasId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Класс описывающий сотрудника, используется для отображение данных в интерфейсе
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTo implements HasId {
    /** Поле ID */
    private Integer id;

    /** Имя сотрудника */
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    /** Дата рождения */
    @NotNull
    private LocalDate birthDate;

    /** Электронная почта */
//    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    /** Название компании в которой работает сотрудник */
    @NotBlank
    private String companyName;
}
