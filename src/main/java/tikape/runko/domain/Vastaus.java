package tikape.runko.domain;

/**
 *
 * @author papirila
 */
public class Vastaus {
    
    private Integer id;
    private String vastausteksti;
    private Boolean oikein;
    private Integer kysymys_id;

    public Vastaus(Integer id, String vastausteksti, Boolean oikein, Integer kysymys_id) {
        this.id = id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getVastausteksti() {
        return vastausteksti;
    }

    public void setVastausteksti(String vatsausteksti) {
        this.vastausteksti=vastausteksti;
    }

    public Boolean getOikein() {
        return oikein;
    }

    public void setOikein(Boolean oikein) {
        this.oikein = oikein;
    }
    
    public Integer getKysymys_id() {
        return kysymys_id;
    }

    
}
