package com.vinicius.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.vinicius.game.Resto;

/** Created by Vinicius Ferraz. **/

public class WinState extends State {
    private Texture Background;
    private Texture btnMenu;
    private Texture Wintext;
    String strpontos = "";
    private BitmapFont bitfont;
    public MenuState menu;
    public String resp = "0";
    public MenuState newmenu;
    private Vector3 touchPoint;
    private boolean cancatch = false;

    public WinState(GameStateManager gsm) {
        super(gsm);
        Background = new Texture("Background.png");
        btnMenu = new Texture("btnMenu.png");
        Wintext = new Texture("WinScreen.png");
       // Wintext2 = new Texture("WinScreen2.png");
        SpriteBatch sb = new SpriteBatch();
        touchPoint = new Vector3();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resto.WIDTH/2 , Resto.HEIGHT/2);
        camera.viewportWidth = Resto.WIDTH;
        camera.viewportHeight = Resto.HEIGHT;
        bitfont = new BitmapFont(Gdx.files.internal("Vinni.fnt"), false);
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
                System.out.println("Click Nulo - InfoState");
            }
            return false;
        }

    }



    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sb.setProjectionMatrix(camera.combined);
        camera.position.set(Resto.WIDTH/2,Resto.HEIGHT/2,0);
        sb.begin();
        strpontos = ""+super.pontos;
        sb.draw(Background, 0, 0, Resto.WIDTH, Resto.HEIGHT);
        sb.draw(Wintext, Resto.WIDTH/2 - (Wintext.getWidth()/2), 200);
        sb.draw(btnMenu, (Resto.WIDTH/2) - (btnMenu.getWidth()/2), (Resto.HEIGHT/2) - 300);
        bitfont.draw(sb, strpontos, Resto.WIDTH/2 - 30, Resto.HEIGHT/2 + 35);

        Rectangle BtMenu = new Rectangle();
        BtMenu.setSize(btnMenu.getWidth(),btnMenu.getHeight());
        BtMenu.setPosition((Resto.WIDTH/2) - (btnMenu.getWidth()/2), (Resto.HEIGHT/2) - 300);


        if (cancatch) {
            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (OverlapTester.pointInRectangle(BtMenu, touchPoint.x, touchPoint.y)) {
                    gsm.pop();
                    dispose();
                    menu = new MenuState(gsm);
                    gsm.push(menu);
                }
            }
        }
        cancatch = true;

        sb.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            gsm.pop();
            dispose();
            newmenu = new MenuState(gsm);
            gsm.push(newmenu);
        }

    }

    @Override
    public void dispose() {
        Background.dispose();
        btnMenu.dispose();
    }
}