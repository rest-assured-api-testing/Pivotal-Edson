package entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class WorkSpaces {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String kind;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String person_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<Integer> project_ids;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public List<Integer> getProject_ids() {
        return project_ids;
    }

    public void setProject_ids(List<Integer> project_ids) {
        this.project_ids = project_ids;
    }
}