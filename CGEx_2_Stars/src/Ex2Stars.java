import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * Created by LAPTOP on 06.11.2017.
 */
public class Ex2Stars {
    boolean endThisApp = false;

    private static int w = 800;
    private static int h = 640;

    public static void main(String[] args) {
        Ex2Stars app = new Ex2Stars();
        app.run();
    }

    public void run() {
        init();
        while (!endThisApp) {
            draw();
            update();
            //Display.sync(60);
        }
    }

    float startTime = 0;

    public void init() {
        try {
            Display.setDisplayMode(new DisplayMode(w, h));
            Display.create();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        startTime = (float) Sys.getTime();
        GL11.glClearColor(0.0f, 0.0f, 0.5f, 0.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity(); // Saubermachen!
        GLU.gluPerspective(45.f, w / (float) h, 0.1f, 3000.f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

    }

    boolean wireFrame = true;

    public void update() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            endThisApp = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && wireFrame) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            wireFrame = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !wireFrame) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            wireFrame = true;
        }
        mRot = (360.f / 365.f) * getSimDay();
        eRot += 1;
        moonRot +=0.1;
    }

    float mRot = 0;
    float eRot = 0;
    float moonRot =0;

    public void draw() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        float[] cameraPos = new float[]{0, 0, -2000};
        GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, 0, 0, 0, 1, 0);

        GL11.glColor3f(255, 255, 0);
        GLDrawHelper.drawSphere(200f, 12, 12);

        GL11.glRotatef(mRot, 0.f, 1.f, 0.f);
        GL11.glTranslatef(500f, 0, 0);
        GL11.glPushMatrix();
        GL11.glRotatef(eRot, 0, 1, 0);
        GL11.glColor3f(0, 255, 255);
        GLDrawHelper.drawSphere(20f, 12, 12);

        GL11.glPopMatrix();
        GL11.glRotatef(moonRot, 0.f, 1.f, 0.f);
        GL11.glTranslatef(50f, 0, 0);
        //GL11.glRotatef(10, 0, 1, 0);
        GL11.glColor3f(100, 100, 100);
        GLDrawHelper.drawSphere(7f, 12, 12);



        Display.update();
    }

    float simTime = 0;
    float days_per_second = 10;

    public float getSimDay() {
        simTime = (days_per_second * (Sys.getTime() - startTime) / Sys.getTimerResolution());
        return simTime;
    }
}
