package ma.youcode.teleexpertise.model;

public class Consultation {

    public enum Statut {
        EN_ATTENTE,
        EN_COURS,
        TERMINEE
    }

    private int id;
    private int patientId;
    private String motif;
    private String observations;
    private String diagnostic;
    private String traitement;
    private double cout;
    private Statut statut;

    public Consultation() {
    }

    public Consultation(int id, int patientId, String motif, String observations,
                        String diagnostic, String traitement,
                        double cout, Statut statut) {
        this.id = id;
        this.patientId = patientId;
        this.motif = motif;
        this.observations = observations;
        this.diagnostic = diagnostic;
        this.traitement = traitement;
        this.cout = cout;
        this.statut = statut;
    }

    public Consultation(int patientId, String motif, String observations,
                        String diagnostic, String traitement) {
        this.patientId = patientId;
        this.motif = motif;
        this.observations = observations;
        this.diagnostic = diagnostic;
        this.traitement = traitement;
        this.cout = 150.0;
        this.statut = Statut.EN_ATTENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}