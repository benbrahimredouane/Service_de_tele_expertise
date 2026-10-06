package ma.youcode.teleexpertise.dto;

public class CreerDemandeRequest {

    private Integer consultationId;
    private Integer specialisteId;
    private String question;
    private String priorite;

    public CreerDemandeRequest() {
    }

    public Integer getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Integer consultationId) {
        this.consultationId = consultationId;
    }

    public Integer getSpecialisteId() {
        return specialisteId;
    }

    public void setSpecialisteId(Integer specialisteId) {
        this.specialisteId = specialisteId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }
}