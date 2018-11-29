package com.vinicius.game.sprites;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.vinicius.game.states.PlayState;
import com.vinicius.game.states.State;

/**
 * Created by Vinicius Ferraz on 26/08/2016.
 */

public class Player extends Sprite implements InputProcessor {
    private Vector3 position;
    private Texture player;

    public Player(int x, int y){
        position = new Vector3(x, y, 0);
        player = new Texture("player.png");

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return player;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

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

    public void update(float dt) {
    }
}
