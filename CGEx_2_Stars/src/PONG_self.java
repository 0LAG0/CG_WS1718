import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;

/**
 * Created by LAPTOP on 23.10.2017.
 */
public class PONG_self {
    private static int width = 800;
    private static int height = 640;

    public static void main(String[] args) {
        PONG_self pong = new PONG_self();
        pong.run();
    }

    int ballX = 10;
    int ballY = 10;
    int ballDirY = 1;
    int ballDirX = 1;
    int player2Y = 100;
    int player1Y = 100;
    int ballspeed = 5;
    int speed = 10;
    int player1_score = 0;
    int player2_score = 0;
    public void run() {
        setup();
        while (update()) {
            draw();
            Display.update();
            Display.sync(60);
        }
    }
    TrueTypeFont font;
    public void setup() {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GL11.glMatrixMode((GL11.GL_PROJECTION));
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        Font awtFont = new Font("Arial", Font.BOLD,24);
        font = new TrueTypeFont(awtFont,true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
        //create display
        //set OpenGL ortho
        //set Viewport
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }


    public boolean update() {
        boolean isEnd = false;
        isEnd = !Display.isCloseRequested();

        long time = getTime();
        long lastTime = time;
        float timeDiff = time - lastTime;
        ballX+=ballspeed*ballDirX;
        ballY+=ballspeed*ballDirY;



        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) isEnd = true;
        //if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) isRunning = true;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) player2Y += speed;
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) player2Y -= speed;

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) player1Y += speed;
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) player1Y -= speed;

        if (ballX >= width || ballX<0){
            ballDirX *= -1;
        }
        if(ballY >= height || ballY <0){
            ballDirY *= -1;
        }

        if (ballY >= player2Y&& ballY <= player2Y+50 && ballX == 700){
            ballDirX *= -1;
        }
        if (ballY >= player1Y && ballY <= player1Y+50 && ballX == 100){
            ballDirX *= -1;
        }
        //get time
        //get keayboard input
        //e.g. W-S end ESC
        //move Player
        //move ball
        return isEnd;
    }

    public void draw() {
        GL11.glPointSize(10);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_BLEND);
        font.drawString(10,20, "SCORE: P1:  " + player1_score + "P2:  " + player2_score, Color.pink);
        GL11.glDisable(GL11.GL_BLEND);
        drawRect(700, player2Y, 710, player2Y + 50);
        drawRect(100, player1Y, 110, player1Y + 50);
        drawRect(ballX, ballY, ballX+10, ballY+10);
        //clear screen
        //draw player
        //draw ball
    }

    public void drawRect(int xs, int ys, int xe, int ye) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(xs, ys);
        GL11.glVertex2f(xs, ye);
        GL11.glVertex2f(xe, ye);
        GL11.glVertex2f(xe, ys);
        GL11.glEnd();
    }

}
