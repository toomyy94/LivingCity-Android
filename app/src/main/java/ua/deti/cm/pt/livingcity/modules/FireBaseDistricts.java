package ua.deti.cm.pt.livingcity.modules;

import java.util.List;

/**
 * Created by Tomás on 30/06/2016.
 */
public class FireBaseDistricts {

    private int id;
    private String nome;
    private List coordenadas;

    public FireBaseDistricts(int id, String nome, List coordenadas){
        this.id = id;
        this.nome = nome;
        this.coordenadas = coordenadas;

    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public List getCoordenadas() {
        return coordenadas;
    }


}