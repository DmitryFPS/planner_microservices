package ru.orlov.micro.planner.plannerusers.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchValues {
    // поля поиска
    private String email;
    private String username;

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;
}