package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewProjectDescription {

    private Project parentProject;
    private String id;
    private String name;
    private boolean copyAllAssociatedSettings;
}
