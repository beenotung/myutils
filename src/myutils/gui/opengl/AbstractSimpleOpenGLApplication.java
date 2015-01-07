package myutils.gui.opengl;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by beenotung on 1/2/15.
 */

public abstract class AbstractSimpleOpenGLApplication extends AbstractOpenGLApplication {
    protected final float DEFAULT_SCROLL_SPEED = 0.2f;
    protected float scrollSpeed;
    protected float xRange, yRange, zRange;
    protected float cx, cy, cz;
    protected float vx, vy, vz;

    @Override
    protected void myInit() {
        cx = cy = cz = vx = vy = vz = 0f;
        xRange = yRange = zRange = 10f;
        scrollSpeed = DEFAULT_SCROLL_SPEED;
    }

    protected void keyInvoke(long window, int key, int scanCode, int action, int mode) {
        switch (key) {
            case GLFW_KEY_ESCAPE:
                key_escape();
                break;
            case GLFW_KEY_W:
                switch (action) {
                    case GLFW_PRESS:
                        vz = scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vz = 0;
                        break;
                }
                break;
            case GLFW_KEY_S:
                switch (action) {
                    case GLFW_PRESS:
                        vz = -scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vz = 0;
                        break;
                }
                break;
            case GLFW_KEY_Q:
                switch (action) {
                    case GLFW_PRESS:
                        vy = scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vy = 0;
                        break;
                }
                break;
            case GLFW_KEY_Z:
                switch (action) {
                    case GLFW_PRESS:
                        vy = -scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vy = 0;
                        break;
                }
                break;
            case GLFW_KEY_A:
                switch (action) {
                    case GLFW_PRESS:
                        vx = -scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vx = 0;
                        break;
                }
                break;
            case GLFW_KEY_D:
                switch (action) {
                    case GLFW_PRESS:
                        vx = scrollSpeed;
                        break;
                    case GLFW_RELEASE:
                        vx = 0;
                        break;
                }
                break;
            default:
                myKeyInvoke(window, key, scanCode, action, mode);
                break;
        }
    }

    protected void key_escape() {
        GLFW.glfwSetWindowShouldClose(window, GL_TRUE);
    }

    protected abstract void myKeyInvoke(long window, int key, int scanCode, int action, int mode);

    @Override
    protected void myTick() {
        cx += vx;
        cy += vy;
        cz += vz;
    }

    @Override
    protected void reshape() {
        glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if (WINDOW_WIDTH > WINDOW_HEIGHT)
            glOrtho(
                    (cx - xRange) * WINDOW_WIDTH / WINDOW_HEIGHT,
                    (cx + xRange) * WINDOW_WIDTH / WINDOW_HEIGHT,
                    cy - yRange, cy + yRange,
                    cz - zRange, cz + zRange
            );
        else
            glOrtho(
                    cx - xRange, cx + xRange,
                    (cy - yRange) * WINDOW_HEIGHT / WINDOW_WIDTH,
                    (cy + yRange) * WINDOW_HEIGHT / WINDOW_WIDTH,
                    cz - zRange, cz + zRange
            );
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
}