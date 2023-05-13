package it.uniroma3.siw.siwmovievendetta.model;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] bytes;

    @Lob
    private String base64Image;

    public Picture(){

    }

    public Picture(byte[] bytes){
        this.bytes = bytes;
        this.setBase64Image(Base64.getEncoder().encodeToString(this.bytes));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
