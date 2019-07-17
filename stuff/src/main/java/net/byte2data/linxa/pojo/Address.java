package net.byte2data.linxa.pojo;

import java.io.Serializable;

public class Address extends OtherAddress implements Serializable {

    private static final long serialVersionUID = 7922505822814669671L;

    private String sokak;
    private String cadde;
    private String mahalle;
    private Integer binaNo;
    private Integer daireNo;


    public Address(String sokak, String cadde, String mahalle, Integer binaNo, Integer daireNo){
        this.sokak=sokak;
        this.cadde=cadde;
        this.mahalle=mahalle;
        this.binaNo=binaNo;
        this.daireNo=daireNo;
    }

    public String getSokak() {
        return sokak;
    }

    public void setSokak(String sokak) {
        this.sokak = sokak;
    }

    public String getCadde() {
        return cadde;
    }

    public void setCadde(String cadde) {
        this.cadde = cadde;
    }

    public String getMahalle() {
        return mahalle;
    }

    public void setMahalle(String mahalle) {
        this.mahalle = mahalle;
    }

    public Integer getBinaNo() {
        return binaNo;
    }

    public void setBinaNo(Integer binaNo) {
        this.binaNo = binaNo;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "sokak='" + sokak + '\'' +
                ", cadde='" + cadde + '\'' +
                ", mahalle='" + mahalle + '\'' +
                ", binaNo=" + binaNo +
                ", daireNo=" + daireNo +
                '}';
    }
}
