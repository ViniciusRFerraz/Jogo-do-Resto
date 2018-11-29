package com.vinicius.game.states;

/**
 * Created by Vinicius Ferraz on 07/09/2016.
 */
class Casa {
    public int index;
    public int valor;
    public int posx;
    public int posy;
    public Casa prox;

    public Casa(int index, int valor, int posx, int posy) {
        this.index = index;
        this.valor = valor;
        this.posx = posx;
        this.posy = posy;
        prox = null;
    }
}

class Tabuleiro {
    Casa primeira,ultima,atual,posplayer;
    int totalcasas;

    public Tabuleiro() {
        primeira = null;
        totalcasas = 0;
    }

    public boolean check(){
        if(totalcasas == 0) return true;
        return false;
    }

    public void inserir(Casa cs){
        if(check()){
            primeira = atual = ultima = posplayer = cs;
        }else{
            ultima.prox = cs;
            ultima = cs;
        }
        totalcasas ++;
    }

}
