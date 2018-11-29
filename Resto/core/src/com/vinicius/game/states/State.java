package com.vinicius.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Vinicius Ferraz on 26/08/2016.
 */
public class State implements InputProcessor, Input.TextInputListener {
    public OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    public static int pontos;

    public State(GameStateManager gsm) {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();

    }

    public State() {

    }

    protected void handleInput() {

    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) {
               return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void input(String text){
    }

    @Override
    public void canceled() {

    }
}

