import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Ex1PONG {
    private static int width = 800;
    private static int height = 640;

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GL11.glPointSize(10);
        GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glBegin(GL11.GL_POINTS);
            for (int i = 0; i < 100; i++) {
                float u = (float) Math.random();
                float v = (float) Math.random();
                GL11.glColor3f(u, v, 1);
                GL11.glVertex2f(u * 2.f - 1.f, v * 2.f - 1.f);
            }
            GL11.glEnd();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Display.update();
        }
    }
}