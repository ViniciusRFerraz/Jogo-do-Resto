package com.vinicius.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vinicius.game.Resto;
import com.vinicius.game.sprites.Player;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/** Created by Vinicius Ferraz **/

public class PlayState extends State implements Input.TextInputListener,Screen,ApplicationListener {
    boolean endgame = false;
    //Variáveis para o dado
    private Texture d1,d2,d3,d4,d5,d6; // faces do dado
    public boolean canroll = false, cancatch = false; // permissão para rolagem do dado
    public SecureRandom gerador = new SecureRandom(); //gerador de numeros aleatórios
    public int diceroll; //diceroll guarda o resultado da rolagem
    boolean generate = true;
    public boolean rolld1 = false, rolld2 = false, rolld3 = false, rolld4 = false, rolld5 = false, rolld6 = false; // face gerada vira true
    String resp = "0";
    public int code = 0;
    public boolean canback = false;

    //Variáveis do mapa e do jogo
    private TiledMap map; //mapa
    public OrthogonalTiledMapRenderer renderer, minimap; // renderizador para o mapa e minimapa
    String str = ""; // str é usada para escrever o numero gerado no tabuleiro
    public int i,j,numero; //numero guarda temporariamente cada numero gerado para o tabuleiro.
    int pontos = 0;
    private BitmapFont bitfont; //bitmap para desenhar a string
    String strpontos = ""; // usada para armazenar a pontuação.

    Tabuleiro tabuleiro = new Tabuleiro();

    int m[][] = { // matriz gerada pelo tiledmap 1= tabuleiro 0= fundo
            {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,5,0,0,0,0,0,0,0,0,0,0,34,35,36,37,38,39,40,41,42,43,44,0,0,0,0,0,0,0,},
            {0,6,0,0,0,0,0,0,0,0,0,0,33,0,0,0,0,0,0,0,0,0,45,0,0,0,0,0,0,0,},
            {0,7,8,9,10,11,12,13,0,0,0,0,32,0,0,0,0,0,0,0,0,0,46,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,14,0,0,0,0,31,0,0,0,0,52,51,50,49,48,47,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,15,0,0,0,0,30,0,0,0,0,53,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,16,0,0,0,0,29,0,0,0,0,54,0,0,0,0,0,0,0,70,71,72,73,0,},
            {0,0,0,0,0,0,0,17,0,0,0,0,28,0,0,0,0,55,0,0,0,0,0,0,0,69,0,0,74,0,},
            {0,0,0,0,0,0,0,18,0,0,0,0,27,0,0,0,0,56,0,0,0,0,0,0,0,68,0,0,75,0,},
            {0,0,0,0,0,0,0,19,0,0,0,0,26,0,0,0,0,57,0,0,0,0,0,0,0,67,0,0,76,0,},
            {0,0,0,0,0,0,0,20,21,22,23,24,25,0,0,0,0,58,59,60,61,62,63,64,65,66,0,0,77,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,78,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,79,80},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    };

    int gerados[][] = { // matriz onde os numeros gerados serão guardados
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
    };

    //variáveis para camera e jogador.
    public static Player player; // jogador
    public int casa = 1, move, resposta;
    public boolean canmove = false, respondido = false, pergunta = false, minimapshow = false;
    public int r = 102,g = 102,b = 255, gerapos = 1;
    private Viewport gamePort;
    public static float playerX;
    public static float playerY;
    Vector3 touchPoint;
    OrthographicCamera minimapcam = new OrthographicCamera();
    SpriteBatch minimapsb = new SpriteBatch();
    SpriteBatch sbaux = new SpriteBatch();

    //Menu
    private Texture hudborda, pngpontos, pngminimap, miniplayer;
    WinState winmenu;
    MenuState newmenu;

    // >> CONSTRUTOR << \\
    public PlayState(GameStateManager gsm) {
        super(gsm);
        player = new Player(0, 0);
        camera.setToOrtho(false, Resto.WIDTH/2 , Resto.HEIGHT/2);
        minimapcam.setToOrtho(false,Resto.WIDTH/2 , Resto.HEIGHT/2);
        gamePort = new ScreenViewport(camera);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);

        //carregamento das texturas do dado
        d1 = new Texture("d1.png");
        d2 = new Texture("d2.png");
        d3 = new Texture("d3.png");
        d4 = new Texture("d4.png");
        d5 = new Texture("d5.png");
        d6 = new Texture("d6.png");
        hudborda = new Texture("Borda.png");
        pngpontos = new Texture("Pontos.png");
        pngminimap = new Texture("Minimap.png");
        miniplayer = new Texture("miniplayer.png");

        //carregamento e renderização do mapa
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("map2.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map);
        minimap = new OrthogonalTiledMapRenderer(map, 1 / 2f);
        Gdx.input.setInputProcessor(player);
        Gdx.input.setCatchBackKey(true);
        bitfont = new BitmapFont(Gdx.files.internal("Vinni.fnt"), false);
        touchPoint = new Vector3();
        minimapcam = new OrthographicCamera(Resto.WIDTH,Resto.HEIGHT);
        minimapsb = new SpriteBatch();
        sbaux = new SpriteBatch();


        //ajuste da camera e do jogador
        camera.viewportWidth = Resto.WIDTH;
        camera.viewportHeight = Resto.HEIGHT;
        playerX = 0;// (map.getProperties().get("width",Integer.class)*map.getProperties().get("tilewidth",Integer.class) - 10);
        //playerY =( (map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class)-120) / 2);

    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        camera.update();
        renderer.setView(camera);
        minimapcam.update();
        minimap.setView(minimapcam);
    }

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
            return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y; }
    }


    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // Limpa Buffer.
        Gdx.gl.glClearColor(r, g, b, 1f); //cor padrão para limpeza do buffer
        sb.setProjectionMatrix(camera.combined);
        minimapsb.setProjectionMatrix(minimapcam.combined);
        sbaux.setProjectionMatrix(camera.combined);

        if(!minimapshow) {
            renderer.setView(camera);
            renderer.render();
        }else {
            renderer.setView(minimapcam);//.combined, 50,425,240,240);
            minimapcam.zoom = 7.5f;
            renderer.render();

        }


        sb.begin();

        //Ajuste do posicionamento da camera
        if(minimapshow){
            sbaux.begin();
            sbaux.draw(hudborda,camera.position.x - Resto.WIDTH/2, camera.position.y + Resto.HEIGHT/2 - 160);
            sbaux.draw(pngpontos, camera.position.x - Resto.WIDTH/2 + 10, camera.position.y + Resto.HEIGHT/2 - 70);
            sbaux.draw(pngminimap, camera.position.x - Resto.WIDTH/2 + 10, camera.position.y + Resto.HEIGHT/2 - 140);
            sbaux.end();

            minimapsb.begin();
            minimapsb.draw(miniplayer,playerX,playerY);
            minimapcam.position.set(1800,0,1f);
            minimapsb.end();
        }else if(!endgame) {
           camera.position.set(playerX, playerY, 0);

            if (camera.position.x < 240) {
                camera.position.x = 240;
            }
            if (camera.position.x > 3600 - 240) {
                camera.position.x = 3600 - 240;
            }
            if (camera.position.y > 1800 - 240) {
                camera.position.y = 1800 - 240;
            }
            if (camera.position.y < 400) {
                camera.position.y = 400;
            }
        }
        minimapcam.update();
        camera.update();

        //geração dos numeros no tabuleiro
        while (gerapos <= 80) {
            for (i = 0; i < 15; i++) {
                for (j = 0; j < 30; j++) {
                    if (generate && m[i][j] == gerapos) {
                        try {
                            do {
                                numero = gerador.nextInt(93) + 6;
                                gerados[i][j] = numero;
                            }while(numero % 6 == 0 );
                        } catch (NullPointerException e) {
                            System.out.println("erro ao gerar numeros do tabuleiro");
                        }
                        canroll = true;
                        tabuleiro.inserir(new Casa(m[i][j], numero, j * 120, 120 + i * 120));

                        System.out.println("casa " + m[i][j] + " valor = " + numero);
                        gerapos++;
                    }
                }
            }
            playerY = 1800 - tabuleiro.primeira.posy;
        }

        sb.draw(player.getTexture(), playerX , playerY); //Desenha o personagem

        //mostrando números na tela
        for (i = 0; i < 15; i++) {
            for (j = 0; j < 30; j++) {
                if (m[i][j] != 0) {
                    if (i == 14 && j == 29) {
                        str = "FIM";
                        bitfont.draw(sb, str, 30 + 120 * j, 1800 - (50 + 120 * i));
                    }else if (gerados[i][j] < 10){
                        str = "" + gerados[i][j];
                        bitfont.draw(sb, str, 48 + 120 * j, 1800 - (50 + 120 * i));
                    }else {
                        str = "" + gerados[i][j];
                        bitfont.draw(sb, str, 40 + 120 * j, 1800 - (50 + 120 * i));
                    }
                }
            }
        }

        //finaliza a geração dos números
        if (gerapos > 80)
            generate = false;


        //Mostra Menu
        sb.draw(hudborda,camera.position.x - Resto.WIDTH/2, camera.position.y + Resto.HEIGHT/2 - 160);
        sb.draw(pngpontos, camera.position.x - Resto.WIDTH / 2 + 10, camera.position.y + Resto.HEIGHT / 2 - 70);
        sb.draw(pngminimap, camera.position.x - Resto.WIDTH/2 + 10, camera.position.y + Resto.HEIGHT/2 - 140);
        Rectangle minimapbutton = new Rectangle();
        minimapbutton.setSize(pngminimap.getWidth(),pngminimap.getHeight());
        minimapbutton.setPosition(camera.position.x - Resto.WIDTH/2 + 10, camera.position.y + Resto.HEIGHT/2 - 140);

        strpontos = ""+super.pontos;
        bitfont.draw(sb, strpontos,camera.position.x - Resto.WIDTH/2 + 200, camera.position.y + Resto.HEIGHT/2 - 20 );

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("!! THE BACK KEY WAS PRESSED BRO !!");
            Gdx.input.getTextInput(this, "O jogo será perdido. Deseja realmente voltar ? ", "", "y or n");
        }

        if(verificasaida()){
            gsm.pop();
            dispose();
            newmenu = new MenuState(gsm);
            gsm.push(newmenu);
        }

        //Jogando o dado
        if(cancatch) {
            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if (OverlapTester.pointInRectangle(minimapbutton, touchPoint.x, touchPoint.y)) {
                    minimapshow = !minimapshow;
                } else if (canroll && !minimapshow) {
                    canroll = false;
                    diceroll = 1 + gerador.nextInt(6);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        System.out.println("Erro na rolagem de dados (Sleep)");
                    }
                    switch (diceroll) {
                        case 1:
                            rolld1 = true; //d1
                            rolld2 = false;
                            rolld3 = false;
                            rolld4 = false;
                            rolld5 = false;
                            rolld6 = false;
                            pergunta = true;
                            System.out.println("d1");
                            break;
                        case 2:
                            rolld1 = false;
                            rolld2 = true; //d2
                            rolld3 = false;
                            rolld4 = false;
                            rolld5 = false;
                            rolld6 = false;
                            pergunta = true;
                            System.out.println("d2");
                            break;
                        case 3:
                            rolld1 = false;
                            rolld2 = false;
                            rolld3 = true; //d3
                            rolld4 = false;
                            rolld5 = false;
                            rolld6 = false;
                            pergunta = true;
                            System.out.println("d3");
                            break;
                        case 4:
                            rolld1 = false;
                            rolld2 = false;
                            rolld3 = false;
                            rolld4 = true; //d4
                            rolld5 = false;
                            rolld6 = false;
                            pergunta = true;
                            System.out.println("d4");
                            break;
                        case 5:
                            rolld1 = false;
                            rolld2 = false;
                            rolld3 = false;
                            rolld4 = false;
                            rolld5 = true; //d5
                            rolld6 = false;
                            pergunta = true;
                            System.out.println("d5");
                            break;
                        case 6:
                            rolld1 = false;
                            rolld2 = false;
                            rolld3 = false;
                            rolld4 = false;
                            rolld5 = false;
                            rolld6 = true; //d6
                            pergunta = true;
                            System.out.println("d6");
                            break;
                    }
                }
            }
        }




        //Desenha a face escolhida do dado
        if (rolld1) {
            sb.draw(d1, camera.position.x - Resto.WIDTH/2 + 350, camera.position.y + Resto.HEIGHT/2 - 120);
        } else if (rolld2) {
            sb.draw(d2, camera.position.x - Resto.WIDTH/2 + 350, camera.position.y + Resto.HEIGHT/2 - 120);
        } else if (rolld3) {
            sb.draw(d3, camera.position.x - Resto.WIDTH/2 + 350,camera.position.y + Resto.HEIGHT/2 - 120 );
        } else if (rolld4) {
            sb.draw(d4, camera.position.x - Resto.WIDTH/2 + 350,camera.position.y + Resto.HEIGHT/2 - 120);
        } else if (rolld5) {
            sb.draw(d5, camera.position.x - Resto.WIDTH/2 + 350,camera.position.y + Resto.HEIGHT/2 - 120);
        } else if (rolld6) {
            sb.draw(d6, camera.position.x - Resto.WIDTH/2 + 350,camera.position.y + Resto.HEIGHT/2 - 120 );
        }

        // Pergunta e Resposta
        if (pergunta) {
            pergunta = false;
            code = 2;
            System.out.println("Resposta é " + tabuleiro.posplayer.valor % diceroll);
            Gdx.input.getTextInput(this, "Qual é o resto da divisão de " + tabuleiro.posplayer.valor + " por " + diceroll + " ?", "", "");
        }

        if (respondido) {
            canroll = true;
            try {
                resposta = Integer.valueOf(resp).intValue();
            } catch (NullPointerException e) {
                System.out.println("Erro! Resposta nula");
            }catch (NumberFormatException e){
                System.out.println("Numero incorreto.");
            }
            if (resp != "") {
                System.out.println("Respondeu = " + resposta);
                if (resposta == tabuleiro.posplayer.valor % diceroll && resposta != 0) {
                    super.pontos += tabuleiro.posplayer.valor * diceroll;
                    move = tabuleiro.posplayer.valor % diceroll;
                    System.out.println("move = "+move);
                    canmove = true;
                }else if (resposta != 0 || (resposta == 0 && resposta != tabuleiro.posplayer.valor % diceroll)){
                    super.pontos -= tabuleiro.posplayer.valor * diceroll;
                }else if (resposta == tabuleiro.posplayer.valor % diceroll && resposta == 0){
                    super.pontos += tabuleiro.posplayer.valor;
                    move = 1;
                    System.out.println("move = "+move);
                    canmove = true;
                }
                respondido = false;
            }
        }

        // Verificando resultado e movendo o jogador pelo tabuleiro
        if(endgame){
            System.out.println("movendo........");
            winmenu = new WinState(gsm);
            gsm.push(winmenu);
            dispose();
        }else if (canmove) {
         laço:   for (i = 0; i < 15; i++) {
                for (j = 0; j < 30; j++) {

                    if (casa + move >= 80) {
                        System.out.println("Final do jogo !");
                        tabuleiro.posplayer = tabuleiro.ultima;
                        move = 0;
                        casa = 80;
                        endgame = true;
                        break laço;
                    }else if (m[i][j] > casa && m[i][j] <= (casa + move) && m[i][j] != 0) {
                        System.out.println("Casa atual = "+(casa));
                        tabuleiro.posplayer = tabuleiro.posplayer.prox;
                        System.out.println("Movimento executado !");
                        i=0; // Nova varredura da matriz. Sem isso não "anda" para cima pelo fato de varrer a matriz para baixo.
                        j=0;
                        move--; // Diminui a quantidade de movimentos restantes.
                        casa++; // Atualiza a verificação da posição atual.
                    }

                    if (m[i][j] == casa) {
                        playerX = tabuleiro.posplayer.posx;
                        playerY = 1800 - tabuleiro.posplayer.posy;
                        System.out.println("Camera Y Normal= "+camera.position.y);

                    }
                }
            }
            canmove = false;
        }
        cancatch = true;
        sb.end();

    }

    public boolean verificasaida(){
        if(resp.equals("y") || resp.equals("Y")){
            System.out.println("Voltando");
            return true;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }



    @Override
    public void dispose() {
        bitfont.dispose();
        d1.dispose();
        d2.dispose();
        d3.dispose();
        d4.dispose();
        d5.dispose();
        d6.dispose();
        map.dispose();
        hudborda.dispose();
        pngpontos.dispose();
        pngminimap.dispose();
        miniplayer.dispose();
        //winmenu = new WinState(gsm);
        //gsm.push(winmenu);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public void input(String text) {
        int txt;
        this.resp = text;

        if(code == 2) {
            try {
                txt = Integer.valueOf(text).intValue();
            } catch (NumberFormatException e) {
                System.out.println("Numero Incorreto ou vazio");
                txt = 0;
                text = "0";
            }
            try {
                if (txt != 0 || txt != 1 || txt != 2 || txt != 3 || txt != 4 || txt != 5) {
                    this.resp = text;
                } else
                    this.resp = "0";
            } catch (NullPointerException e) {
                System.out.println("Erro ao capturar string");
            }
            respondido = true;
        }
        code = 0;
    }

    @Override
    public void canceled() {
        if (code == 2) {
            resp = "9";
            respondido = true;
            code = 0;
        }
    }

}
