package ma.fstt.backend_empolyee.dto;

public record EmployeeDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Double salary
) {}