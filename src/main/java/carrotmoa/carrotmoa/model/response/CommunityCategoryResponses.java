package carrotmoa.carrotmoa.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
public class CommunityCategoryResponses {
    private List<CommunityCategoryResponse> categories = new ArrayList<>();

    public CommunityCategoryResponses(List<CommunityCategoryResponse> categories) {
        this.categories = categories;
    }
}
