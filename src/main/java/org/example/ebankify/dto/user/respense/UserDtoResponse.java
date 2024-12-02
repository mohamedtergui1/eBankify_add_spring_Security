package org.example.ebankify.dto.user.respense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private Long id;

    private String name;

    private String email;

    private int age;

    private Double monthlyIncome;

    private int creditScore;

}
