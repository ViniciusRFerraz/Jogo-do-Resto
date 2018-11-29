package com.vinicius.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.vinicius.game.Resto;

/** Created by Vinicius Ferraz. **/

public class MenuState extends State {
    private Texture Background;
    private Texture btnPlay;
    private Texture btnInfo;
    private Texture msgInfo;
    public PlayState game;
    public InfoState info;
    public boolean cancatch = false;
    public String resp = "0";
    private Vector3 touchPoint;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        Background = new Texture("Background.png");
        btnPlay = new Texture("btnPlay.png");
        btnInfo = new Texture("btnInfo.png");
        msgInfo = new Texture("msgInfo.png");
        SpriteBatch sb = new SpriteBatch();
        touchPoint = new Vector3();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resto.WIDTH / 2, Resto.HEIGHT / 2);
        camera.viewportWidth = Resto.WIDTH;
        camera.viewportHeight = Resto.HEIGHT;
        render(sb);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        camera.update();
        handleInput();
    }


    // Função que controla o input da tela
    public static class OverlapTester {
        public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
            if (r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y)
                return true;
            else
                return false;
        }

        public static boolean pointInRectangle(Rectangle r, Vector2 p) {
            return r.x <= p.x && r.x + r.width >= p.x && r.y <= p.y && r.y + r.height >= p.y;
        }

        public static boolean pointInRectangle(Rectangle r, float x, float y) {
            try {
                return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
            } catch (NullPointerException e) {
                System.out.println("Click Nulo - MenuState");
            }
            return false;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sb.setProjectionMatrix(camera.combined);
        camera.position.set(Resto.WIDTH / 2, Resto.HEIGHT / 2, 0);
        sb.begin();
        sb.draw(Background, 0, 0, Resto.WIDTH, Resto.HEIGHT);
        sb.draw(btnPlay, (Resto.WIDTH / 2) - (btnPlay.getWidth() / 2), (Resto.HEIGHT / 2));
        sb.draw(btnInfo, (Resto.WIDTH / 2) - (btnInfo.getWidth() / 2), (Resto.HEIGHT / 2) - 150);


        // Seleção do Botão Play
        Rectangle BtPlay = new Rectangle();
        BtPlay.setSize(btnPlay.getWidth(),btnPlay.getHeight());
        BtPlay.setPosition((Resto.WIDTH/2) - (btnPlay.getWidth() / 2), (Resto.HEIGHT) / 2);

        // Seleção do Botão Info
        Rectangle BtInfo = new Rectangle();
        BtInfo.setSize(btnInfo.getWidth(),btnInfo.getHeight());
        BtInfo.setPosition((Resto.WIDTH / 2) - (btnInfo.getWidth() / 2), (Resto.HEIGHT / 2) - 150);


        if (cancatch) {
            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (OverlapTester.pointInRectangle(BtPlay, touchPoint.x, touchPoint.y)) {
                    super.pontos = 0;
                    game = new PlayState(gsm);
                    gsm.push(game);
                    dispose();
                }
                if (OverlapTester.pointInRectangle(BtInfo, touchPoint.x, touchPoint.y)) {
                    info = new InfoState(gsm);
                    gsm.push(info);
                    dispose();
                }


            }
        }
        cancatch = true;
        sb.end();



        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("!! THE BACK KEY WAS PRESSED BRO !!");
            Gdx.input.getTextInput(this, "Deseja Realmente Sair ???? ", "", "y or n");
        }

        if(verificasaida()){
            gsm.pop();
            dispose();
            Gdx.app.exit();
        }
    }

    public boolean verificasaida(){
        if(resp.equals("y") || resp.equals("Y")){
            System.out.println("Voltando");
            return true;
        }
        return false;
    }

    public void input(String text) {
        this.resp = text;
    }

    @Override
    public void dispose() {
        Background.dispose();
        btnPlay.dispose();
        btnInfo.dispose();
        msgInfo.dispose();
    }
}