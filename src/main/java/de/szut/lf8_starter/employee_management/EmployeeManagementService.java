    package de.szut.lf8_starter.employee_management;

    import de.szut.lf8_starter.employee_management.model.SkillDto;
    import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.HttpServerErrorException;

    import java.util.Arrays;
    import java.util.Set;
    import java.util.stream.Collectors;

    @Service
    public class EmployeeManagementService {

        private final EmployeeManagementApiClient apiClient;

        public EmployeeManagementService(EmployeeManagementApiClient apiClient) {
            this.apiClient = apiClient;
        }

        public boolean VerifyEmployeeIsQualifiedForProject(int id, Object projectEntiy) {
            return false; //todo: implement this with `ProjectEntity`
        }

        public boolean VerifyEmployeeExists(int id) {
            return apiClient.getEmployeeById(id).isPresent();
        }

        public boolean VerifyEmployeeIsQualified(int id, int... qualifications) {
            if (qualifications == null || qualifications.length == 0) {
                return false;
            }

            var employeeOpt = apiClient.getEmployeeById(id);

            if (employeeOpt.isEmpty()) {
                return false;
            }

            var employee = employeeOpt.get();

            var skills = employee.getSkillSet();

            if (skills == null) {
                return false;
            }

            return Arrays.stream(qualifications)
                    .allMatch(q -> skills.stream()
                            .map(SkillDto::getId)
                            .anyMatch(skillId -> skillId == q));
        }
    }
